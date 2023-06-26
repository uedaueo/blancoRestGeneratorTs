/*
 * blanco Framework
 * Copyright (C) 2004-2006 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.restgeneratorts;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoStringUtil;
import blanco.restgeneratorts.resourcebundle.BlancoRestGeneratorTsResourceBundle;
import blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramFieldStructure;
import blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramProcessStructure;
import blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramStructure;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Generates class source code to process messages from "Message Definition" Excel format.
 *
 * This class is responsible for auto-generating source code from intermediate XML files.
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoRestGeneratorTsXml2SourceFile {

    private boolean fVerbose = false;
    public void setVerbose(boolean argVerbose) {
        this.fVerbose = argVerbose;
    }
    public boolean isVerbose() {
        return fVerbose;
    }

    private int fTabs = 4;
    public int getTabs() {
        return fTabs;
    }
    public void setTabs(int fTabs) {
        this.fTabs = fTabs;
    }

    private boolean fCreateServiceMethod = false;
    public void setCreateServiceMethod(boolean argCreateServiceMethod) {
        this.fCreateServiceMethod = argCreateServiceMethod;
    }
    public boolean isCreateServiceMethod() {
        return fCreateServiceMethod;
    }

    private boolean fDefaultGenerateToJson = false;
    public boolean isDefaultGenerateToJson() {
        return this.fDefaultGenerateToJson;
    }
    public void setDefaultGenerateToJson(boolean generateToJson) {
        this.fDefaultGenerateToJson = generateToJson;
    }

    private boolean fSearchApiTelegramPackage = true;
    public boolean searchApiTelegramPackage() {
        return this.fSearchApiTelegramPackage;
    }
    public void setSearchApiTelegramPackage(boolean search) {
        this.fSearchApiTelegramPackage = search;
    }

    private String fApiTelegramPackage;
    public String getApiTelegramPackage() {
        return this.fApiTelegramPackage;
    }
    public void setApiTelegramPackge(String apiTelegramPackge) {
        this.fApiTelegramPackage = apiTelegramPackge;
    }

    private String fApiTelegramBase = "%";
    public String getApiTelegramBase() {
        return this.fApiTelegramBase;
    }
    public void setApiTelegramBase(String apiTelegramBase) {
        this.fApiTelegramBase = apiTelegramBase;
    }

    /**
     * An access object to the resource bundle for this product.
     */
    private final BlancoRestGeneratorTsResourceBundle fBundle = new BlancoRestGeneratorTsResourceBundle();

    /**
     * Target programming language.
     */
    private int fTargetLang = BlancoCgSupportedLang.TS;

    /**
     * A programming language expected for the input sheet.
     */
    private int fSheetLang = BlancoCgSupportedLang.JAVA;

    public void setSheetLang(final int argSheetLang) {
        fSheetLang = argSheetLang;
    }

    /**
     * Style of the source code generation destination directory
     */
    private boolean fTargetStyleAdvanced = false;
    public void setTargetStyleAdvanced(boolean argTargetStyleAdvanced) {
        this.fTargetStyleAdvanced = argTargetStyleAdvanced;
    }
    public boolean isTargetStyleAdvanced() {
        return this.fTargetStyleAdvanced;
    }

    /**
     * A factory for blancoCg to be used internally.
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * Source file information for blancoCg to be used internally.
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    /**
     * Class information for blancoCg to be used internally.
     */
    private BlancoCgClass fCgClass = null;

    /**
     * Interface information for blancoCg to be used internally.
     */
    private BlancoCgInterface fCgInterface = null;

    /**
     * Whether to perform name transformation for field and method names.
     *
     */
    private boolean fNameAdjust = true;

    /**
     * Base class for request telegrams.
     */
    private String inputTelegramBase = null;
    /**
     * Base class for response telegrams.
     */
    private String outputTelegramBase = null;

    /**
     * Character encoding of auto-generated source files.
     */
    private String fEncoding = null;

    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    /**
     * Auto-generates source code from intermediate XML files.
     *
     * @param argMetaXmlSourceFile
     *            An XML file that contains meta-information.
     * @param argDirectoryTarget
     *            Output directory of the generated source code (specify the part excluding /main).
     * @throws IOException
     *             If an I/O exception occurs.
     */
    public BlancoRestGeneratorTsTelegramProcessStructure[] process(final File argMetaXmlSourceFile,
                        final File argDirectoryTarget)
            throws IOException, TransformerException {

        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorTsXml2SourceFile#process file = " + argMetaXmlSourceFile.getName());
        }

        fNameAdjust = true; // In BlancoRestGenerator, it always transforms the field name.

        BlancoRestGeneratorTsXmlParser parser = new BlancoRestGeneratorTsXmlParser();
        parser.setVerbose(this.isVerbose());
        parser.setCreateServiceMethod(this.isCreateServiceMethod());
        if (!this.searchApiTelegramPackage()) {
            parser.setSearchApiTelegramPackage(false);
            parser.setApiTelegramPackge(this.getApiTelegramPackage());
            parser.setApiTelegramBase(this.getApiTelegramBase());
        }

        BlancoRestGeneratorTsTelegramProcessStructure[] processStructures =
                parser.parse(argMetaXmlSourceFile);

        if (processStructures == null) {
            System.out.println("!!! SKIP !!!! " + argMetaXmlSourceFile.getName());
            return null;
        }

        BlancoRestGeneratorTsExpander expander = null;
        if (BlancoRestGeneratorTsUtil.isTelegramStyleBlanco) {
            expander = new BlancoRestGeneratorTsBlancoStyleExpander();
        } else {
            throw new IllegalArgumentException("NIY BlancoRestGeneratorTsPlainStyelExpander.");
        }

        expander.setEncoding(this.fEncoding);
        expander.setSheetLang(this.fSheetLang);
        expander.setTargetStyleAdvanced(this.fTargetStyleAdvanced);
        expander.setVerbose(this.fVerbose);
        expander.setCreateServiceMethod(this.fCreateServiceMethod);
        expander.setTabs(this.getTabs());
        expander.setDefaultGenerateToJson(this.fDefaultGenerateToJson);
        if (BlancoStringUtil.null2Blank(this.fApiTelegramPackage).length() > 0) {
            expander.setSearchApiTelegramPackage(false);
            expander.setApiTelegramPackge(this.fApiTelegramPackage);
            expander.setApiTelegramBase(this.fApiTelegramBase);
        }
        expander.setNameAdjust(this.fNameAdjust);

        for (int index = 0; index < processStructures.length; index++) {
            BlancoRestGeneratorTsTelegramProcessStructure processStructure = processStructures[index];
            // Generates TypeScript code from the obtained information.
            expander.expand(processStructure, argDirectoryTarget);
        }
        return processStructures;
    }

    public void processProcessList(
            final List<BlancoRestGeneratorTsTelegramProcessStructure> argProcessStructures,
            final String argProcessListId, String processListBasedir,
            final File argDirectoryTarget
    ) {

        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorTsXml2SourceFile#createProcessListClass file = " + argProcessListId);
        }

        fNameAdjust = true; // BlancoRestGenerator always transforms the field name.

        final BlancoRestGeneratorTsXmlParser parser = new BlancoRestGeneratorTsXmlParser();
        parser.setVerbose(this.isVerbose());
        parser.setCreateServiceMethod(this.isCreateServiceMethod());
        final List<String> importHeaderList = parser.parseProcessListImport(argProcessStructures, argProcessListId, processListBasedir);

        /*
         * First, generates an interface.
         */
        final String interfaceId = this.buildProcessListInterface(argDirectoryTarget, argProcessListId, importHeaderList);

        /*
         * Then, generates the telegram processing list class.
         */
        this.buildProcessList(argProcessStructures, importHeaderList, argDirectoryTarget, interfaceId, argProcessListId);

    }

    protected String buildProcessListInterface(
            final File argDirectoryTarget,
            final String argProcessListId,
            final List<String> argImportHeaderList
    ) {
        /* tueda DEBUG */
        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorTsXml2SourceFile#buildProcessListInterface SATR with argTargetDirectory : " + argDirectoryTarget.getAbsolutePath());
        }

        /*
         * The output directory will be in the format specified by the targetStyle argument of the ant task.
         * For compatibility, the output directory will be blanco/main if it is not specified.
         * by tueda, 2019/08/30
         */
        String strTarget = argDirectoryTarget
                .getAbsolutePath(); // advanced
        if (!this.isTargetStyleAdvanced()) {
            strTarget += "/main"; // legacy
        }
        final File fileBlancoMain = new File(strTarget);

        final String processListPackage = BlancoRestGeneratorTsUtil.getPackageName(argProcessListId);
        final String processListInterface = BlancoRestGeneratorTsUtil.getSimpleClassName(argProcessListId) + "Interface";

        final String toImort = "import { " + processListInterface + " } from \"./" + processListInterface + "\"";
        argImportHeaderList.add(toImort);

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(processListPackage, "This source code has been generated by blanco Framework.");
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());
        // Creates a class.
        fCgInterface = fCgFactory.createInterface(processListInterface,
                BlancoStringUtil.null2Blank("The interface definition of a list that gets instances from API argument string."));
        fCgSourceFile.getInterfaceList().add(fCgInterface);
        fCgInterface.setAccess("public");
        fCgInterface.getLangDoc().getTagList().add(fCgFactory.createLangDocTag("author", null, "blanco Framework"));

        final String arrayConstructor = "[i: string]: any" + BlancoCgLineUtil.getTerminator(fTargetLang);
        fCgInterface.getPlainTextList().add(arrayConstructor);

        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getTsSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
        return processListInterface;
    }

    protected void buildProcessList(
            List<BlancoRestGeneratorTsTelegramProcessStructure> argProcessStructures,
            List<String> argImportHeaderList,
            File argDirectoryTarget,
            String argInterfaceId,
            String argProcessListId
    ) {
        /* tueda DEBUG */
        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorTsXml2SourceFile#buildProcessList SATR with argTargetDirectory : " + argDirectoryTarget.getAbsolutePath());
        }

        /*
         * The output directory will be in the format specified by the targetStyle argument of the ant task.
         * For compatibility, the output directory will be blanco/main if it is not specified.
         * by tueda, 2019/08/30
         */
        String strTarget = argDirectoryTarget
                .getAbsolutePath(); // advanced
        if (!this.isTargetStyleAdvanced()) {
            strTarget += "/main"; // legacy
        }
        final File fileBlancoMain = new File(strTarget);

        String processListPackage = BlancoRestGeneratorTsUtil.getPackageName(argProcessListId);
        String processListClass = BlancoRestGeneratorTsUtil.getSimpleClassName(argProcessListId);

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(processListPackage, "This source code has been generated by blanco Framework.");
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());
        // Creates a class.
        fCgClass = fCgFactory.createClass(processListClass,
                BlancoStringUtil.null2Blank("A class that holds a list of instances to be obtained from API argument string."));
        fCgSourceFile.getClassList().add(fCgClass);
        fCgClass.setAccess("public");

        /*
         * Setting the Import statement.
         */
        for (String header : argImportHeaderList) {
            fCgSourceFile.getHeaderList().add(header);
        }

        this.buildProcessListField(argProcessStructures, argInterfaceId);

        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getTsSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * For the sake of formatting, the field is inserted as Plain Text.
     * @param argProcessStructures
     * @param argInterfaceId
     */
    protected void buildProcessListField(
            List<BlancoRestGeneratorTsTelegramProcessStructure> argProcessStructures,
            String argInterfaceId
    ) {
        // System.lineSeparator should not be used, because it cannot be changed from its fixed value at startup.
        final String lineSeparator = System.getProperty("line.separator");
        final String fieldName = "constructors";
        final String fieldType = argInterfaceId;

        final BlancoCgField field = fCgFactory.createField(fieldName,
                fieldType,"A list of instances to be obtained from API argument string.");

        fCgClass.getFieldList().add(field);

        field.setAccess("public");
        field.setStatic(true);
        field.setNotnull(true);

        /*
         * For the sake of formatting, the default value should be a fixed tab.
         */
        int tabs = this.getTabs();
        String indentUnit = " ";
        String indent = "";
        for (int i = 0; i < tabs; i++) {
            indent += indentUnit;
        }
        String indent2 = indent + indent;

        StringBuffer sb = new StringBuffer();
        sb.append("{" + lineSeparator);

        boolean first = true;
        for (BlancoRestGeneratorTsTelegramProcessStructure processStructure : argProcessStructures) {
            if (!first) {
                sb.append("," + lineSeparator);
            }
            sb.append(indent2 + processStructure.getName() + ": " + processStructure.getName());
            first = false;
        }
        sb.append(lineSeparator + indent + "}");

        field.setDefault(sb.toString());
    }
}
