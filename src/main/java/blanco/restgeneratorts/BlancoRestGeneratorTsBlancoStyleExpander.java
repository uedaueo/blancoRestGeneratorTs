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
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.commons.util.BlancoStringUtil;
import blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramFieldStructure;
import blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramProcessStructure;
import blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramStructure;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Generates class source code to process messages from "Message Definition" Excel format.
 *
 * This class is responsible for auto-generating source code from intermediate XML files.
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoRestGeneratorTsBlancoStyleExpander extends  BlancoRestGeneratorTsExpander {
    public void expand(final BlancoRestGeneratorTsTelegramProcessStructure argProcessStructure, final File argDirectoryTarget) {

        // FIrst, generates a telegram.
        Set<String> methodKeys = argProcessStructure.getListTelegrams().keySet(); // It should not be null because it is checked at time of parse.
        for (String methodKey : methodKeys) {
            HashMap<String, BlancoRestGeneratorTsTelegramStructure> kindMap =
                    argProcessStructure.getListTelegrams().get(methodKey);
            Set<String> kindKeys = kindMap.keySet(); // It should not be null because it is checked at time of parse.
            for (String kindKey : kindKeys) {
                generateTelegram(kindMap.get(kindKey), argDirectoryTarget);
            }
        }

        // Next, it generates the telegram processing.
        generateTelegramProcess(argProcessStructure, argDirectoryTarget);
    }

    /**
     * Generates a telegram class.
     *
     * @param argTelegramStructure
     * @param argDirectoryTarget
     */
    public void generateTelegram(
            final BlancoRestGeneratorTsTelegramStructure argTelegramStructure,
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
            System.out.println("BlancoRestGeneratorTsXml2SourceFile#generateTelegram SATR with argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
        }

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(argTelegramStructure
                .getPackage(), "This source code has been generated by blanco Framework.");
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());
        // Creates a class.
        fCgClass = fCgFactory.createClass(argTelegramStructure.getName(),
                BlancoStringUtil.null2Blank(argTelegramStructure
                        .getDescription()));
        fCgSourceFile.getClassList().add(fCgClass);
        // The telegram processing class is always public.
        fCgClass.setAccess("public");
        // Inheritance
        if (BlancoStringUtil.null2Blank(argTelegramStructure.getExtends())
                .length() > 0) {
            fCgClass.getExtendClassList().add(
                    fCgFactory.createType(argTelegramStructure.getExtends()));
        }
        // Implementation
        for (int index = 0; index < argTelegramStructure.getImplementsList()
                .size(); index++) {
            final String impl = (String) argTelegramStructure.getImplementsList()
                    .get(index);
            fCgClass.getImplementInterfaceList().add(
                    fCgFactory.createType(impl));
        }
        // Sets the JavaDoc for the class.
        fCgClass.setDescription(argTelegramStructure.getDescription());
        for (String line : argTelegramStructure.getDescriptionList()) {
            fCgClass.getLangDoc().getDescriptionList().add(line);
        }

        /* Sets the annotation for the class. */
        List annotationList = argTelegramStructure.getAnnotationList();
        if (annotationList != null && annotationList.size() > 0) {
            fCgClass.getAnnotationList().addAll(argTelegramStructure.getAnnotationList());
            /* tueda DEBUG */
            if (this.isVerbose()) {
                System.out.println("BlancoRestGeneratorTsXml2SourceFile#generateTelegram : class annotation = " + argTelegramStructure.getAnnotationList().get(0));
            }
        }

        /* In TypeScript, sets the header instead of import. */
        for (int index = 0; index < argTelegramStructure.getHeaderList()
                .size(); index++) {
            final String header = (String) argTelegramStructure.getHeaderList()
                    .get(index);
            fCgSourceFile.getHeaderList().add(header);
        }

        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorTsXml2SourceFile: Start create properties : " + argTelegramStructure.getName());
        }

        boolean toJson = false;
        // Telegram definition list.
        for (int indexField = 0; indexField < argTelegramStructure.getListField()
                .size(); indexField++) {
            // Processes each field.
            final BlancoRestGeneratorTsTelegramFieldStructure fieldStructure =
                    argTelegramStructure.getListField().get(indexField);

            // Performs exception processing if a required field is not set.
            if (fieldStructure.getName() == null) {
                throw new IllegalArgumentException(fBundle
                        .getXml2sourceFileErr004(argTelegramStructure.getName()));
            }
            if (fieldStructure.getType() == null) {
                throw new IllegalArgumentException(fBundle.getXml2sourceFileErr003(
                        argTelegramStructure.getName(), fieldStructure.getName()));
            }

            if (!toJson && !fieldStructure.getExcludeToJson()) {
                toJson = true;
            }

            if (this.isVerbose()) {
                System.out.println("property : " + fieldStructure.getName());
            }

            // Creates fields.
            buildField(argTelegramStructure, fieldStructure);

            // Creates a setter method.
            buildMethodSet(fieldStructure);

            // Creates a getter method.
            buildMethodGet(fieldStructure);

            // Creates a method that returns a Type string.
            buildMethodType(fieldStructure);

            // Creates a method that returns a Generic string.
            buildMethodGeneric(fieldStructure);

            // validation will be implemented later.
        }

        buildMethodTelegramId(argTelegramStructure);

        if (toJson && this.isDefaultGenerateToJson()) {
            buildMethodToJSON(argTelegramStructure);
        }

        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getTsSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }
}
