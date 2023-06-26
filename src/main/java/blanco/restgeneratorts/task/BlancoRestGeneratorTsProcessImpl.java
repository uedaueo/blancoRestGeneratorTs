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
import blanco.commons.util.BlancoStringUtil;
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
     * An access object to the resource bundle for this product.
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
             * Determines the newline code.
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
             * Processes targetdir and targetStyle.
             * Sets the storage location for the generated code.
             * targetstyle = blanco:
             *  Creates a main directory under targetdir.
             * targetstyle = maven:
             *  Creates a main/java directory under targetdir.
             * targetstyle = free:
             *  Creates a directory using targetdir as is.
             *  However, the default string (blanco) is used if targetdir is empty.
             * by tueda, 2019/08/30
             */
            String strTarget = input.getTargetdir();
            String style = input.getTargetStyle();
            // Always true when passing through here.
            boolean isTargetStyleAdvanced = true;

            if (style != null && BlancoRestGeneratorTsConstants.TARGET_STYLE_MAVEN.equals(style)) {
                strTarget = strTarget + "/" + BlancoRestGeneratorTsConstants.TARGET_DIR_SUFFIX_MAVEN;
            } else if (style == null ||
                    !BlancoRestGeneratorTsConstants.TARGET_STYLE_FREE.equals(style)) {
                strTarget = strTarget + "/" + BlancoRestGeneratorTsConstants.TARGET_DIR_SUFFIX_BLANCO;
            }
            /* Uses targetdir as is if style is free. */
            if (input.getVerbose()) {
                System.out.println("BlancoRestGeneratorTsProcessImpl#process TARGETDIR = " + strTarget);
            }

            /*
             * Gets an object that is already defined in ValueObject (supposed to be) to use when creating a validator.
             */
            BlancoRestGeneratorTsUtil.isVerbose = input.getVerbose();
            BlancoRestGeneratorTsUtil.processValueObjects(input);

            /*
             * Gets the class name and file name that holds the list of telegram definition IDs (strings).
             */
            String processListId = input.getProcesslist();
            boolean createProcessList = false;
            List<BlancoRestGeneratorTsTelegramProcessStructure> processStructureList = new ArrayList<>();
            if (processListId != null && processListId.length() > 0) {
                createProcessList = true;
            }

            /*
             * Get apiTelegramPackage and base
             */
            String apiTelegramPackage = input.getApiTelegramPackage();
            String apiTelegramBase = input.getApiTelegramBase(); // has default value.

            /*
             * Specify telegramStyle
             */
            String telegramStyle = input.getTelegramStyle();
            if (!telegramStyle.equals(BlancoRestGeneratorTsConstants.TELEGRAM_STYLE_BLANCO) && !telegramStyle.equals(BlancoRestGeneratorTsConstants.TELEGRAM_STYLE_PLAIN)) {
                throw new IllegalArgumentException(fBundle.getBlancorestTelegramStyleError());
            }
            if (telegramStyle.equals(BlancoRestGeneratorTsConstants.TELEGRAM_STYLE_PLAIN)) {
                BlancoRestGeneratorTsUtil.isTelegramStyleBlanco = false;
                BlancoRestGeneratorTsUtil.isTelegramStylePlain = true;
            }

            // Creates a temporary directory.
            new File(input.getTmpdir()
                    + BlancoRestGeneratorTsConstants.TARGET_SUBDIRECTORY)
                    .mkdirs();

            // Processes the specified meta directory.
            new BlancoRestGeneratorTsMeta2Xml()
                    .processDirectory(fileMetadir, input.getTmpdir()
                            + BlancoRestGeneratorTsConstants.TARGET_SUBDIRECTORY);

            // Generates source code from XML-ized intermediate files.
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

                if (BlancoStringUtil.null2Blank(apiTelegramPackage).length() > 0) {
                    xml2source.setSearchApiTelegramPackage(false);
                    xml2source.setApiTelegramPackge(apiTelegramPackage);
                    xml2source.setApiTelegramBase(apiTelegramBase);
                }

                BlancoRestGeneratorTsTelegramProcessStructure[] processStructures =
                xml2source.process(fileMeta2[index], new File(strTarget));
                /*
                 * If the generation of processList is specified, the list of auto-generated telegram processing class is collected.
                 */
                for (int index2 = 0; createProcessList && index2 < processStructures.length; index2++) {
                    processStructureList.add(processStructures[index2]);
                }
            }

            /*
             * Generates processList.
             */
            if (createProcessList && processStructureList.size() > 0) {


                final BlancoRestGeneratorTsXml2SourceFile xml2source = new BlancoRestGeneratorTsXml2SourceFile();
                xml2source.setEncoding(input.getEncoding());
                xml2source.setSheetLang(new BlancoCgSupportedLang().convertToInt(input.getSheetType()));
                xml2source.setTargetStyleAdvanced(isTargetStyleAdvanced);
                xml2source.setTabs(2);
                xml2source.setVerbose(input.getVerbose());
                /*
                 * In processList, it does not generate toJSON method.
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
