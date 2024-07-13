package blanco.restgeneratorts;

import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;
import blanco.restgeneratorts.resourcebundle.BlancoRestGeneratorTsResourceBundle;
import blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramFieldStructure;
import blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramProcessStructure;
import blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramStructure;
import blanco.valueobjectts.valueobject.BlancoValueObjectTsClassStructure;
import blanco.xml.bind.BlancoXmlBindingUtil;
import blanco.xml.bind.BlancoXmlUnmarshaller;
import blanco.xml.bind.valueobject.BlancoXmlDocument;
import blanco.xml.bind.valueobject.BlancoXmlElement;

import java.io.File;
import java.util.*;

public class BlancoRestGeneratorTsXmlParser {

    /**
     * An access object to the resource bundle for this product.
     */
    private final BlancoRestGeneratorTsResourceBundle fBundle = new BlancoRestGeneratorTsResourceBundle();


    private boolean fVerbose = false;
    public void setVerbose(boolean argVerbose) {
        this.fVerbose = argVerbose;
    }
    public boolean isVerbose() {
        return fVerbose;
    }

    private boolean fCreateServiceMethod = false;
    public void setCreateServiceMethod(boolean argCreateServiceMethod) {
        this.fCreateServiceMethod = argCreateServiceMethod;
    }
    public boolean isCreateServiceMethod() {
        return fCreateServiceMethod;
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
     * Parses an XML document in an intermediate XML file to get an array of value object information.
     *
     * @param argMetaXmlSourceFile
     *            An intermediate XML file.
     * @return An array of value object information obtained as a result of parsing.
     */
    public BlancoRestGeneratorTsTelegramProcessStructure[] parse(
            final File argMetaXmlSourceFile) {
        final BlancoXmlDocument documentMeta = new BlancoXmlUnmarshaller()
                .unmarshal(argMetaXmlSourceFile);
        if (documentMeta == null) {
            System.out.println("Fail to unmarshal XML.");
            return null;
        }

        // Gets the root element.
        final BlancoXmlElement elementRoot = BlancoXmlBindingUtil
                .getDocumentElement(documentMeta);
        if (elementRoot == null) {
            // The process is aborted if there is no root element.
            if (this.isVerbose()) {
                System.out.println("praser !!! NO ROOT ELEMENT !!!");
            }
            return null;
        }

        // First, it gets the list of telegrams.
        Map<String, BlancoRestGeneratorTsTelegramStructure> telegramStructureMap =
                parseTelegrams(elementRoot);

        if (telegramStructureMap.isEmpty()) {
            if (this.isVerbose()) {
                System.out.println("praser !!! NO telegramStructureMap !!!");
            }
            return null;
        }

        // Then, it gets the telegram processing.
        return parseTelegramProcess(elementRoot,telegramStructureMap);
    }

    public List<String> parseProcessListImport(List<BlancoRestGeneratorTsTelegramProcessStructure> argProcessStructures, String argProcessList, String processListBasedir) {

        String listPackageName = BlancoRestGeneratorTsUtil.getPackageName(argProcessList);
        String listClassName = BlancoRestGeneratorTsUtil.getSimpleClassName(argProcessList);

        /*
         * First, it gathers the information to import all the telegram processing.
         */
        Map<String, List<String>> importHeaderList = new HashMap<>();
        for (BlancoRestGeneratorTsTelegramProcessStructure processStructure : argProcessStructures) {
            String packageName = processStructure.getPackage();
            String processId = processStructure.getName();

            BlancoRestGeneratorTsUtil.makeImportHeaderList(packageName, processId, importHeaderList, processListBasedir, listPackageName);
        }

        /*
         * Then, it generates the import statement.
         */
        return this.parseHeaderList(null, importHeaderList);
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to create a list of telegram information that can be searched by telegram name.
     *
     * @param argElementRoot
     * @return
     */
    private Map<String, BlancoRestGeneratorTsTelegramStructure> parseTelegrams(final BlancoXmlElement argElementRoot) {

        Map <String, BlancoRestGeneratorTsTelegramStructure> telegramStructureMap = new HashMap<>();

        // Gets a list of sheets (Excel sheets).
        final List<BlancoXmlElement> listSheet = BlancoXmlBindingUtil
                .getElementsByTagName(argElementRoot, "sheet");
        final int sizeListSheet = listSheet.size();
        for (int index = 0; index < sizeListSheet; index++) {
            // Processes each sheet individually.
            final BlancoXmlElement elementSheet = (BlancoXmlElement) listSheet
                    .get(index);

            final Map<String, List<String>> importHeaderList = new HashMap<>();
            // Gets detailed information from the sheet.
            final BlancoRestGeneratorTsTelegramStructure telegramStructure = parseTelegramSheet(elementSheet, importHeaderList);

            // Stores the telegram information in a map with the telegram ID as the key.
            if (telegramStructure != null) {
                telegramStructureMap.put(telegramStructure.getName(), telegramStructure);
            }
        }
        /**
         *  Checking if Input and Output are paired is done when storing in the TelegramProcessStructure.
         */
        return telegramStructureMap;
    }


    /**
     * Parses an XML document in the form of an  intermediate XML file to get telegram information.
     * @param argElementSheet
     * @param argImportHeaderList
     * @return
     */
    private BlancoRestGeneratorTsTelegramStructure parseTelegramSheet(final BlancoXmlElement argElementSheet, Map<String, List<String>> argImportHeaderList) {

        final BlancoRestGeneratorTsTelegramStructure telegramStructure = new BlancoRestGeneratorTsTelegramStructure();

        // Gets the common information.
        final BlancoXmlElement elementCommon = BlancoXmlBindingUtil
                .getElement(argElementSheet, fBundle
                        .getMeta2xmlTelegramCommon());
        if (elementCommon == null) {
            // If there is no common, skips the processing of this sheet.
            // System.out.println("BlancoRestXmlSourceFile#process !!! NO COMMON !!!");
            return telegramStructure;
        }

        final String name = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "name");
        if (BlancoStringUtil.null2Blank(name).trim().length() == 0) {
            // If name is empty, skips the process.
            // System.out.println("BlancoRestXmlSourceFile#process !!! NO NAME !!!");
            return telegramStructure;
        }

        final String httpMethod = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "telegramMethod");
        if (BlancoStringUtil.null2Blank(httpMethod).trim().length() == 0) {
            // If httpMethod is empty, skips the process.
            // System.out.println("BlancoRestXmlSourceFile#process !!! NO NAME !!!");
            return telegramStructure;
        }

        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorTsXmlParser#parseTelegramSheet name = " + name);
        }

        // TelegramDefinition common
        this.parseTelegramCommon(elementCommon, telegramStructure);

        // TelegramDefinition inheritance
        this.parseTelegramExtends(argImportHeaderList, telegramStructure);

        // TelegramDefinition implementation
        final List<BlancoXmlElement> interfaceList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        fBundle.getMeta2xmlTelegramImplements());
        if (interfaceList != null && interfaceList.size() != 0) {
            final BlancoXmlElement elementInterfaceRoot = interfaceList.get(0);
            this.parseTelegramImplements(elementInterfaceRoot, argImportHeaderList, telegramStructure);
        }

        // Gets the list information.
        final List<BlancoXmlElement> listList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlTeregramList());
        if (listList != null && listList.size() != 0) {
            final BlancoXmlElement elementListRoot = listList.get(0);
            this.parseTelegramFields(elementListRoot, argImportHeaderList, telegramStructure);
        }

        // Gets the header information.
        final List<BlancoXmlElement> headerElementList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        fBundle.getMeta2xmlTelegramHeader());
        List<String> headerList = this.parseHeaderList(headerElementList, argImportHeaderList);
        if (headerList != null && headerList.size() > 0) {
            telegramStructure.getHeaderList().addAll(headerList);
        }

        return telegramStructure;
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition common".
     *
     * @param argElementCommon
     * @param argTelegramStructure
     */
    private void parseTelegramCommon(final BlancoXmlElement argElementCommon, final BlancoRestGeneratorTsTelegramStructure argTelegramStructure) {

        // Telegram ID
        argTelegramStructure.setName(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "name"));
        // Package
        argTelegramStructure.setPackage(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "package"));
        // Description
        argTelegramStructure.setDescription(BlancoXmlBindingUtil.getTextContent(argElementCommon, "description"));
        if (BlancoStringUtil.null2Blank(argTelegramStructure.getDescription())
                .length() > 0) {
            final String[] lines = BlancoNameUtil.splitString(argTelegramStructure
                    .getDescription(), '\n');
            for (int index = 0; index < lines.length; index++) {
                if (index == 0) {
                    argTelegramStructure.setDescription(lines[index]);
                } else {
                    // For a multi-line description, it will be split and stored.
                    // From the second line, it is assumed that character reference encoding has been properly implemented.
                    argTelegramStructure.getDescriptionList().add(lines[index]);
                }
            }
        }

        // Telegram type (Input/Output)
        argTelegramStructure.setTelegramType(BlancoXmlBindingUtil.getTextContent(argElementCommon, "type"));
        // HTTP method
        argTelegramStructure.setTelegramMethod(BlancoXmlBindingUtil.getTextContent(argElementCommon, "telegramMethod"));
        argTelegramStructure.setBasedir(BlancoXmlBindingUtil.getTextContent(argElementCommon, "basedir"));

        // telegram suffix
        argTelegramStructure.setTelegramSuffix(BlancoXmlBindingUtil.getTextContent(argElementCommon, "telegramSuffix"));

        // StatusCode
        if (BlancoRestGeneratorTsConstants.TELEGRAM_TYPE_ERROR.equals(argTelegramStructure.getTelegramType())) {
            String statusCode = BlancoXmlBindingUtil.getTextContent(argElementCommon, "statusCode");
            if (BlancoStringUtil.null2Blank(statusCode).trim().length() == 0) {
                throw new IllegalArgumentException(fBundle.getBlancorestTelegramStylePlainStatusCodeRequired());
            }
            argTelegramStructure.setStatusCode("\"" + statusCode + "\"");
        }

        // Additional Path
        argTelegramStructure.setAdditionalPath(BlancoXmlBindingUtil.getTextContent(argElementCommon, "additionalPath"));
        // paramPreferred
        argTelegramStructure.setParamPreferred(BlancoXmlBindingUtil.getTextContent(argElementCommon, "paramPreferred"));

        /* Supports for class annotation. */
        String classAnnotation = BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "annotation");
        if (BlancoStringUtil.null2Blank(classAnnotation).length() > 0) {
            String [] annotations = classAnnotation.split("\\\\\\\\");
            List<String> annotationList = new ArrayList<>(Arrays.asList(annotations));
            argTelegramStructure.setAnnotationList(annotationList);
        }

        argTelegramStructure.setCreateImportList("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "createImportList")));
    }

    /**
     * In blancoRestGenerator, a telegram always inherits from Api[Get|Post|Put|Delete]Telegram <- ApiTelegram.
     *
     * @param argImportHeaderList
     * @param argTelegramStructure
     */
    private void parseTelegramExtends(
            final Map<String, List<String>> argImportHeaderList,
            final BlancoRestGeneratorTsTelegramStructure argTelegramStructure) {

        // Null in a method is already checked in common.
        String method = argTelegramStructure.getTelegramMethod().toUpperCase();
        boolean isRequest = "Input".equals(argTelegramStructure.getTelegramType());
        String superClassId = "";
        if (BlancoRestGeneratorTsConstants.HTTP_METHOD_GET.endsWith(method)) {
            superClassId = isRequest ?
                    BlancoRestGeneratorTsConstants.DEFAULT_API_GET_REQUESTID :
                    BlancoRestGeneratorTsConstants.DEFAULT_API_GET_RESPONSEID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_POST.endsWith(method)) {
            superClassId = isRequest ?
                    BlancoRestGeneratorTsConstants.DEFAULT_API_POST_REQUESTID :
                    BlancoRestGeneratorTsConstants.DEFAULT_API_POST_RESPONSEID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_PUT.endsWith(method)) {
            superClassId = isRequest ?
                    BlancoRestGeneratorTsConstants.DEFAULT_API_PUT_REQUESTID :
                    BlancoRestGeneratorTsConstants.DEFAULT_API_PUT_RESPONSEID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_DELETE.endsWith(method)) {
            superClassId = isRequest ?
                    BlancoRestGeneratorTsConstants.DEFAULT_API_DELETE_REQUESTID :
                    BlancoRestGeneratorTsConstants.DEFAULT_API_DELETE_RESPONSEID;
        } else {
            throw new IllegalArgumentException("!!! NO SUCH METHOD !!! " + method);
        }
        /*
         * Searches for the package name for this class.
         */
        String packageName = null;
        String superClassIdCanon = superClassId;
        if (!this.searchApiTelegramPackage()) {
            packageName = this.getApiTelegramPackage();
            superClassIdCanon = packageName + "." + superClassId;
            if (isVerbose()) {
                System.out.println("Extends : " + superClassIdCanon);
            }
            argTelegramStructure.setExtends(superClassIdCanon);

            /*
             * Creates import information for TypeScript.
             */
            BlancoRestGeneratorTsUtil.makeImportHeaderList(packageName, superClassId, argImportHeaderList, this.getApiTelegramBase(), argTelegramStructure.getPackage());
        } else {
            BlancoValueObjectTsClassStructure voStructure = BlancoRestGeneratorTsUtil.objects.get(superClassId);
            if (voStructure != null) {
                packageName = voStructure.getPackage();
            }
            if (packageName != null) {
                superClassIdCanon = packageName + "." + superClassId;
            }
            if (isVerbose()) {
                System.out.println("Extends : " + superClassIdCanon);
            }
            argTelegramStructure.setExtends(superClassIdCanon);

            /*
             * Creates import information for TypeScript.
             */
            if (argTelegramStructure.getCreateImportList()) {
                BlancoRestGeneratorTsUtil.makeImportHeaderList(packageName, superClassId, argImportHeaderList, argTelegramStructure.getBasedir(), argTelegramStructure.getPackage());
            }
        }
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition implementation".
     *  @param argElementInterfaceRoot
     * @param argImportHeaderList
     * @param argTelegramStructure
     */
    private void parseTelegramImplements(
            final BlancoXmlElement argElementInterfaceRoot,
            final Map<String, List<String>> argImportHeaderList,
            final BlancoRestGeneratorTsTelegramStructure argTelegramStructure) {

        final List<BlancoXmlElement> listInterfaceChildNodes = BlancoXmlBindingUtil
                .getElementsByTagName(argElementInterfaceRoot, "interface");
        for (int index = 0;
             listInterfaceChildNodes != null &&
                     index < listInterfaceChildNodes.size();
             index++) {
            final BlancoXmlElement elementList = listInterfaceChildNodes
                    .get(index);

            final String interfaceName = BlancoXmlBindingUtil
                    .getTextContent(elementList, "name");
            if (interfaceName == null || interfaceName.trim().length() == 0) {
                continue;
            }

            argTelegramStructure.getImplementsList().add(
                    BlancoXmlBindingUtil
                            .getTextContent(elementList, "name"));
            /*
             * Creates import information for TypeScript.
             */
            if (argTelegramStructure.getCreateImportList()) {
                String packageName = BlancoRestGeneratorTsUtil.getPackageName(interfaceName);
                String className = BlancoRestGeneratorTsUtil.getSimpleClassName(interfaceName);
                if (packageName.length() == 0) {
                    /*
                     * Searches for the package name for this class.
                     */
                    BlancoValueObjectTsClassStructure voStructure = BlancoRestGeneratorTsUtil.objects.get(className);
                    if (voStructure != null) {
                        packageName = voStructure.getPackage();
                    }
                }
                BlancoRestGeneratorTsUtil.makeImportHeaderList(packageName, className, argImportHeaderList, argTelegramStructure.getBasedir(), argTelegramStructure.getPackage());
            }
        }
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition list".
     *  @param argElementListRoot
     * @param argImportHeaderList
     * @param argTelegramStructure
     */
    private void parseTelegramFields(
            final BlancoXmlElement argElementListRoot,
            final Map<String, List<String>> argImportHeaderList,
            final BlancoRestGeneratorTsTelegramStructure argTelegramStructure) {

        final List<BlancoXmlElement> listChildNodes = BlancoXmlBindingUtil
                .getElementsByTagName(argElementListRoot, "field");

        for (int index = 0; index < listChildNodes.size(); index++) {
            final BlancoXmlElement elementList = listChildNodes.get(index);
            final BlancoRestGeneratorTsTelegramFieldStructure fieldStructure = new BlancoRestGeneratorTsTelegramFieldStructure();

            fieldStructure.setNo(BlancoXmlBindingUtil.getTextContent(
                    elementList, "no"));
            fieldStructure.setName(BlancoXmlBindingUtil.getTextContent(
                    elementList, "fieldName"));

//            System.out.println("*** field name = " + fieldStructure.getName());

            if (fieldStructure.getName() == null
                    || fieldStructure.getName().trim().length() == 0) {
//                System.out.println("*** NO NAME SKIP!!! ");
                continue;
            }

            /* if telegramStyle is plain, statusCode is reserved. */
            if (BlancoRestGeneratorTsConstants.TELEGRAM_TYPE_ERROR.equals(argTelegramStructure.getTelegramType()) &&
                    BlancoRestGeneratorTsConstants.TELEGRAM_STATUS_CODE.equals(fieldStructure.getName())) {
                throw new IllegalArgumentException(fBundle.getBlancorestTelegramStylePlainStatusCodeReserved());
            }

            /*
             * Gets the type. The definition document assumes that PHP-like types are defined.
             * Changes the type name to TypeScript-style here.
             */
            String phpType = BlancoXmlBindingUtil.getTextContent(elementList, "fieldType");
            String targetType = phpType;
            if ("boolean".equalsIgnoreCase(phpType)) {
                targetType = "boolean";
            } else
            if ("integer".equalsIgnoreCase(phpType)) {
                targetType = "number";
            } else
            if ("double".equalsIgnoreCase(phpType)) {
                targetType = "number";
            } else
            if ("float".equalsIgnoreCase(phpType)) {
                targetType = "number";
            } else
            if ("string".equalsIgnoreCase(phpType)) {
                targetType = "string";
            } else
//                if ("datetime".equalsIgnoreCase(phpType)) {
//                    javaType = "java.util.Date";
//                } else
            if ("array".equalsIgnoreCase(phpType)) {
                targetType = "Array";
            } else
            if ("object".equalsIgnoreCase(phpType)) {
                targetType = "any";
            } else {
                /* Searches for a package with this name. */
                BlancoValueObjectTsClassStructure voStructure = BlancoRestGeneratorTsUtil.objects.get(phpType);
                String packageName = null;
                if (voStructure != null) {
                    packageName = voStructure.getPackage();
                }
                if (packageName == null) {
                    // Tries to isolate the package name.
                    String simpleName = BlancoRestGeneratorTsUtil.getSimpleClassName(phpType);
                    if (simpleName != null && !simpleName.equals(phpType)) {
                        packageName = BlancoRestGeneratorTsUtil.getPackageName(phpType);
                        phpType = simpleName;
                    }
                }
                if (packageName != null) {
                    targetType = packageName + "." + phpType;
                }

                /* Others are written as is. */
//                System.out.println("/* tueda */ Unknown php type: " + targetType);

                /*
                 * Creates import information for TypeScript.
                 */
                if (argTelegramStructure.getCreateImportList()) {
                    BlancoRestGeneratorTsUtil.makeImportHeaderList(packageName, phpType, argImportHeaderList, argTelegramStructure.getBasedir(), argTelegramStructure.getPackage());
                }
            }

            fieldStructure.setType(targetType);

            /* Supports for Generic. */
            String phpGeneric = BlancoXmlBindingUtil.getTextContent(elementList, "fieldGeneric");
            if (BlancoStringUtil.null2Blank(phpGeneric).length() != 0) {
                String targetGeneric = phpGeneric;
                if ("boolean".equalsIgnoreCase(phpGeneric)) {
                    targetGeneric = "boolean";
                } else
                if ("integer".equalsIgnoreCase(phpGeneric)) {
                    targetGeneric = "number";
                } else
                if ("double".equalsIgnoreCase(phpGeneric)) {
                    targetGeneric = "number";
                } else
                if ("float".equalsIgnoreCase(phpGeneric)) {
                    targetGeneric = "number";
                } else
                if ("string".equalsIgnoreCase(phpGeneric)) {
                    targetGeneric = "string";
                } else
//                    if ("datetime".equalsIgnoreCase(phpGeneric)) {
//                        javaGeneric = "java.util.Date";
//                    } else
                    if ("array".equalsIgnoreCase(phpGeneric)) {
                        throw new IllegalArgumentException("");
                    } else
                    if ("object".equalsIgnoreCase(phpGeneric)) {
                        targetGeneric = "any";
                    } else {
                        /* Searches for a package with this name. */
                        BlancoValueObjectTsClassStructure voStructure = BlancoRestGeneratorTsUtil.objects.get(phpGeneric);
                        String packageName = null;
                        if (voStructure != null) {
                            packageName = voStructure.getPackage();
                        }
                        if (packageName == null) {
                            // Tries to isolate the package name.
                            String simpleName = BlancoRestGeneratorTsUtil.getSimpleClassName(phpGeneric);
                            if (simpleName != null && !simpleName.equals(phpGeneric)) {
                                packageName = BlancoRestGeneratorTsUtil.getPackageName(phpGeneric);
                                phpGeneric = simpleName;
                            }
                        }
                        if (packageName != null) {
                            targetGeneric = packageName + "." + phpGeneric;
                        }

                        /* Others are written as is. */
//                        System.out.println("/* tueda */ Unknown php type: " + targetGeneric);

                        /*
                         * Creates import information for TypeScript.
                         */
                        if (argTelegramStructure.getCreateImportList()) {
                            BlancoRestGeneratorTsUtil.makeImportHeaderList(packageName, phpGeneric, argImportHeaderList, argTelegramStructure.getBasedir(), argTelegramStructure.getPackage());
                        }
                    }

                fieldStructure.setGeneric(targetGeneric);
                fieldStructure.setType(targetType);
            }

            /* Supports for annotation of method. */
            String methodAnnotation = BlancoXmlBindingUtil.getTextContent(elementList, "annotation");
            if (BlancoStringUtil.null2Blank(methodAnnotation).length() != 0) {
                String [] annotations = methodAnnotation.split("\\\\\\\\");
                List<String> annotationList = new ArrayList<>(Arrays.asList(annotations));

                fieldStructure.setAnnotationList(annotationList);
            }

            // Supports for Nullable.
            fieldStructure.setNullable("true".equals(BlancoXmlBindingUtil
                    .getTextContent(elementList, "nullable")));

            // Supports for toJSON.
            fieldStructure.setExcludeToJson("true".equals(BlancoXmlBindingUtil
                    .getTextContent(elementList, "excludeToJSON")));

            // Description
            fieldStructure.setDescription(BlancoXmlBindingUtil
                    .getTextContent(elementList, "fieldDescription"));
            final String[] lines = BlancoNameUtil.splitString(
                    fieldStructure.getDescription(), '\n');
            for (int indexLine = 0; indexLine < lines.length; indexLine++) {
                if (indexLine == 0) {
                    fieldStructure.setDescription(lines[indexLine]);
                } else {
                    // For a multi-line description, it will be split and stored.
                    // From the second line, assumes that character reference encoding has been properly implemented.
                    fieldStructure.getDescriptionList().add(
                            lines[indexLine]);
                }
            }

            // Default
            fieldStructure.setDefault(BlancoXmlBindingUtil.getTextContent(
                    elementList, "default"));
            // Length
            String strMinLength = BlancoXmlBindingUtil
                    .getTextContent(elementList, "minLength");
            if (strMinLength != null) {
                try {
                    int minLength = Integer.parseInt(strMinLength);
                    fieldStructure.setMinLength(minLength);

                } catch (NumberFormatException e) {
                    System.out.println(fBundle.getXml2sourceFileErr008(argTelegramStructure.getName(), fieldStructure.getName()));
                }
            }
            String strMaxLength = BlancoXmlBindingUtil
                    .getTextContent(elementList, "maxLength");
            if (strMaxLength != null) {
                try {
                    int maxLength = Integer.parseInt(strMaxLength);
                    fieldStructure.setMaxLength(maxLength);
                } catch (NumberFormatException e) {

                }
            }

            // Maximum and minimum
            fieldStructure.setMinInclusive(BlancoXmlBindingUtil
                    .getTextContent(elementList, "minInclusive"));
            fieldStructure.setMaxInclusive(BlancoXmlBindingUtil
                    .getTextContent(elementList, "maxInclusive"));
            // Regular expression
            fieldStructure.setPattern(BlancoXmlBindingUtil.getTextContent(
                    elementList, "pattern"));

            /* alias */
            String alias = BlancoXmlBindingUtil.getTextContent(elementList, "aliasTs");
            if (BlancoStringUtil.null2Blank(alias).trim().isEmpty()) {
                alias = BlancoXmlBindingUtil.getTextContent(elementList, "alias");
            }
            if (BlancoStringUtil.null2Blank(alias).trim().isEmpty()) {
                alias = fieldStructure.getName();
            }
            fieldStructure.setAlias(alias);
            /* queryKind */
            fieldStructure.setQueryKind(BlancoXmlBindingUtil.getTextContent(elementList, "queryKind"));
            if (BlancoStringUtil.null2Blank(fieldStructure.getQueryKind()).trim().length() > 0) {
                argTelegramStructure.setHasQueryParams(true);
            }

            if (fieldStructure.getType() == null
                    || fieldStructure.getType().trim().length() == 0) {
                throw new IllegalArgumentException(fBundle.getXml2sourceFileErr005(argTelegramStructure.getName(), fieldStructure.getName()));
            }

            argTelegramStructure.getListField().add(fieldStructure);
        }
    }

    /**
     *
     *
     * @param argHeaderElementList
     * @param argImportHeaderList
     * @return
     */
    private List<String> parseHeaderList(final List<BlancoXmlElement> argHeaderElementList, final Map<String, List<String>> argImportHeaderList) {
        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorTsXmlParser#parseHeaderList: Start parseHeaderList.");
        }

        List<String> headerList = new ArrayList<>();

        /*
         * Creates a list of header.
         * First, outputs what is written in the definition as it is.
         */
        if (argHeaderElementList != null && argHeaderElementList.size() > 0) {
            final BlancoXmlElement elementHeaderRoot = argHeaderElementList.get(0);
            final List<BlancoXmlElement> listHeaderChildNodes = BlancoXmlBindingUtil
                    .getElementsByTagName(elementHeaderRoot, "header");
            for (int index = 0; index < listHeaderChildNodes.size(); index++) {
                final BlancoXmlElement elementList = listHeaderChildNodes
                        .get(index);

                final String headerName = BlancoXmlBindingUtil
                        .getTextContent(elementList, "name");
                if (this.isVerbose()) {
                    System.out.println("BlancoRestGeneratorTsXmlParser#parseHeaderList header = " + headerName);
                }
                if (headerName == null || headerName.trim().length() == 0) {
                    continue;
                }
                headerList.add(
                        BlancoXmlBindingUtil
                                .getTextContent(elementList, "name"));
            }
        }

        /*
         * Next, outputs the auto-generated one.
         * The current method requires the following assumptions.
         *  * One class definition per file
         *  * Represents directories with Java/Kotlin style package notation in the definition sheet
         * TODO: Should it be possible to define the directory where the files are located on the definition sheet?
         */
        if (argImportHeaderList != null && argImportHeaderList.size() > 0) {
            Set<String> fromList = argImportHeaderList.keySet();
            for (String strFrom : fromList) {
                StringBuffer sb = new StringBuffer();
                sb.append("import { ");
                List<String> classNameList = argImportHeaderList.get(strFrom);
                int count = 0;
                for (String className : classNameList) {
                    if (count > 0) {
                        sb.append(", ");
                    }
                    sb.append(className);
                    count++;
                }
                if (count > 0) {
                    sb.append(" } from \"" + strFrom + "\"");
                    if (this.isVerbose()) {
                        System.out.println("BlancoRestGeneratorTsXmlParser#parseHeaderList import = " + sb.toString());
                    }
                    headerList.add(sb.toString());
                }
            }
        }

        return headerList;
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get an array of value object information.
     *
     * @param argElementRoot
     *            An intermediate XML file.
     * @return An array of value object information obtained as a result of parsing.
     */
    public BlancoRestGeneratorTsTelegramProcessStructure[] parseTelegramProcess(
            final BlancoXmlElement argElementRoot,
            final Map <String, BlancoRestGeneratorTsTelegramStructure> argTelegramStructureMap) {

        final List<BlancoRestGeneratorTsTelegramProcessStructure> processStructures = new ArrayList<>();

        // Gets a list of sheets (Excel sheets).
        final List<BlancoXmlElement> listSheet = BlancoXmlBindingUtil
                .getElementsByTagName(argElementRoot, "sheet");
        final int sizeListSheet = listSheet.size();
        for (int index = 0; index < sizeListSheet; index++) {
            // Processes each sheet individually.
            final BlancoXmlElement elementSheet = (BlancoXmlElement) listSheet
                    .get(index);

            final Map<String, List<String>> importHeaderList = new HashMap<>();
            // Gets detailed information from the sheet.
            final BlancoRestGeneratorTsTelegramProcessStructure structure =
                    parseProcessSheet(elementSheet, importHeaderList, argTelegramStructureMap);

            if (structure != null) {
                processStructures.add(structure);
            }
        }

        final BlancoRestGeneratorTsTelegramProcessStructure[] result = new BlancoRestGeneratorTsTelegramProcessStructure[processStructures
                .size()];
        processStructures.toArray(result);
        return result;
    }

    /**
     *
     * @param argElementSheet
     * @param argTelegramStructureMap
     * @return
     */
    private BlancoRestGeneratorTsTelegramProcessStructure parseProcessSheet(
            final BlancoXmlElement argElementSheet,
            final Map<String, List<String>> argImportHeaderList,
            final Map <String, BlancoRestGeneratorTsTelegramStructure> argTelegramStructureMap) {
        BlancoRestGeneratorTsTelegramProcessStructure processStructure = new BlancoRestGeneratorTsTelegramProcessStructure();

        // Gets the common information.
        final BlancoXmlElement elementCommon = BlancoXmlBindingUtil
                .getElement(argElementSheet, fBundle
                        .getMeta2xmlProcessCommon());
        if (elementCommon == null) {
            // If there is no common, skips the processing of this sheet.
            // System.out.println("BlancoRestXmlSourceFile#processTelegramProcess !!! NO COMMON !!!");
            return null;
        }

        final String name = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "name");

        if (BlancoStringUtil.null2Blank(name).trim().length() == 0) {
            // If name is empty, skips the process.
            // System.out.println("BlancoRestXmlSourceFile#processTelegramProcess !!! NO NAME !!!");
            return null;
        }

        if (argTelegramStructureMap == null || argTelegramStructureMap.isEmpty()) {
            System.out.println("parseProcessSheet !!! NO TelegramStructureMap for " + name + " !!!");
            return null;
        }

        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorTsXmlParser#parseProcessSheet name = " + name);
        }

        // TelegramProcessDefinition common
        parseProcessCommon(elementCommon, processStructure);

        // TelegramProcessDefinition inheritance
        final List<BlancoXmlElement> extendsList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlProcessExtends());
        if (extendsList != null && extendsList.size() != 0) {
            final BlancoXmlElement elementExtendsRoot = extendsList.get(0);
            parseProcessExtends(elementExtendsRoot, argImportHeaderList, processStructure);
        }

        // Adds ApiTelegram to HeaderList.
        this.addApiTelegramToHeaderList(argImportHeaderList, processStructure);

        // TelegramProcessDefinition implementation.
        final List<BlancoXmlElement> interfaceList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlProcessImplements());
        if (interfaceList != null && interfaceList.size() != 0) {
            final BlancoXmlElement elementInterfaceRoot = interfaceList.get(0);
            parseProcessImplements(elementInterfaceRoot, argImportHeaderList, processStructure);
        }

        // import field is ignored in TypeScript.

        /*
         * Determines the telegram ID from telegram process ID, and sets only the defined one to processStructure.
         * The telegram ID is determined by the following rule.
         * <Telegram process ID> + <Method> + <Request|Response>
         */
        if (!this.linkTelegramToProcess(processStructure.getName(), argTelegramStructureMap, processStructure, argImportHeaderList)) {
            /* The telegram is undefined or In and Out are not aligned. */
            System.out.println("!!! Invalid Telegram !!! for " + processStructure.getName());
            return null;
        }

        // Gets the header information.
        final List<BlancoXmlElement> headerElementList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        fBundle.getMeta2xmlProcessHeader());
        List<String> headerList = this.parseHeaderList(headerElementList, argImportHeaderList);
        if (headerList != null && headerList.size() > 0) {
            processStructure.getHeaderList().addAll(headerList);
        }

        return processStructure;
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition common".
     *
     * @param argElementCommon
     * @param argProcessStructure
     */
    private void parseProcessCommon(final BlancoXmlElement argElementCommon, final BlancoRestGeneratorTsTelegramProcessStructure argProcessStructure) {


        // Telegram process ID
        argProcessStructure.setName(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "name"));
        // Description
        argProcessStructure.setDescription(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "description"));
        if (BlancoStringUtil.null2Blank(argProcessStructure.getDescription())
                .length() > 0) {
            final String[] lines = BlancoNameUtil.splitString(argProcessStructure
                    .getDescription(), '\n');
            for (int index = 0; index < lines.length; index++) {
                if (index == 0) {
                    argProcessStructure.setDescription(lines[index]);
                } else {
                    // For a multi-line description, it will be split and stored.
                    // From the second line, assumes that character reference encoding has been properly implemented.
                    argProcessStructure.getDescriptionList().add(lines[index]);
                }
            }
        }

        // Web service ID
        argProcessStructure.setServiceId(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "webServiceId"));

        // Location
        argProcessStructure.setLocation(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "location"));

        // Package
        argProcessStructure.setPackage(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "package"));

        // Base directory
        argProcessStructure.setBasedir(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "basedir"));

        // Annotation
        String classAnnotation = BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "annotation");
        if (BlancoStringUtil.null2Blank(classAnnotation).length() > 0) {
            String [] annotations = classAnnotation.split("\\\\\\\\");
            List<String> annotationList = new ArrayList<>(Arrays.asList(annotations));
            argProcessStructure.setAnnotationList(annotationList);
        }

        // API that do not require authentication
        argProcessStructure.setNoAuthentication("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "noAuthentication")));

        // Auto-generation of import statements
        argProcessStructure.setCreateImportList("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "createImportList")));
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition inheritance".
     * @param argElementExtendsRoot
     * @param argImportHeaderList
     * @param argProcessStructure
     */
    private void parseProcessExtends(
            final BlancoXmlElement argElementExtendsRoot,
            final Map<String, List<String>> argImportHeaderList,
            final BlancoRestGeneratorTsTelegramProcessStructure argProcessStructure) {

        String className = BlancoXmlBindingUtil.getTextContent(argElementExtendsRoot, "name");
        if (className != null) {
            String classNameCanon = className;
            String packageName = BlancoXmlBindingUtil.getTextContent(argElementExtendsRoot, "package");
            if (packageName == null) {
                /*
                 * Searches for the package name for this name.
                 */
                BlancoValueObjectTsClassStructure voStructure = BlancoRestGeneratorTsUtil.objects.get(className);
                if (voStructure != null) {
                    packageName = voStructure.getPackage();
                }
            }
            if (packageName != null) {
                classNameCanon = packageName + "." + className;
            }
            if (isVerbose()) {
                System.out.println("Extends : " + classNameCanon);
            }
            argProcessStructure.setExtends(classNameCanon);

            /*
             * Creates import information for TypeScript.
             */
            if (argProcessStructure.getCreateImportList()) {
                BlancoRestGeneratorTsUtil.makeImportHeaderList(packageName, className, argImportHeaderList, argProcessStructure.getBasedir(), argProcessStructure.getPackage());
            }
        } else {
            System.out.println("/* Extends Skip */ className is not specified!!!");
        }
    }

    /**
     * Adds ApiTelegram to HeaderList.
     * @param argImportHeaderList
     * @param argProcessStructure
     */
    private void addApiTelegramToHeaderList(
            final Map<String, List<String>> argImportHeaderList,
            final BlancoRestGeneratorTsTelegramProcessStructure argProcessStructure) {
        String superClassId = BlancoRestGeneratorTsConstants.API_TELEGRAM_BASE;

        /*
         * Searches for the package name for this name.
         */
        String packageName = null;
        BlancoValueObjectTsClassStructure voStructure = BlancoRestGeneratorTsUtil.objects.get(superClassId);
        if (voStructure != null) {
            packageName = voStructure.getPackage();
        }
        String superClassIdCanon = superClassId;
        if (packageName != null) {
            superClassIdCanon = packageName + "." + superClassId;
        }
        if (isVerbose()) {
            System.out.println("addApiTelegramToHeaderList : " + superClassIdCanon);
        }

        if (argProcessStructure.getCreateImportList()) {
            BlancoRestGeneratorTsUtil.makeImportHeaderList(packageName, superClassId, argImportHeaderList, argProcessStructure.getBasedir(), argProcessStructure.getPackage());
        }
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition implementation".
     *  @param argElementInterfaceRoot
     * @param argImportHeaderList
     * @param argProcessStructure
     */
    private void parseProcessImplements(
            final BlancoXmlElement argElementInterfaceRoot,
            final Map<String, List<String>> argImportHeaderList,
            final BlancoRestGeneratorTsTelegramProcessStructure argProcessStructure) {

        final List<BlancoXmlElement> listInterfaceChildNodes = BlancoXmlBindingUtil
                .getElementsByTagName(argElementInterfaceRoot, "interface");
        for (int index = 0;
             listInterfaceChildNodes != null &&
                     index < listInterfaceChildNodes.size();
             index++) {
            final BlancoXmlElement elementList = listInterfaceChildNodes
                    .get(index);

            final String interfaceName = BlancoXmlBindingUtil
                    .getTextContent(elementList, "name");
            if (interfaceName == null || interfaceName.trim().length() == 0) {
                continue;
            }

            argProcessStructure.getImplementsList().add(
                    BlancoXmlBindingUtil
                            .getTextContent(elementList, "name"));
            /*
             * Creates import information for TypeScript.
             */
            if (argProcessStructure.getCreateImportList()) {
                String packageName = BlancoRestGeneratorTsUtil.getPackageName(interfaceName);
                String className = BlancoRestGeneratorTsUtil.getSimpleClassName(interfaceName);
                if (packageName.length() == 0) {
                    /*
                     * Searches for the package name for this name.
                     */
                    BlancoValueObjectTsClassStructure voStructure = BlancoRestGeneratorTsUtil.objects.get(className);
                    if (voStructure != null) {
                        packageName = voStructure.getPackage();
                    }
                }
                BlancoRestGeneratorTsUtil.makeImportHeaderList(packageName, className, argImportHeaderList, argProcessStructure.getBasedir(), argProcessStructure.getPackage());
            }
        }
    }

    /**
     * Determines the telegram ID from telegram process ID, and sets only the defined one to processStructure.
     * The telegram ID is determined by the following rule.
     * <Telegram process ID> + <Method> + <Request|Response>
     *
     * @param argProcessId
     * @param argTelegramStructureMap
     * @param argProcessStructure
     * @return
     */
    private boolean linkTelegramToProcess(
            final String argProcessId,
            final Map<String, BlancoRestGeneratorTsTelegramStructure> argTelegramStructureMap,
            final BlancoRestGeneratorTsTelegramProcessStructure argProcessStructure,
            final Map<String, List<String>> argImportHeaderList
            ) {
        boolean found = false;

        Map<String, String> httpMethods = new HashMap<>();
        httpMethods.put(BlancoRestGeneratorTsConstants.HTTP_METHOD_GET, "Get");
        httpMethods.put(BlancoRestGeneratorTsConstants.HTTP_METHOD_POST, "Post");
        httpMethods.put(BlancoRestGeneratorTsConstants.HTTP_METHOD_PUT, "Put");
        httpMethods.put(BlancoRestGeneratorTsConstants.HTTP_METHOD_DELETE, "Delete");
        Map<String, String> telegramKind = new HashMap<>();
        telegramKind.put(BlancoRestGeneratorTsConstants.TELEGRAM_INPUT, "Request");
        telegramKind.put(BlancoRestGeneratorTsConstants.TELEGRAM_OUTPUT, "Response");

        Set<String> methodKeys = httpMethods.keySet();
        for (String methodKey : methodKeys) {
            String method = httpMethods.get(methodKey);
            Set<String> kindKeys = telegramKind.keySet();
            HashMap<String, BlancoRestGeneratorTsTelegramStructure> telegrams = new HashMap<>();
            for (String kindKey : kindKeys) {
                String kind = telegramKind.get(kindKey);
                String telegramId = argProcessId + method + kind;

                BlancoRestGeneratorTsTelegramStructure telegramStructure =
                        argTelegramStructureMap.get(telegramId);
                if (telegramStructure != null) {
                    telegrams.put(kindKey, telegramStructure);
                }
            }

            if (argProcessStructure.getCreateImportList() && this.isCreateServiceMethod()) {
                /*
                 * Creates import information for the default telegram class.
                 */
                // Request
                String defaultTelegramId = BlancoRestGeneratorTsUtil.getDefaultRequestTelegramId(method);
                String defaultTelegramPackage = null;
                BlancoValueObjectTsClassStructure voStructure = BlancoRestGeneratorTsUtil.objects.get(defaultTelegramId);
                if (voStructure != null) {
                    defaultTelegramPackage = voStructure.getPackage();
                }
                BlancoRestGeneratorTsUtil.makeImportHeaderList(defaultTelegramPackage, defaultTelegramId, argImportHeaderList, argProcessStructure.getBasedir(), argProcessStructure.getPackage());
                // Response
                defaultTelegramId = BlancoRestGeneratorTsUtil.getDefaultResponseTelegramId(method);
                defaultTelegramPackage = null;
                voStructure = BlancoRestGeneratorTsUtil.objects.get(defaultTelegramId);
                if (voStructure != null) {
                    defaultTelegramPackage = voStructure.getPackage();
                }
                BlancoRestGeneratorTsUtil.makeImportHeaderList(defaultTelegramPackage, defaultTelegramId, argImportHeaderList, argProcessStructure.getBasedir(), argProcessStructure.getPackage());
            }

            if (telegrams.size() == 0) {
                continue;
            }
            if (telegrams.size() != 2) {
                /* In and Out are not aligned. */
                return false;
            }
            argProcessStructure.getListTelegrams().put(methodKey, telegrams);
            found = true;

            /* Search Error telegrams on telegramType is plain. */
            if (BlancoRestGeneratorTsUtil.isTelegramStylePlain) {
                String telegramIdPrefix = argProcessId + method + "Error";
                List<BlancoRestGeneratorTsTelegramStructure> listErrors = BlancoRestGeneratorTsUtil.searchTelegramsStartWith(telegramIdPrefix, argTelegramStructureMap);
                if (listErrors.size() > 0) {
                    argProcessStructure.getErrorTelegrams().put(methodKey, listErrors);
                }
            }

            /*
             * Creates import information for TypeScript.
             */
            if (argProcessStructure.getCreateImportList() && this.isCreateServiceMethod()) {
                Set<String> kinds = telegrams.keySet();
                for (String kind : kinds) {
                    BlancoRestGeneratorTsTelegramStructure telegram = telegrams.get(kind);
                    BlancoRestGeneratorTsUtil.makeImportHeaderList(telegram.getPackage(), telegram.getName(), argImportHeaderList, argProcessStructure.getBasedir(), argProcessStructure.getPackage());
                }
            }
        }

        return found;
    }
}
