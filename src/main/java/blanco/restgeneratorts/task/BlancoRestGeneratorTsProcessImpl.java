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
import blanco.restgeneratorts.BlancoRestGeneratorTsObjectsInfo;
import blanco.restgeneratorts.BlancoRestGeneratorTsXml2SourceFile;
import blanco.restgeneratorts.resourcebundle.BlancoRestGeneratorTsResourceBundle;
import blanco.restgeneratorts.task.valueobject.BlancoRestGeneratorTsProcessInput;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

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
                System.out.println("/* tueda */ TARGETDIR = " + strTarget);
            }

            /*
             * validator を作る時に使うために，
             * ValueObject で既に定義されている（はずの）オブジェクトを取得しておく
             */
            final BlancoRestGeneratorTsObjectsInfo objectsInfo = new BlancoRestGeneratorTsObjectsInfo();
            objectsInfo.setEncoding(input.getEncoding());
            objectsInfo.setVerbose(input.getVerbose());
            objectsInfo.process(input);

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
                xml2source.process(fileMeta2[index], new File(strTarget));
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
