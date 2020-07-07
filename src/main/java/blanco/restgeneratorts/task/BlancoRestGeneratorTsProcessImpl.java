/*
 * blanco Framework
 * Copyright (C) 2004-2009 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.restgeneratorts.task;

import blanco.cg.BlancoCgSupportedLang;
import blanco.restgeneratorts.BlancoRestGeneratorTsConstants;
import blanco.restgeneratorts.BlancoRestGeneratorTsMeta2Xml;
import blanco.restgeneratorts.BlancoRestGeneratorTsUtil;
import blanco.restgeneratorts.BlancoRestGeneratorTsXml2SourceFile;
import blanco.restgeneratorts.resourcebundle.BlancoRestGeneratorTsResourceBundle;
import blanco.restgeneratorts.task.valueobject.BlancoRestGeneratorTsProcessInput;
import blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramProcessStructure;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlancoRestGeneratorTsProcessImpl implements
        BlancoRestGeneratorTsProcess {
    /**
     * このプロダクトのリソースバンドルへのアクセスオブジェクト。
     */
    private final BlancoRestGeneratorTsResourceBundle fBundle = new BlancoRestGeneratorTsResourceBundle();

    /**
     * {@inheritDoc}
     */
    public int execute(final BlancoRestGeneratorTsProcessInput input) {
        System.out.println("- " + BlancoRestGeneratorTsConstants.PRODUCT_NAME
                + " (" + BlancoRestGeneratorTsConstants.VERSION + ")" + " for " + input.getSheetType());

        try {
            final File fileMetadir = new File(input.getMetadir());
            if (fileMetadir.exists() == false) {
                throw new IllegalArgumentException(fBundle
                        .getAnttaskErr001(input.getMetadir()));
            }

            /*
             * 改行コードを決定します。
             */
            String LF = "\n";
            String CR = "\r";
            String CRLF = CR + LF;
            String lineSeparatorMark = input.getLineSeparator();
            String lineSeparator = "";
            if ("LF".equals(lineSeparatorMark)) {
                lineSeparator = LF;
            } else if ("CR".equals(lineSeparatorMark)) {
                lineSeparator = CR;
            } else if ("CRLF".equals(lineSeparatorMark)) {
                lineSeparator = CRLF;
            }
            if (lineSeparator.length() != 0) {
                System.setProperty("line.separator", lineSeparator);
                if (input.getVerbose()) {
                    System.out.println("lineSeparator try to change to " + lineSeparatorMark);
                    String newProp = System.getProperty("line.separator");
                    String newMark = "other";
                    if (LF.equals(newProp)) {
                        newMark = "LF";
                    } else if (CR.equals(newProp)) {
                        newMark = "CR";
                    } else if (CRLF.equals(newProp)) {
                        newMark = "CRLF";
                    }
                    System.out.println("New System Props = " + newMark);
                }
            }

            /*
             * targetdir, targetStyleの処理。
             * 生成されたコードの保管場所を設定する。
             * targetstyle = blanco:
             *  targetdirの下に main ディレクトリを作成
             * targetstyle = maven:
             *  targetdirの下に main/java ディレクトリを作成
             * targetstyle = free:
             *  targetdirをそのまま使用してディレクトリを作成。
             *  ただしtargetdirがからの場合はデフォルト文字列(blanco)使用する。
             * by tueda, 2019/08/30
             */
            String strTarget = input.getTargetdir();
            String style = input.getTargetStyle();
            // ここを通ったら常にtrue
            boolean isTargetStyleAdvanced = true;

            if (style != null && BlancoRestGeneratorTsConstants.TARGET_STYLE_MAVEN.equals(style)) {
                strTarget = strTarget + "/" + BlancoRestGeneratorTsConstants.TARGET_DIR_SUFFIX_MAVEN;
            } else if (style == null ||
                    !BlancoRestGeneratorTsConstants.TARGET_STYLE_FREE.equals(style)) {
                strTarget = strTarget + "/" + BlancoRestGeneratorTsConstants.TARGET_DIR_SUFFIX_BLANCO;
            }
            /* style が free だったらtargetdirをそのまま使う */
            if (input.getVerbose()) {
                System.out.println("BlancoRestGeneratorTsProcessImpl#process TARGETDIR = " + strTarget);
            }

            /*
             * validator を作る時に使うために，
             * ValueObject で既に定義されている（はずの）オブジェクトを取得しておく
             */
            BlancoRestGeneratorTsUtil.isVerbose = input.getVerbose();
            BlancoRestGeneratorTsUtil.processValueObjects(input);

            /*
             * 電文定義ID（文字列）の一覧を保持するクラス名・ファイル名を取得
             */
            String processListId = input.getProcesslist();
            boolean createProcessList = false;
            List<BlancoRestGeneratorTsTelegramProcessStructure> processStructureList = new ArrayList<>();
            if (processListId != null && processListId.length() > 0) {
                createProcessList = true;
            }

            // テンポラリディレクトリを作成。
            new File(input.getTmpdir()
                    + BlancoRestGeneratorTsConstants.TARGET_SUBDIRECTORY)
                    .mkdirs();

            // 指定されたメタディレクトリを処理します。
            new BlancoRestGeneratorTsMeta2Xml()
                    .processDirectory(fileMetadir, input.getTmpdir()
                            + BlancoRestGeneratorTsConstants.TARGET_SUBDIRECTORY);

            // XML化された中間ファイルからソースコードを生成
            final File[] fileMeta2 = new File(input.getTmpdir()
                    + BlancoRestGeneratorTsConstants.TARGET_SUBDIRECTORY)
                    .listFiles();
            for (int index = 0; index < fileMeta2.length; index++) {
                if (fileMeta2[index].getName().endsWith(".xml") == false) {
                    continue;
                }

                final BlancoRestGeneratorTsXml2SourceFile xml2source = new BlancoRestGeneratorTsXml2SourceFile();
                xml2source.setEncoding(input.getEncoding());
                xml2source.setSheetLang(new BlancoCgSupportedLang().convertToInt(input.getSheetType()));
                xml2source.setTargetStyleAdvanced(isTargetStyleAdvanced);
                xml2source.setVerbose(input.getVerbose());
                xml2source.setCreateServiceMethod(!input.getClient());
                xml2source.setTabs(input.getTabs());
                xml2source.setDefaultGenerateToJson(input.getGenerateToJson());

                BlancoRestGeneratorTsTelegramProcessStructure[] processStructures =
                xml2source.process(fileMeta2[index], new File(strTarget));
                /*
                 * processList の生成が指定されている場合は、
                 * 自動生成した電文処理クラスの一覧を収集します。
                 */
                for (int index2 = 0; createProcessList && index2 < processStructures.length; index2++) {
                    processStructureList.add(processStructures[index2]);
                }
            }

            /*
             * processList を生成します。
             */
            if (createProcessList && processStructureList.size() > 0) {


                final BlancoRestGeneratorTsXml2SourceFile xml2source = new BlancoRestGeneratorTsXml2SourceFile();
                xml2source.setEncoding(input.getEncoding());
                xml2source.setSheetLang(new BlancoCgSupportedLang().convertToInt(input.getSheetType()));
                xml2source.setTargetStyleAdvanced(isTargetStyleAdvanced);
                xml2source.setTabs(2);
                xml2source.setVerbose(input.getVerbose());
                /*
                 * processList では toJSON メソッドは生成しません。
                 */
                xml2source.setDefaultGenerateToJson(false);
                xml2source.processProcessList(processStructureList, processListId, input.getProcesslistBase(), new File(strTarget));
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.toString());
        } catch (TransformerException ex) {
            throw new IllegalArgumentException(ex.toString());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    public boolean progress(final String argProgressMessage) {
        System.out.println(argProgressMessage);
        return false;
    }
}
