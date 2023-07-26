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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates class source code to process messages from "Message Definition" Excel format.
 *
 * This class is responsible for auto-generating source code from intermediate XML files.
 *
 * @author IGA Tosiki
 * @author tueda
 */
public abstract class BlancoRestGeneratorTsExpander implements BlancoRestGeneratorTsExpanderInterface {

    protected boolean fVerbose = false;
    public void setVerbose(boolean argVerbose) {
        this.fVerbose = argVerbose;
    }
    public boolean isVerbose() {
        return fVerbose;
    }

    protected int fTabs = 4;
    public int getTabs() {
        return fTabs;
    }
    public void setTabs(int fTabs) {
        this.fTabs = fTabs;
    }

    protected boolean fCreateServiceMethod = false;
    public void setCreateServiceMethod(boolean argCreateServiceMethod) {
        this.fCreateServiceMethod = argCreateServiceMethod;
    }
    public boolean isCreateServiceMethod() {
        return fCreateServiceMethod;
    }

    protected boolean fDefaultGenerateToJson = false;
    public boolean isDefaultGenerateToJson() {
        return this.fDefaultGenerateToJson;
    }
    public void setDefaultGenerateToJson(boolean generateToJson) {
        this.fDefaultGenerateToJson = generateToJson;
    }

    protected boolean fSearchApiTelegramPackage = true;
    public boolean searchApiTelegramPackage() {
        return this.fSearchApiTelegramPackage;
    }
    public void setSearchApiTelegramPackage(boolean search) {
        this.fSearchApiTelegramPackage = search;
    }

    protected String fApiTelegramPackage;
    public String getApiTelegramPackage() {
        return this.fApiTelegramPackage;
    }
    public void setApiTelegramPackge(String apiTelegramPackge) {
        this.fApiTelegramPackage = apiTelegramPackge;
    }

    protected String fApiTelegramBase = "%";
    public String getApiTelegramBase() {
        return this.fApiTelegramBase;
    }
    public void setApiTelegramBase(String apiTelegramBase) {
        this.fApiTelegramBase = apiTelegramBase;
    }

    /**
     * An access object to the resource bundle for this product.
     */
    protected final BlancoRestGeneratorTsResourceBundle fBundle = new BlancoRestGeneratorTsResourceBundle();

    /**
     * Target programming language.
     */
    protected int fTargetLang = BlancoCgSupportedLang.TS;

    /**
     * A programming language expected for the input sheet.
     */
    protected int fSheetLang = BlancoCgSupportedLang.JAVA;

    public void setSheetLang(final int argSheetLang) {
        fSheetLang = argSheetLang;
    }

    /**
     * Style of the source code generation destination directory
     */
    protected boolean fTargetStyleAdvanced = false;
    public void setTargetStyleAdvanced(boolean argTargetStyleAdvanced) {
        this.fTargetStyleAdvanced = argTargetStyleAdvanced;
    }
    public boolean isTargetStyleAdvanced() {
        return this.fTargetStyleAdvanced;
    }

    /**
     * A factory for blancoCg to be used internally.
     */
    protected BlancoCgObjectFactory fCgFactory = null;

    /**
     * Source file information for blancoCg to be used internally.
     */
    protected BlancoCgSourceFile fCgSourceFile = null;

    /**
     * Class information for blancoCg to be used internally.
     */
    protected BlancoCgClass fCgClass = null;

    /**
     * Interface information for blancoCg to be used internally.
     */
    protected BlancoCgInterface fCgInterface = null;

    /**
     * Whether to perform name transformation for field and method names.
     *
     */
    protected boolean fNameAdjust = true;
    public void setNameAdjust(boolean argNameAdjust) {
        this.fNameAdjust = argNameAdjust;
    }

    /**
     * Base class for request telegrams.
     */
    protected String inputTelegramBase = null;
    /**
     * Base class for response telegrams.
     */
    protected String outputTelegramBase = null;

    /**
     * Character encoding of auto-generated source files.
     */
    protected String fEncoding = null;

    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    /**
     * Generates a telegram processing class.
     * @param argProcessStructure
     * @param argDirectoryTarget
     */
    protected void generateTelegramProcess(
            final BlancoRestGeneratorTsTelegramProcessStructure argProcessStructure,
            final File argDirectoryTarget) {


        /*
         * The output directory will be in the format specified by the targetStyle argument of the ant task.
         * To maintain compatibility with the previous version, it will be blanco/main if not specified.
         * by tueda, 2019/08/30
         */
        String strTarget = argDirectoryTarget
                .getAbsolutePath(); // advanced
        if (!this.isTargetStyleAdvanced()) {
            strTarget += "/main"; // legacy
        }
        final File fileBlancoMain = new File(strTarget);

        /* tueda DEBUG */
        if (this.isVerbose()) {
            System.out.println("BlancoRestGenerateTsXml2SourceFile#generateTelegramProcess START with argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
        }

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(argProcessStructure
                .getPackage(), "This source code has been generated by blanco Framework.");
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());
        // Creates a class.
        fCgClass = fCgFactory.createClass(BlancoRestGeneratorTsConstants.PREFIX_ABSTRACT + argProcessStructure.getName(),
                BlancoStringUtil.null2Blank(argProcessStructure
                        .getDescription()));
        fCgSourceFile.getClassList().add(fCgClass);
        // The telegram processing class is always public abstract.
        fCgClass.setAccess("public");
        fCgClass.setAbstract(true);
        // Inheritance
        if (BlancoStringUtil.null2Blank(argProcessStructure.getExtends())
                .length() > 0) {
            fCgClass.getExtendClassList().add(
                    fCgFactory.createType(argProcessStructure.getExtends()));
        }
        // Implementation
        for (int index = 0; index < argProcessStructure.getImplementsList()
                .size(); index++) {
            final String impl = (String) argProcessStructure.getImplementsList()
                    .get(index);
            fCgClass.getImplementInterfaceList().add(
                    fCgFactory.createType(impl));
        }
        // Sets the JavaDoc for the class.
        fCgClass.setDescription(argProcessStructure.getDescription());
        for (String line : argProcessStructure.getDescriptionList()) {
            fCgClass.getLangDoc().getDescriptionList().add(line);
        }

        /* Sets the annotation for the class. */
        List<String> annotationList = argProcessStructure.getAnnotationList();
        if (annotationList != null && annotationList.size() > 0) {
            fCgClass.getAnnotationList().addAll(argProcessStructure.getAnnotationList());
            /* tueda DEBUG */
            if (this.isVerbose()) {
                System.out.println("generateTelegramProcess : class annotation = " + argProcessStructure.getAnnotationList().get(0));
            }
        }

        /* In TypeScript, sets the header instead of import. */
        for (int index = 0; index < argProcessStructure.getHeaderList()
                .size(); index++) {
            final String header = (String) argProcessStructure.getHeaderList()
                    .get(index);
            fCgSourceFile.getHeaderList().add(header);
        }

        // Generates a service method.
        if (this.isCreateServiceMethod()) {
            createServiceMethods(argProcessStructure);
        } else {
            if (this.isVerbose()) {
                System.out.println("BlancoRestGeneratorTsXml2SourceFile#generateTelegramProcess: SKIP SERVICE METHOD!");
            }
        }

        // Overrides isAuthenticationRequired method.
        overrideAuthenticationRequired(argProcessStructure);

        // Generates a URL from a location and a web service ID.
        createLocationUrl(argProcessStructure);

        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getTsSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * Implements Service method.
     *
     * @param argProcessStructure
     */
    protected void createServiceMethods(
            final BlancoRestGeneratorTsTelegramProcessStructure argProcessStructure
    ) {

        Map<String, String> httpMethods = new HashMap<>();
        httpMethods.put(BlancoRestGeneratorTsConstants.HTTP_METHOD_GET, "Get");
        httpMethods.put(BlancoRestGeneratorTsConstants.HTTP_METHOD_POST, "Post");
        httpMethods.put(BlancoRestGeneratorTsConstants.HTTP_METHOD_PUT, "Put");
        httpMethods.put(BlancoRestGeneratorTsConstants.HTTP_METHOD_DELETE, "Delete");
        Map<String, String> telegramKind = new HashMap<>();
        telegramKind.put(BlancoRestGeneratorTsConstants.TELEGRAM_INPUT, "Request");
        telegramKind.put(BlancoRestGeneratorTsConstants.TELEGRAM_OUTPUT, "Response");

        for (Map.Entry<String, String> method : httpMethods.entrySet()) {
            HashMap<String, BlancoRestGeneratorTsTelegramStructure> telegrams = argProcessStructure.getListTelegrams().get(method.getKey());
            if (telegrams == null) {
                /* This method is not supported. */
                createExecuteMethodNotImplemented(method.getKey());
                createTelegramGenerateMethodNotImplemented(method, telegramKind);
            } else {
                createAbstractProcessMethod(telegrams);
                createExecuteMethod(telegrams, method);
                createTelegramGenerateMethod(telegrams, method, telegramKind);
            }
        }

        // Creates a factory method for a telegram instance.
        createTelegramGeneratorFactory(httpMethods, telegramKind);
    }

    protected void createAbstractProcessMethod(HashMap<String, BlancoRestGeneratorTsTelegramStructure> argTelegrams) {

        // Defines Processor.
        final BlancoCgMethod cgProcessorMethod = fCgFactory.createMethod(
                BlancoRestGeneratorTsConstants.API_PROCESS_METHOD, fBundle.getXml2sourceFileProcessorDescription());
        fCgClass.getMethodList().add(cgProcessorMethod);
        cgProcessorMethod.setAccess("protected");
        cgProcessorMethod.setAbstract(true);

        String requestId = argTelegrams.get(BlancoRestGeneratorTsConstants.TELEGRAM_INPUT).getName();
        String responseId = argTelegrams.get(BlancoRestGeneratorTsConstants.TELEGRAM_OUTPUT).getName();

        cgProcessorMethod.getParameterList().add(
                fCgFactory.createParameter("arg" + requestId, requestId,
                        fBundle.getXml2sourceFileProsessorArgLangdoc()));

        cgProcessorMethod.setReturn(fCgFactory.createReturn(responseId,
                fBundle.getXml2sourceFileProsessorReturnLangdoc()));

    }

    protected void createExecuteMethod(
            HashMap<String, BlancoRestGeneratorTsTelegramStructure> argTelegrams,
            Map.Entry<String, String> method) {

        final BlancoCgMethod cgExecutorMethod = fCgFactory.createMethod(
                BlancoRestGeneratorTsConstants.BASE_EXECUTOR_METHOD, fBundle.getXml2sourceFileExecutorDescription());
        fCgClass.getMethodList().add(cgExecutorMethod);
        cgExecutorMethod.setAccess("protected");

        /*
         * Uses the default telegram parent class to pass the type check.
         */
        String requestSuperId = BlancoRestGeneratorTsUtil.getDefaultRequestTelegramId(method.getKey());
        String responseSuperId = BlancoRestGeneratorTsUtil.getDefaultResponseTelegramId(method.getKey());
        /*
         * The original telegram class.
         */
        String requestId = argTelegrams.get(BlancoRestGeneratorTsConstants.TELEGRAM_INPUT).getName();
        String responseId = argTelegrams.get(BlancoRestGeneratorTsConstants.TELEGRAM_OUTPUT).getName();

        //        System.out.println("### requestId = " + requestId);
        cgExecutorMethod.getParameterList().add(
                fCgFactory.createParameter("arg" + requestSuperId, requestSuperId,
                        fBundle
                                .getXml2sourceFileExecutorArgLangdoc()));

        cgExecutorMethod.setReturn(fCgFactory.createReturn(responseSuperId,
                fBundle.getXml2sourceFileExecutorReturnLangdoc()));

        // Implements the method.
        final List<String> listLine = cgExecutorMethod.getLineList();

        listLine.add(
                responseSuperId + " " +
                BlancoCgLineUtil.getVariablePrefix(fTargetLang) + "ret" + responseId + " = "
                        + BlancoCgLineUtil.getVariablePrefix(fTargetLang) + "this." + BlancoRestGeneratorTsConstants.API_PROCESS_METHOD
                        + "( "  +  "("+ requestId + ")" + BlancoCgLineUtil.getVariablePrefix(fTargetLang) + "arg" + requestSuperId + " )"
                        + BlancoCgLineUtil.getTerminator(fTargetLang));

        listLine.add("\n");
        listLine.add("return "
                + BlancoCgLineUtil.getVariablePrefix(fTargetLang) + "ret" + responseId
                + BlancoCgLineUtil.getTerminator(fTargetLang));
    }

    /**
     * This is the execute method for when an unsupported method is called.
     *
     * @param method
     */
    protected void createExecuteMethodNotImplemented(String method) {
        final BlancoCgMethod cgExecutorMethod = fCgFactory.createMethod(
                BlancoRestGeneratorTsConstants.BASE_EXECUTOR_METHOD, fBundle.getXml2sourceFileExecutorDescription());
        fCgClass.getMethodList().add(cgExecutorMethod);
        cgExecutorMethod.setAccess("protected");
        final List<String> ListLine = cgExecutorMethod.getLineList();

        // Since there is no telegram definition, the default telegram name should be specified.
        String requestId = BlancoRestGeneratorTsUtil.getDefaultRequestTelegramId(method);
        String responseId = BlancoRestGeneratorTsUtil.getDefaultResponseTelegramId(method);

        /* If there is no telegram definition in the Excel sheet, it will come here, in which case uses the definition in the telegram processing sheet. */
        cgExecutorMethod.getParameterList().add(
                fCgFactory.createParameter("argRequest", requestId,
                        fBundle
                                .getXml2sourceFileExecutorArgLangdoc()));
        cgExecutorMethod.setReturn(fCgFactory.createReturn(responseId,
                fBundle.getXml2sourceFileExecutorReturnLangdoc()));

        /*
         * In TypeScript, exceptions need to inherit from Error. We'll just throw Error here.
         */
        //throw new BlancoRestException("GetMethod is not implemented in this api");
        // Implements the method.
        ListLine.add(
                "throw new " + BlancoRestGeneratorTsConstants.DEFAULT_EXCEPTION + "( " + BlancoCgLineUtil.getStringLiteralEnclosure(fTargetLang) +
                        fBundle.getBlancorestErrorMsg05(method) + BlancoCgLineUtil.getStringLiteralEnclosure(fTargetLang) + ")" + BlancoCgLineUtil.getTerminator(fTargetLang));

    }


    /**
     * TelegramGenerate method.
     *
     * @param method
     */
    protected void createTelegramGenerateMethod(
            HashMap<String, BlancoRestGeneratorTsTelegramStructure> argTelegrams,
            Map.Entry<String, String> method,
            Map<String, String> telegramKind) {
//        List<String> telegramNames = new ArrayList<>();
//        String requestId = argTelegrams.get(BlancoRestGeneratorTsConstants.TELEGRAM_INPUT).getName();
//        String responseId = argTelegrams.get(BlancoRestGeneratorTsConstants.TELEGRAM_OUTPUT).getName();
//
//        telegramNames.add(requestId);
//        telegramNames.add(responseId);

        for (Map.Entry<String, String> kind : telegramKind.entrySet()) {
            final String name = argTelegrams.get(kind.getKey()).getName();
            final BlancoCgMethod cgTelegramGenerateMethod = fCgFactory.createMethod(
                    String.format("getApi%s%s", method.getValue(), kind.getValue()), fBundle.getXml2sourceFileTelegramGeneratorDescription());
            fCgClass.getMethodList().add(cgTelegramGenerateMethod);

            cgTelegramGenerateMethod.setAccess("protected");
            cgTelegramGenerateMethod.setReturn(fCgFactory.createReturn(
                    name,
                    fBundle.getXml2sourceFileTelegramGeneratorReturnDescription()));
            final List<String> listLine = cgTelegramGenerateMethod.getLineList();

            // Implements the method.
            listLine.add("return new " + name + "()" + BlancoCgLineUtil.getTerminator(fTargetLang));
        }
    }


    /**
     * This is the TelegramGenerate method for when an unsupported method is called.
     *
     * @param method Target method.
     */
    protected void createTelegramGenerateMethodNotImplemented(Map.Entry<String, String> method, Map<String, String> telegramKind) {

        for (Map.Entry<String, String> kind : telegramKind.entrySet()) {
            final BlancoCgMethod cgTelegramGenerateMethod = fCgFactory.createMethod(
                    String.format("getApi%s%s", method.getValue(), kind.getValue()), fBundle.getXml2sourceFileTelegramGeneratorDescription());
            fCgClass.getMethodList().add(cgTelegramGenerateMethod);

            cgTelegramGenerateMethod.setAccess("protected");
            final List<String> listLine = cgTelegramGenerateMethod.getLineList();

            // Implements the method.
            listLine.add(
                    "throw new " + BlancoRestGeneratorTsConstants.DEFAULT_EXCEPTION + "( " + BlancoCgLineUtil.getStringLiteralEnclosure(fTargetLang) +
                            fBundle.getBlancorestErrorMsg05(method.getKey()) + BlancoCgLineUtil.getStringLiteralEnclosure(fTargetLang) + ")" + BlancoCgLineUtil.getTerminator(fTargetLang));
        }
    }


    /**
     * Factory method of the telegram instance.
     */
    protected void createTelegramGeneratorFactory(Map<String, String> httpMethods, Map<String, String> telegramKind) {
        final String lineTerminal =  BlancoCgLineUtil.getTerminator(fTargetLang);
        final String literalEnclosure = BlancoCgLineUtil.getStringLiteralEnclosure(fTargetLang);

        for (Map.Entry<String, String> kind : telegramKind.entrySet()) {
            final BlancoCgMethod cgTelegramGenerateMethod = fCgFactory.createMethod(
                    String.format("get%sTelegram", kind.getValue()), fBundle.getXml2sourceFileTelegramGeneratorFactoryDescription());
            fCgClass.getMethodList().add(cgTelegramGenerateMethod);
            cgTelegramGenerateMethod.getParameterList().add(
                    fCgFactory.createParameter("method", "String",
                            fBundle.getXml2sourceFileTelegramGeneratorFactoryArgMethodDescription()));
            cgTelegramGenerateMethod.setAccess("protected");
            cgTelegramGenerateMethod.setReturn(fCgFactory.createReturn(
                    BlancoRestGeneratorTsConstants.API_TELEGRAM_BASE,
                    fBundle.getXml2sourceFileTelegramGeneratorFactoryReturnDescription()));
            final List<String> listLine = cgTelegramGenerateMethod.getLineList();

            // Implements the method.
            listLine.add("let req: ApiTelegram" + lineTerminal);
            listLine.add("switch(method) {");
            for (Map.Entry<String, String> method : httpMethods.entrySet()) {
                listLine.add(String.format("case %s%s%s:", literalEnclosure, method.getValue().toUpperCase(), literalEnclosure));
                listLine.add(String.format("req = this.getApi%s%s()",
                        method.getValue().substring(0, 1).toUpperCase() + method.getValue().substring(1).toLowerCase(),
                        kind.getValue().substring(0, 1).toUpperCase() + kind.getValue().substring(1).toLowerCase()) + lineTerminal);
                listLine.add("break" + lineTerminal);
            }
            listLine.add("default:");
            listLine.add("throw new " + BlancoRestGeneratorTsConstants.DEFAULT_EXCEPTION + "(`" +
                    fBundle.getBlancorestErrorMsg06("${method}") + "`)" + lineTerminal);
            listLine.add("}");
            listLine.add("return req" + lineTerminal);
        }
    }


    protected void overrideAuthenticationRequired(BlancoRestGeneratorTsTelegramProcessStructure argStructure) {
        String methodName = BlancoRestGeneratorTsConstants.API_AUTHENTICATION_REQUIRED;

        final BlancoCgMethod cgAuthenticationRequiredMethod = fCgFactory.createMethod(
                methodName, fBundle.getXml2sourceFileAuthflagDescription());
        fCgClass.getMethodList().add(cgAuthenticationRequiredMethod);
        cgAuthenticationRequiredMethod.setAccess("public");

        cgAuthenticationRequiredMethod.setReturn(fCgFactory.createReturn("java.lang.boolean",
                fBundle.getXml2sourceFileAuthflagReturnLangdoc()));

        // Implements the method.
        final List<String> listLine = cgAuthenticationRequiredMethod.getLineList();

        String retval = "true";
        if (argStructure.getNoAuthentication()) {
            retval = "false";
        }

        listLine.add("return " + retval
                + BlancoCgLineUtil.getTerminator(fTargetLang));
    }

    protected void createLocationUrl(BlancoRestGeneratorTsTelegramProcessStructure argStructure) {
        // Creates a location URL.
        String locationUrl = argStructure.getLocation();
        if (locationUrl == null || locationUrl.trim().equals("/")) {
            locationUrl = "";
        } else {
            locationUrl = locationUrl.trim();
        }
        String sep = "";
        if (!locationUrl.endsWith("/")) {
            sep = "/";
        }
        locationUrl = locationUrl + sep + argStructure.getServiceId();

        final String fieldName = "_locationURL";
        final BlancoCgField field = fCgFactory.createField(fieldName,
                "string", null);

        fCgClass.getFieldList().add(field);
        field.setAccess("private");
        field.setNotnull(true);
        field.getLangDoc().getDescriptionList().add(
                BlancoCgSourceUtil.escapeStringAsLangDoc(BlancoCgSupportedLang.TS, fBundle.getXml2sourceFileFieldDefault(
                        locationUrl)));
        field.setDefault("\"" + locationUrl +"\"");

        // Generates a setter method.
        final BlancoCgMethod setterMethod = fCgFactory.createMethod(BlancoNameAdjuster.toParameterName(fieldName),
                fBundle.getXml2sourceFileSetLangdoc01(fieldName));
        fCgClass.getMethodList().add(setterMethod);

        setterMethod.setAccess("set");

        // JavaDoc configuration of the setter method.
        setterMethod.getLangDoc().getDescriptionList().add(
                fBundle.getXml2sourceFileSetLangdoc02("string"));

        // param configuration of the setter method.
        BlancoCgParameter param = fCgFactory.createParameter("arg"
                        + BlancoNameAdjuster.toClassName(fieldName),
                "java.lang.string",
                fBundle.getXml2sourceFileSetArgLangdoc(fieldName));
        param.setNotnull(true);

        setterMethod.getParameterList().add(param);

        // Implements the setter method.
        setterMethod.getLineList().add("this." + fieldName + " = "
                + "arg" + BlancoNameAdjuster.toClassName(fieldName) + ";");

        // Generates a getter method.
        final BlancoCgMethod getterMethod = fCgFactory.createMethod(BlancoNameAdjuster.toParameterName(fieldName),
                fBundle.getXml2sourceFileGetLangdoc01(fieldName));
        fCgClass.getMethodList().add(getterMethod);

        getterMethod.setAccess("get");

        // JavaDoc configuration of the getter method.
        getterMethod.getLangDoc().getDescriptionList().add(
                fBundle.getXml2sourceFileGetLangdoc02("string"));

        // Return value configuration of the getter method.
        getterMethod.setNotnull(true);
        getterMethod.setReturn(fCgFactory.createReturn("java.lang.string",
                fBundle.getXml2sourceFileGetReturnLangdoc(fieldName)));

        // Implements the getter method.
        getterMethod.getLineList().add("return this." + fieldName + ";");
    }

    protected void createRequestIdMethod(String methodName, String requestIdName, String packageName) {
        final BlancoCgMethod cgRequestIdMethod = fCgFactory.createMethod(
                methodName, fBundle.getXml2sourceFileRequestidDesctiption());
        fCgClass.getMethodList().add(cgRequestIdMethod);

        cgRequestIdMethod.setAccess("protected");

        List<String> annotators = new ArrayList<>();
        annotators.add("Override");
        cgRequestIdMethod.setAnnotationList(annotators);

        cgRequestIdMethod.setReturn(fCgFactory.createReturn("java.lang.string",
                fBundle.getXml2sourceFileRequestidReturnLangdoc()));

        /*
         * Implements the method.
         * Returns null if not defined.
         */
        final List<String> listLine = cgRequestIdMethod.getLineList();
        if(requestIdName == null) {
            listLine.add("return null" + BlancoCgLineUtil.getTerminator(fTargetLang));
        } else {
            listLine.add("return " + "\"" + packageName + "." + requestIdName + "\""
                    + BlancoCgLineUtil.getTerminator(fTargetLang));
        }
    }

    protected void createResponseIdMethod(String methodName, String responseIdName, String packageName) {

        final BlancoCgMethod cgResponseIdMethod = fCgFactory.createMethod(
                methodName, fBundle.getXml2sourceFileRequestidDesctiption());
        fCgClass.getMethodList().add(cgResponseIdMethod);
        cgResponseIdMethod.setAccess("protected");

        List<String> annotators = new ArrayList<>();
        annotators.add("Override");
        cgResponseIdMethod.setAnnotationList(annotators);

        cgResponseIdMethod.setReturn(fCgFactory.createReturn("java.lang.String",
                fBundle.getXml2sourceFileRequestidReturnLangdoc()));

        /*
         * Implements the method.
         * Returns null if not defined.
         */
        final List<String> listLine = cgResponseIdMethod.getLineList();
        if(responseIdName == null) {
            listLine.add("return null" + BlancoCgLineUtil.getTerminator(fTargetLang));
        } else {
            listLine.add("return " + "\"" + packageName + "." + responseIdName + "\""
                    + BlancoCgLineUtil.getTerminator(fTargetLang));
        }
    }

    /**
     * Generates a field in the class.
     * @param argClassStructure
     *            Class information.
     * @param argFieldStructure
     */
    protected void buildField(
            final BlancoRestGeneratorTsTelegramStructure argClassStructure,
            final BlancoRestGeneratorTsTelegramFieldStructure argFieldStructure) {

        /*
         * Field names are always transformed.
         */
        String fieldName = "f" + this.getFieldNameAdjustered(argFieldStructure);
        final BlancoCgField field = fCgFactory.createField(fieldName,
                argFieldStructure.getType(), null);

        fCgClass.getFieldList().add(field);

        /*
         * Supports Generic. If you don't set it here, it will not be set correctly because blancoCg assumes that "<>" is attached and trims the package part.
         */
        String generic = argFieldStructure.getGeneric();
        if (generic != null && generic.length() > 0) {
            field.getType().setGenerics(generic);
        }

        if (this.isVerbose()) {
            System.out.println("!!! type = " + argFieldStructure.getType());
            System.out.println("!!! generic = " + field.getType().getGenerics());
        }

        field.setAccess("private");
        /*
         * For the time being, final will not be supported.
         */
        field.setFinal(false);

        // Supports nullable.
        Boolean isNullable = argFieldStructure.getNullable();
        if (isNullable != null && isNullable) {
            field.setNotnull(false);
        } else {
            field.setNotnull(true);
        }

        // Sets the JavaDoc for the field.
        field.setDescription(argFieldStructure.getDescription());
        for (String line : argFieldStructure.getDescriptionList()) {
            field.getLangDoc().getDescriptionList().add(line);
        }

        /*
         * In TypeScript, the default value of a property is mandatory in principle.
         */
        String defaultRawValue = argFieldStructure.getDefault();
        if ((defaultRawValue == null ||defaultRawValue.length() <= 0)) {
            if (!isNullable) {
                throw new IllegalArgumentException(fBundle
                        .getXml2sourceFileErr009(argClassStructure.getName(), argFieldStructure.getName()));
            }
        } else {
            // Sets the default value for the field.
            field.getLangDoc().getDescriptionList().add(
                    BlancoCgSourceUtil.escapeStringAsLangDoc(BlancoCgSupportedLang.TS, fBundle.getXml2sourceFileFieldDefault(argFieldStructure
                            .getDefault())));
            // For the time being, the default value tansformation is always off, and the value in the definition document is used as is.
            field.setDefault(argFieldStructure.getDefault());

            /* Sets the annotation for the method. */
            List annotationList = argFieldStructure.getAnnotationList();
            if (annotationList != null && annotationList.size() > 0) {
                field.getAnnotationList().addAll(annotationList);
                if (this.isVerbose()) {
                    System.out.println("method annotation = " + field.getAnnotationList().get(0));
                }
            }
        }
    }

    /**
     * Generates a set method.
     *
     * @param argFieldStructure
     *            Field information.
     */
    protected void buildMethodSet(
            final BlancoRestGeneratorTsTelegramFieldStructure argFieldStructure) {
        // Generates a setter method for each field.
        final BlancoCgMethod method = fCgFactory.createMethod(argFieldStructure.getName(),
                fBundle.getXml2sourceFileSetLangdoc01(argFieldStructure
                        .getName()));
        fCgClass.getMethodList().add(method);

        method.setAccess("set");

        // JavaDoc configuration of the method.
        if (argFieldStructure.getDescription() != null) {
            method.getLangDoc().getDescriptionList().add(
                    fBundle.getXml2sourceFileSetLangdoc02(argFieldStructure
                            .getDescription()));
        }
        for (String line : argFieldStructure.getDescriptionList()) {
            method.getLangDoc().getDescriptionList().add(line);
        }

        BlancoCgParameter param = fCgFactory.createParameter("arg"
                        + getFieldNameAdjustered(argFieldStructure),
                argFieldStructure.getType(),
                fBundle.getXml2sourceFileSetArgLangdoc(argFieldStructure
                        .getName()));
        if (!argFieldStructure.getNullable()) {
            param.setNotnull(true);
        }

        String generic = argFieldStructure.getGeneric();
        if (generic != null && generic.length() > 0) {
            param.getType().setGenerics(generic);
        }
        method.getParameterList().add(param);

        // Implements the method.
        method.getLineList().add(
                "this.f"
                        + getFieldNameAdjustered(argFieldStructure)
                        + " = "
                        + "arg"
                        + getFieldNameAdjustered(argFieldStructure) + ";");
    }

    /**
     * Generates a get method.
     *
     * @param argFieldStructure
     *            Field information.
     */
    protected void buildMethodGet(
            final BlancoRestGeneratorTsTelegramFieldStructure argFieldStructure) {
        // Generates a getter method for each field.
        final BlancoCgMethod method = fCgFactory.createMethod(argFieldStructure.getName(),
                fBundle.getXml2sourceFileGetLangdoc01(argFieldStructure
                        .getName()));
        fCgClass.getMethodList().add(method);

        if (!argFieldStructure.getNullable()) {
            method.setNotnull(true);
        }

        method.setAccess("get");

        // JavaDoc configuration of the method.
        if (argFieldStructure.getDescription() != null) {
            method.getLangDoc().getDescriptionList().add(
                    fBundle.getXml2sourceFileGetLangdoc02(argFieldStructure
                            .getDescription()));
        }
        for (String line : argFieldStructure.getDescriptionList()) {
            method.getLangDoc().getDescriptionList().add(line);
        }
        if (argFieldStructure.getDefault() != null) {
            method.getLangDoc().getDescriptionList().add(
                    BlancoCgSourceUtil.escapeStringAsLangDoc(BlancoCgSupportedLang.JAVA, fBundle.getXml2sourceFileGetArgLangdoc(argFieldStructure
                            .getDefault())));
        }

        method.setReturn(fCgFactory.createReturn(argFieldStructure.getType(),
                fBundle.getXml2sourceFileGetReturnLangdoc(argFieldStructure.getName())));
        String generic = argFieldStructure.getGeneric();
        if (generic != null && generic.length() > 0) {
            method.getReturn().getType().setGenerics(generic);
        }

        // Implements the method.
        method.getLineList().add(
                "return this.f"
                        + this.getFieldNameAdjustered(argFieldStructure) + ";");
    }

    /**
     * Expands type method.
     *
     * @param argFieldStructure
     */
    protected void buildMethodType(
            final BlancoRestGeneratorTsTelegramFieldStructure argFieldStructure) {
        // Always adjusts field names.
        String fieldName = getFieldNameAdjustered(argFieldStructure);

        final BlancoCgMethod cgMethod = fCgFactory.createMethod("type"
                + fieldName, fBundle.getXml2sourceFileGetLangdoc01(argFieldStructure
                .getName()));
        fCgClass.getMethodList().add(cgMethod);
        cgMethod.setAccess("public");
        cgMethod.setStatic(true);

        cgMethod.getLangDoc().getDescriptionList().add(
                fBundle.getXml2sourceFileTypeLangdoc02(argFieldStructure.getType()));

        cgMethod.setReturn(fCgFactory.createReturn("string", fBundle
                .getXml2sourceFileTypeReturnLangdoc(argFieldStructure.getName())));

        if (BlancoStringUtil.null2Blank(argFieldStructure.getDescription()).length() > 0) {
            cgMethod.getLangDoc().getDescriptionList().add(
                    argFieldStructure.getDescription());
        }

        // Implements the method.
        final List<String> listLine = cgMethod.getLineList();

        /*
         * In TypeScript, removes package if it's given.
         */
        String methodType = BlancoRestGeneratorTsUtil
                .getSimpleClassName(argFieldStructure.getType());

        listLine
                .add("return "
                        + "\"" + methodType + "\""
                        + BlancoCgLineUtil.getTerminator(fTargetLang));
    }

    /**
     * Expands Generic method.
     * @param argFieldStructure
     */
    protected void buildMethodGeneric(
            final BlancoRestGeneratorTsTelegramFieldStructure argFieldStructure) {
        String fieldName = getFieldNameAdjustered(argFieldStructure);
        String fieldGeneric = argFieldStructure.getGeneric();

        if (BlancoStringUtil.null2Blank(fieldGeneric).length() == 0) {
            return;
        }

        final BlancoCgMethod cgMethod = fCgFactory.createMethod("generic"
                + fieldName, fBundle.getXml2sourceFileGetLangdoc01(argFieldStructure
                .getName()));
        fCgClass.getMethodList().add(cgMethod);
        cgMethod.setAccess("public");
        cgMethod.setStatic(true);

        cgMethod.getLangDoc().getDescriptionList().add(
                fBundle.getXml2sourceFileTypeLangdoc02(fieldGeneric));

        cgMethod.setReturn(fCgFactory.createReturn("string", fBundle
                .getXml2sourceFileTypeReturnLangdoc(argFieldStructure.getName())));

        if (BlancoStringUtil.null2Blank(argFieldStructure.getDescription()).length() > 0) {
            cgMethod.getLangDoc().getDescriptionList().add(
                    argFieldStructure.getDescription());
        }

        // Implements the method.
        final List<String> listLine = cgMethod.getLineList();

        /*
         * In TypeScript, removes package if it's given.
         */
        String genericType = BlancoRestGeneratorTsUtil
                .getSimpleClassName(argFieldStructure.getGeneric());

        listLine
                .add("return "
                        + "\"" + genericType + "\""
                        + BlancoCgLineUtil.getTerminator(fTargetLang));
    }

    protected void buildMethodTelegramId(
            final BlancoRestGeneratorTsTelegramStructure  argTelegramStructure
    ) {
        final BlancoCgMethod method = fCgFactory.createMethod("telegramId",
                "Returns the name of this telegram class as a string.");
        fCgClass.getMethodList().add(method);

        method.setReturn(fCgFactory.createReturn("string",
                "The class name of this telegram."));
        method.setNotnull(true);
        /*
         * Specified, but not valid in TypeScript.
         */
        method.setFinal(true);
        method.setAccess("get");

        final List<String> listLine = method.getLineList();
        listLine.add("return \"" + argTelegramStructure.getName() + "\";");
    }

    protected void buildMethodTelegramType(
            final BlancoRestGeneratorTsTelegramStructure  argTelegramStructure
    ) {
        final BlancoCgMethod method = fCgFactory.createMethod("telegramType",
                "Returns the kind of this telegram as a string.");
        fCgClass.getMethodList().add(method);

        method.setReturn(fCgFactory.createReturn("string",
                "The kind of this telegram."));
        method.setNotnull(true);
        /*
         * Specified, but not valid in TypeScript.
         */
        method.setFinal(true);
        method.setAccess("get");

        final List<String> listLine = method.getLineList();
        listLine.add("return \"" + argTelegramStructure.getTelegramType() + "\";");
    }

    protected void buildMethodTelegramMethod(
            final BlancoRestGeneratorTsTelegramStructure  argTelegramStructure
    ) {
        final BlancoCgMethod method = fCgFactory.createMethod("telegramMethod",
                "Returns the method of this telegram as a string.");
        fCgClass.getMethodList().add(method);

        method.setReturn(fCgFactory.createReturn("string",
                "The method of this telegram."));
        method.setNotnull(true);
        /*
         * Specified, but not valid in TypeScript.
         */
        method.setFinal(true);
        method.setAccess("get");

        final List<String> listLine = method.getLineList();
        listLine.add("return \"" + argTelegramStructure.getTelegramMethod() + "\";");
    }

    /**
     * Gets an adjusted field name.
     *
     * @param argFieldStructure
     *            Field information.
     * @return An adjusted field name.
     */
    protected String getFieldNameAdjustered(
            final BlancoRestGeneratorTsTelegramFieldStructure argFieldStructure) {
        return BlancoNameAdjuster.toClassName(argFieldStructure.getName());
    }
}
