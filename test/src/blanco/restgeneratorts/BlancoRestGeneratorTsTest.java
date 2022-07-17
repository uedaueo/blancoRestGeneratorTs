/*
 * blanco Framework
 * Copyright (C) 2004-2020 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.restgeneratorts;

import blanco.restgeneratorts.task.BlancoRestGeneratorTsProcessImpl;
import blanco.restgeneratorts.task.valueobject.BlancoRestGeneratorTsProcessInput;
import blanco.valueobjectts.task.BlancoValueObjectTsProcessImpl;
import blanco.valueobjectts.task.valueobject.BlancoValueObjectTsProcessInput;
import org.junit.Test;

import java.io.IOException;

/**
 * Java言語用の生成試験。
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoRestGeneratorTsTest {

    @Test
    public void testBlancoRestGenerator() {
        /*
         * まず ValueObject を生成します。
         */
        BlancoValueObjectTsProcessInput inputValueObject = new BlancoValueObjectTsProcessInput();
        inputValueObject.setMetadir("meta/telegrams");
        inputValueObject.setEncoding("UTF-8");
        inputValueObject.setSheetType("php");
        inputValueObject.setTmpdir("tmpTest");
        inputValueObject.setTargetdir("sample/blanco");
        inputValueObject.setTargetStyle("maven");
        inputValueObject.setVerbose(true);
        inputValueObject.setTabs(2);
        inputValueObject.setGenerateToJson(true);
        inputValueObject.setLineSeparator("CRLF");

        BlancoValueObjectTsProcessImpl impleValueObject = new BlancoValueObjectTsProcessImpl();
        try {
            impleValueObject.execute(inputValueObject);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * もうひとつ、 ValueObject を生成します。
         */
        inputValueObject = new BlancoValueObjectTsProcessInput();
        inputValueObject.setMetadir("meta/sampleObjects");
        inputValueObject.setEncoding("UTF-8");
        inputValueObject.setSheetType("php");
        inputValueObject.setTmpdir("tmpSample");
        inputValueObject.setTargetdir("sample/blanco");
        inputValueObject.setTargetStyle("maven");
        inputValueObject.setVerbose(true);
        inputValueObject.setTabs(2);
        inputValueObject.setGenerateToJson(true);
        inputValueObject.setLineSeparator("CRLF");

        impleValueObject = new BlancoValueObjectTsProcessImpl();
        try {
            impleValueObject.execute(inputValueObject);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * 3つ目の、 ValueObject を生成します。
         */
        inputValueObject = new BlancoValueObjectTsProcessInput();
        inputValueObject.setMetadir("meta/sampleObjects2");
        inputValueObject.setEncoding("UTF-8");
        inputValueObject.setSheetType("php");
        inputValueObject.setTmpdir("tmpSample2");
        inputValueObject.setTargetdir("sample/blanco");
        inputValueObject.setTargetStyle("maven");
        inputValueObject.setVerbose(true);
        inputValueObject.setTabs(2);
        inputValueObject.setGenerateToJson(true);
        inputValueObject.setLineSeparator("CRLF");

        impleValueObject = new BlancoValueObjectTsProcessImpl();
        try {
            impleValueObject.execute(inputValueObject);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * その後、電文と電文処理を生成します。
         */
        BlancoRestGeneratorTsProcessInput inputRestGenerator = new BlancoRestGeneratorTsProcessInput();
        inputRestGenerator.setMetadir("meta/api");
        inputRestGenerator.setEncoding("UTF-8");
        inputRestGenerator.setSheetType("php");
        inputRestGenerator.setTmpdir("tmpTest");
        inputRestGenerator.setTargetdir("sample/blanco");
        inputRestGenerator.setTargetStyle("maven");
        inputRestGenerator.setNameAdjust(true);
        inputRestGenerator.setClient(true);
        inputRestGenerator.setVerbose(true);
        inputRestGenerator.setTabs(2);
        inputRestGenerator.setGenerateToJson(true);
        inputRestGenerator.setProcesslist("blanco.sample.common.TelegramProcessList");
        inputRestGenerator.setProcesslistBase("%");
        inputRestGenerator.setLineSeparator("CR");
        inputRestGenerator.setSearchTmpdir("tmpSample,tmpSample2");
        inputRestGenerator.setApiTelegramPackage("api.telegrams");
        inputRestGenerator.setApiTelegramBase("%%");

        BlancoRestGeneratorTsProcessImpl imple = new BlancoRestGeneratorTsProcessImpl();
        imple.execute(inputRestGenerator);
    }

}
