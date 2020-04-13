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
     * このプロダクトのリソースバンドルへのアクセスオブジェクト。
     */
    private final BlancoRestGeneratorTsResourceBundle fBundle = new BlancoRestGeneratorTsResourceBundle();


    private boolean fVerbose = false;
    public void setVerbose(boolean argVerbose) {
        this.fVerbose = argVerbose;
    }
    public boolean isVerbose() {
        return fVerbose;
    }


    /**
     * 中間XMLファイルのXMLドキュメントをパースして、バリューオブジェクト情報の配列を取得します。
     *
     * @param argMetaXmlSourceFile
     *            中間XMLファイル。
     * @return パースの結果得られたバリューオブジェクト情報の配列。
     */
    public BlancoRestGeneratorTsTelegramProcessStructure[] parse(
            final File argMetaXmlSourceFile) {
        final BlancoXmlDocument documentMeta = new BlancoXmlUnmarshaller()
                .unmarshal(argMetaXmlSourceFile);
        if (documentMeta == null) {
            System.out.println("Fail to unmarshal XML.");
            return null;
        }

        // ルートエレメントを取得します。
        final BlancoXmlElement elementRoot = BlancoXmlBindingUtil
                .getDocumentElement(documentMeta);
        if (elementRoot == null) {
            // ルートエレメントが無い場合には処理中断します。
            if (this.isVerbose()) {
                System.out.println("praser !!! NO ROOT ELEMENT !!!");
            }
            return null;
        }

        // まず電文の一覧を取得します。
        Map<String, BlancoRestGeneratorTsTelegramStructure> telegramStructureMap =
                parseTelegrams(elementRoot);

        if (telegramStructureMap.isEmpty()) {
            if (this.isVerbose()) {
                System.out.println("praser !!! NO telegramStructureMap !!!");
            }
            return null;
        }

        // 次に電文処理を取得します。
        return parseTelegramProcess(elementRoot,telegramStructureMap);
    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、電文名で検索可能な電文情報の一覧を作成します。
     *
     * @param argElementRoot
     * @return
     */
    private Map<String, BlancoRestGeneratorTsTelegramStructure> parseTelegrams(final BlancoXmlElement argElementRoot) {

        Map <String, BlancoRestGeneratorTsTelegramStructure> telegramStructureMap = new HashMap<>();

        // sheet(Excelシート)のリストを取得します。
        final List<BlancoXmlElement> listSheet = BlancoXmlBindingUtil
                .getElementsByTagName(argElementRoot, "sheet");
        final int sizeListSheet = listSheet.size();
        for (int index = 0; index < sizeListSheet; index++) {
            // おのおののシートを処理します。
            final BlancoXmlElement elementSheet = (BlancoXmlElement) listSheet
                    .get(index);

            final Map<String, List<String>> importHeaderList = new HashMap<>();
            // シートから詳細な情報を取得します。
            final BlancoRestGeneratorTsTelegramStructure telegramStructure = parseTelegramSheet(elementSheet, importHeaderList);

            // 電文情報を電文IDをキーにMapに格納する
            if (telegramStructure != null) {
                telegramStructureMap.put(telegramStructure.getName(), telegramStructure);
            }
        }
        /**
         *  InputとOutputが対になっているかのチェックは
         *  TelegramProcessStructureへの格納時にを行う
         */
        return telegramStructureMap;
    }


    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、電文情報を取得します。
     * @param argElementSheet
     * @param argImportHeaderList
     * @return
     */
    private BlancoRestGeneratorTsTelegramStructure parseTelegramSheet(final BlancoXmlElement argElementSheet, Map<String, List<String>> argImportHeaderList) {

        final BlancoRestGeneratorTsTelegramStructure telegramStructure = new BlancoRestGeneratorTsTelegramStructure();

        // 共通情報を取得します。
        final BlancoXmlElement elementCommon = BlancoXmlBindingUtil
                .getElement(argElementSheet, fBundle
                        .getMeta2xmlTelegramCommon());
        if (elementCommon == null) {
            // commonが無い場合には、このシートの処理をスキップします。
            // System.out.println("BlancoRestXmlSourceFile#process !!! NO COMMON !!!");
            return telegramStructure;
        }

        final String name = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "name");
        if (BlancoStringUtil.null2Blank(name).trim().length() == 0) {
            // nameが空の場合には処理をスキップします。
            // System.out.println("BlancoRestXmlSourceFile#process !!! NO NAME !!!");
            return telegramStructure;
        }

        final String httpMethod = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "telegramMethod");
        if (BlancoStringUtil.null2Blank(httpMethod).trim().length() == 0) {
            // httpMethodが空の場合には処理をスキップします。
            // System.out.println("BlancoRestXmlSourceFile#process !!! NO NAME !!!");
            return telegramStructure;
        }

        if (this.isVerbose()) {
            System.out.println("/* tueda */ XmlParser#parseTelegramSheet name = " + name);
        }

        // 電文定義・共通
        this.parseTelegramCommon(elementCommon, telegramStructure);

        // 電文定義・継承
        this.parseTelegramExtends(argImportHeaderList, telegramStructure);

        // 電文定義・実装
        final List<BlancoXmlElement> interfaceList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        fBundle.getMeta2xmlTelegramImplements());
        if (interfaceList != null && interfaceList.size() != 0) {
            final BlancoXmlElement elementInterfaceRoot = interfaceList.get(0);
            this.parseTelegramImplements(elementInterfaceRoot, argImportHeaderList, telegramStructure);
        }

        // 一覧情報を取得します。
        final List<BlancoXmlElement> listList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlTeregramList());
        if (listList != null && listList.size() != 0) {
            final BlancoXmlElement elementListRoot = listList.get(0);
            this.parseTelegramFields(elementListRoot, argImportHeaderList, telegramStructure);
        }

        // ヘッダ情報を取得します。
        final List<BlancoXmlElement> headerElementList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        fBundle.getMeta2xmlTelegramHeader());
        if (headerElementList != null && headerElementList.size() != 0) {
            List<String> headerList = this.parseHeaderList(headerElementList, argImportHeaderList);
            telegramStructure.getHeaderList().addAll(headerList);
        }

        return telegramStructure;
    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、「電文定義・共通」を取得します。
     *
     * @param argElementCommon
     * @param argTelegramStructure
     */
    private void parseTelegramCommon(final BlancoXmlElement argElementCommon, final BlancoRestGeneratorTsTelegramStructure argTelegramStructure) {

        // 電文ID
        argTelegramStructure.setName(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "name"));
        // パッケージ
        argTelegramStructure.setPackage(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "package"));
        // 説明
        argTelegramStructure.setDescription(BlancoXmlBindingUtil.getTextContent(argElementCommon, "description"));
        // 電文種類 Input/Output
        argTelegramStructure.setTelegramType(BlancoXmlBindingUtil.getTextContent(argElementCommon, "type"));
        // HTTP メソッド
        argTelegramStructure.setTelegramMethod(BlancoXmlBindingUtil.getTextContent(argElementCommon, "telegramMethod"));
        argTelegramStructure.setBasedir(BlancoXmlBindingUtil.getTextContent(argElementCommon, "basedir"));

        /* クラスの annotation に対応 */
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
     * blancoRestGenerator では、電文は常に
     * Api[Get|Post|Put|Delete]Telegram <- ApiTelegram
     * を継承します。
     *
     * @param argImportHeaderList
     * @param argTelegramStructure
     */
    private void parseTelegramExtends(
            final Map<String, List<String>> argImportHeaderList,
            final BlancoRestGeneratorTsTelegramStructure argTelegramStructure) {

        // method の NULL check は common で済み
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
         * このクラスのパッケージ名を探す
         */
        String packageName = null;
        BlancoValueObjectTsClassStructure voStructure = BlancoRestGeneratorTsObjectsInfo.objects.get(superClassId);
        if (voStructure != null) {
            packageName = voStructure.getPackage();
        }
        String superClassIdCanon = superClassId;
        if (packageName != null) {
            superClassIdCanon = packageName + "." + superClassId;
        }
        if (isVerbose()) {
            System.out.println("/* tueda */ Extends : " + superClassIdCanon);
        }
        argTelegramStructure.setExtends(superClassIdCanon);

        /*
         * TypeScript 用 import 情報の作成
         */
        if (argTelegramStructure.getCreateImportList()) {
            this.makeImportHeaderList(packageName, superClassId, argImportHeaderList, argTelegramStructure.getBasedir(), argTelegramStructure.getPackage());
        }
    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、「電文定義・実装」を取得します。
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
             * TypeScript 用 import 情報の作成
             */
            if (argTelegramStructure.getCreateImportList()) {
                String packageName = this.getPackageName(interfaceName);
                String className = this.getSimpleClassName(interfaceName);
                if (packageName.length() == 0) {
                    /*
                     * このクラスのパッケージ名を探す
                     */
                    BlancoValueObjectTsClassStructure voStructure = BlancoRestGeneratorTsObjectsInfo.objects.get(className);
                    if (voStructure != null) {
                        packageName = voStructure.getPackage();
                    }
                }
                this.makeImportHeaderList(packageName, className, argImportHeaderList, argTelegramStructure.getBasedir(), argTelegramStructure.getPackage());
            }
        }
    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、「電文定義・一覧」を取得します。
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

            /*
             * 型の取得。定義書にはphp風な型が定義されている前提。
             * ここで TypeScript 風の型名に変えておく
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
                /* この名前の package を探す */
                BlancoValueObjectTsClassStructure voStructure = BlancoRestGeneratorTsObjectsInfo.objects.get(phpType);
                String packageName = null;
                if (voStructure != null) {
                    packageName = voStructure.getPackage();
                }
                if (packageName == null) {
                    // package 名の分離を試みる
                    String simpleName = this.getSimpleClassName(phpType);
                    if (simpleName != null && !simpleName.equals(phpType)) {
                        packageName = this.getPackageName(phpType);
                        phpType = simpleName;
                    }
                }
                if (packageName != null) {
                    targetType = packageName + "." + phpType;
                }

                /* その他はそのまま記述する */
                System.out.println("/* tueda */ Unknown php type: " + targetType);

                /*
                 * TypeScript 用 import 情報の作成
                 */
                if (argTelegramStructure.getCreateImportList()) {
                    this.makeImportHeaderList(packageName, phpType, argImportHeaderList, argTelegramStructure.getBasedir(), argTelegramStructure.getPackage());
                }
            }

            fieldStructure.setType(targetType);

            /* Generic に対応 */
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
                        /* この名前の package を探す */
                        BlancoValueObjectTsClassStructure voStructure = BlancoRestGeneratorTsObjectsInfo.objects.get(phpGeneric);
                        String packageName = null;
                        if (voStructure != null) {
                            packageName = voStructure.getPackage();
                        }
                        if (packageName == null) {
                            // package 名の分離を試みる
                            String simpleName = this.getSimpleClassName(phpGeneric);
                            if (simpleName != null && !simpleName.equals(phpGeneric)) {
                                packageName = this.getPackageName(phpGeneric);
                                phpGeneric = simpleName;
                            }
                        }
                        if (packageName != null) {
                            targetGeneric = packageName + "." + phpGeneric;
                        }

                        /* その他はそのまま記述する */
                        System.out.println("/* tueda */ Unknown php type: " + targetGeneric);

                        /*
                         * TypeScript 用 import 情報の作成
                         */
                        if (argTelegramStructure.getCreateImportList()) {
                            this.makeImportHeaderList(packageName, phpGeneric, argImportHeaderList, argTelegramStructure.getBasedir(), argTelegramStructure.getPackage());
                        }
                    }

                fieldStructure.setGeneric(targetGeneric);
                fieldStructure.setType(targetType);
            }

            /* method の annnotation に対応 */
            String methodAnnotation = BlancoXmlBindingUtil.getTextContent(elementList, "annotation");
            if (BlancoStringUtil.null2Blank(methodAnnotation).length() != 0) {
                String [] annotations = methodAnnotation.split("\\\\\\\\");
                List<String> annotationList = new ArrayList<>(Arrays.asList(annotations));

                fieldStructure.setAnnotationList(annotationList);
            }

            // Nullable に対応
            fieldStructure.setNullable("true".equals(BlancoXmlBindingUtil
                    .getTextContent(elementList, "nullable")));
            // 説明
            fieldStructure.setDescription(BlancoXmlBindingUtil
                    .getTextContent(elementList, "fieldDescription"));
            final String[] lines = BlancoNameUtil.splitString(
                    fieldStructure.getDescription(), '\n');
            for (int indexLine = 0; indexLine < lines.length; indexLine++) {
                if (indexLine == 0) {
                    fieldStructure.setDescription(lines[indexLine]);
                } else {
                    // 複数行の description については、これを分割して格納します。
                    // ２行目からは、適切に文字参照エンコーディングが実施されているものと仮定します。
                    fieldStructure.getDescriptionList().add(
                            lines[indexLine]);
                }
            }

            // デフォルト
            fieldStructure.setDefault(BlancoXmlBindingUtil.getTextContent(
                    elementList, "default"));
            // 長さ
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

            // 最大最小
            fieldStructure.setMinInclusive(BlancoXmlBindingUtil
                    .getTextContent(elementList, "minInclusive"));
            fieldStructure.setMaxInclusive(BlancoXmlBindingUtil
                    .getTextContent(elementList, "maxInclusive"));
            // 正規表現
            fieldStructure.setPattern(BlancoXmlBindingUtil.getTextContent(
                    elementList, "pattern"));

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

        List<String> headerList = new ArrayList<>();

        /*
         * header の一覧作成
         * まず、定義書に書かれたものをそのまま出力します。
         */
        if (argHeaderElementList != null && argHeaderElementList.size() != 0) {
            final BlancoXmlElement elementHeaderRoot = argHeaderElementList.get(0);
            final List<BlancoXmlElement> listHeaderChildNodes = BlancoXmlBindingUtil
                    .getElementsByTagName(elementHeaderRoot, "header");
            for (int index = 0; index < listHeaderChildNodes.size(); index++) {
                final BlancoXmlElement elementList = listHeaderChildNodes
                        .get(index);

                final String headerName = BlancoXmlBindingUtil
                        .getTextContent(elementList, "name");
                if (this.isVerbose()) {
                    System.out.println("/* tueda */ header = " + headerName);
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
         * 次に、自動生成されたものを出力します。
         * 現在の方式だと、以下の前提が必要。
         *  * 1ファイルに1クラスの定義
         *  * 定義シートでは Java/kotlin 式の package 表記でディレクトリを表現
         * TODO: 定義シート上にファイルの配置ディレクトリを定義できるようにすべし？
         */
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
                headerList.add(sb.toString());
            }
        }

        return headerList;
    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、バリューオブジェクト情報の配列を取得します。
     *
     * @param argElementRoot
     *            中間XMLファイルのXMLドキュメント。
     * @return パースの結果得られたバリューオブジェクト情報の配列。
     */
    public BlancoRestGeneratorTsTelegramProcessStructure[] parseTelegramProcess(
            final BlancoXmlElement argElementRoot,
            final Map <String, BlancoRestGeneratorTsTelegramStructure> argTelegramStructureMap) {

        final List<BlancoRestGeneratorTsTelegramProcessStructure> processStructures = new ArrayList<>();

        // sheet(Excelシート)のリストを取得します。
        final List<BlancoXmlElement> listSheet = BlancoXmlBindingUtil
                .getElementsByTagName(argElementRoot, "sheet");
        final int sizeListSheet = listSheet.size();
        for (int index = 0; index < sizeListSheet; index++) {
            // おのおののシートを処理します。
            final BlancoXmlElement elementSheet = (BlancoXmlElement) listSheet
                    .get(index);

            final Map<String, List<String>> importHeaderList = new HashMap<>();
            // シートから詳細な情報を取得します。
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

        // 共通情報を取得します。
        final BlancoXmlElement elementCommon = BlancoXmlBindingUtil
                .getElement(argElementSheet, fBundle
                        .getMeta2xmlProcessCommon());
        if (elementCommon == null) {
            // commonが無い場合には、このシートの処理をスキップします。
            // System.out.println("BlancoRestXmlSourceFile#processTelegramProcess !!! NO COMMON !!!");
            return null;
        }

        final String name = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "name");

        if (BlancoStringUtil.null2Blank(name).trim().length() == 0) {
            // nameが空の場合には処理をスキップします。
            // System.out.println("BlancoRestXmlSourceFile#processTelegramProcess !!! NO NAME !!!");
            return null;
        }

        if (argTelegramStructureMap == null || argTelegramStructureMap.isEmpty()) {
            System.out.println("parseProcessSheet !!! NO TelegramStructureMap for " + name + " !!!");
            return null;
        }

        if (this.isVerbose()) {
            System.out.println("parseProcessSheet name = " + name);
        }

        // 電文処理定義・共通
        parseProcessCommon(elementCommon, processStructure);

        // 電文処理定義・継承
        final List<BlancoXmlElement> extendsList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlProcessExtends());
        if (extendsList != null && extendsList.size() != 0) {
            final BlancoXmlElement elementExtendsRoot = extendsList.get(0);
            parseProcessExtends(elementExtendsRoot, argImportHeaderList, processStructure);
        }

        // 電文処理定義・実装
        final List<BlancoXmlElement> interfaceList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlProcessImplements());
        if (interfaceList != null && interfaceList.size() != 0) {
            final BlancoXmlElement elementInterfaceRoot = interfaceList.get(0);
            parseProcessImplements(elementInterfaceRoot, argImportHeaderList, processStructure);
        }

        // TypeScript では import 欄は無視します

        /*
         * 電文書IDから電文IDを決定し、定義されているもののみをprocessStructureに設定します。
         * 電文IDは以下のルールで決定されます。
         * <電文処理ID> + <Method> + <Request|Response>
         */
        if (!this.linkTelegramToProcess(processStructure.getName(), argTelegramStructureMap, processStructure)) {
            /* 電文が未定義またはInとOutが揃っていない */
            System.out.println("!!! Invalid Telegram !!! for " + processStructure.getName());
            return null;
        }

        // ヘッダ情報を取得します。
        final List<BlancoXmlElement> headerElementList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        fBundle.getMeta2xmlProcessHeader());
        if (headerElementList != null && headerElementList.size() != 0) {
            List<String> headerList = this.parseHeaderList(headerElementList, argImportHeaderList);
            processStructure.getHeaderList().addAll(headerList);
        }

        return processStructure;
    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、「電文定義・共通」を取得します。
     *
     * @param argElementCommon
     * @param argProcessStructure
     */
    private void parseProcessCommon(final BlancoXmlElement argElementCommon, final BlancoRestGeneratorTsTelegramProcessStructure argProcessStructure) {


        // 電文処理ID
        argProcessStructure.setName(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "name"));
        // 説明
        argProcessStructure.setDescription(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "description"));
        if (BlancoStringUtil.null2Blank(argProcessStructure.getDescription())
                .length() > 0) {
            final String[] lines = BlancoNameUtil.splitString(argProcessStructure
                    .getDescription(), '\n');
            for (int index = 0; index < lines.length; index++) {
                if (index == 0) {
                    argProcessStructure.setDescription(lines[index]);
                }
            }
        }

        // WebサービスID
        argProcessStructure.setPackage(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "webServiceId"));

        // ロケーション
        argProcessStructure.setPackage(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "location"));

        // パッケージ
        argProcessStructure.setPackage(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "package"));

        // 配置ディレクトリ
        argProcessStructure.setBasedir(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "basedir"));

        // アノテーション
        String classAnnotation = BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "annotation");
        if (BlancoStringUtil.null2Blank(classAnnotation).length() > 0) {
            String [] annotations = classAnnotation.split("\\\\\\\\");
            List<String> annotationList = new ArrayList<>(Arrays.asList(annotations));
            argProcessStructure.setAnnotationList(annotationList);
        }

        // 認証が不要なAPI
        argProcessStructure.setCreateImportList("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "noAuthentication")));

        // import 文の自動生成
        argProcessStructure.setCreateImportList("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "createImportList")));
    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、「電文定義・継承」を取得します。
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
                 * このクラスのパッケージ名を探す
                 */
                BlancoValueObjectTsClassStructure voStructure = BlancoRestGeneratorTsObjectsInfo.objects.get(className);
                if (voStructure != null) {
                    packageName = voStructure.getPackage();
                }
            }
            if (packageName != null) {
                classNameCanon = packageName + "." + className;
            }
            if (isVerbose()) {
                System.out.println("/* tueda */ Extends : " + classNameCanon);
            }
            argProcessStructure.setExtends(classNameCanon);

            /*
             * TypeScript 用 import 情報の作成
             */
            if (argProcessStructure.getCreateImportList()) {
                this.makeImportHeaderList(packageName, className, argImportHeaderList, argProcessStructure.getBasedir(), argProcessStructure.getPackage());
            }
        } else {
            System.out.println("/* Extends Skip */ className is not specified!!!");
        }
    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、「電文定義・実装」を取得します。
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
             * TypeScript 用 import 情報の作成
             */
            if (argProcessStructure.getCreateImportList()) {
                String packageName = this.getPackageName(interfaceName);
                String className = this.getSimpleClassName(interfaceName);
                if (packageName.length() == 0) {
                    /*
                     * このクラスのパッケージ名を探す
                     */
                    BlancoValueObjectTsClassStructure voStructure = BlancoRestGeneratorTsObjectsInfo.objects.get(className);
                    if (voStructure != null) {
                        packageName = voStructure.getPackage();
                    }
                }
                this.makeImportHeaderList(packageName, className, argImportHeaderList, argProcessStructure.getBasedir(), argProcessStructure.getPackage());
            }
        }
    }

    /**
     * 電文書IDから電文IDを決定し、定義されているもののみをprocessStructureに設定します。
     * 電文IDは以下のルールで決定されます。
     * <電文処理ID> + <Method> + <Request|Response>
     *
     * @param processId
     * @param telegramStructureMap
     * @param processStructure
     * @return
     */
    private boolean linkTelegramToProcess(
            final String processId,
            final Map<String, BlancoRestGeneratorTsTelegramStructure> telegramStructureMap,
            final BlancoRestGeneratorTsTelegramProcessStructure processStructure
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
                String telegramId = processId + method + kind;

                BlancoRestGeneratorTsTelegramStructure telegramStructure =
                        telegramStructureMap.get(telegramId);
                if (telegramStructure != null) {
                    telegrams.put(kindKey, telegramStructure);
                }
            }
            if (telegrams.size() == 0) {
                continue;
            }
            if (telegrams.size() != 2) {
                /* In と Out が揃っていない */
                return false;
            }
            processStructure.getListTelegrams().put(methodKey, telegrams);
            found = true;
        }

        return found;
    }

    /**
     * インポート文を生成する
     * @param argPackageName
     * @param argClassName
     * @param argImportHeaderList
     * @param argBasedir
     * @param argTelegramPackage
     */
    private void makeImportHeaderList(
            final String argPackageName,
            final String argClassName,
            final Map<String, List<String>> argImportHeaderList,
            final String argBasedir,
            final String argTelegramPackage) {
        if (argClassName == null || argClassName.length() == 0) {
            System.out.println("/* tueda */ className is not specified. SKIP.");
            return;
        }
        String importFrom = "./" + argClassName;
        if (argPackageName != null &&
                argPackageName.length() != 0 &&
                argPackageName.equals(argTelegramPackage) != true) {
            String classNameCanon = argPackageName.replace('.', '/') + "/" + argClassName;

            String basedir = "";
            if (argBasedir != null) {
                basedir = argBasedir;
            }
            importFrom = "@" + basedir + "/" + classNameCanon;
        }

        List<String> importClassList = argImportHeaderList.get(importFrom);
        if (importClassList == null) {
            importClassList = new ArrayList<>();
            argImportHeaderList.put(importFrom, importClassList);
        }
        boolean isMatch = false;
        for (String myClass : importClassList) {
            if (argClassName.equals(myClass)) {
                isMatch = true;
                break;
            }
        }
        if (!isMatch) {
            importClassList.add(argClassName);
            if (this.isVerbose()) {
                System.out.println("/* tueda */ new import { " + argClassName + " } from \"" + importFrom + "\"");
            }
        }
    }

    /**
     * Make canonical classname into Simple.
     *
     * @param argClassNameCanon
     * @return simpleName
     */
    private String getSimpleClassName(final String argClassNameCanon) {
        if (argClassNameCanon == null) {
            return "";
        }

        String simpleName = "";
        final int findLastDot = argClassNameCanon.lastIndexOf('.');
        if (findLastDot == -1) {
            simpleName = argClassNameCanon;
        } else if (findLastDot != argClassNameCanon.length() - 1) {
            simpleName = argClassNameCanon.substring(findLastDot + 1);
        }
        return simpleName;
    }

    /**
     * Make canonical classname into packageName
     *
     * @param argClassNameCanon
     * @return
     */
    private String getPackageName(final String argClassNameCanon) {
        if (argClassNameCanon == null) {
            return "";
        }

        String simpleName = "";
        final int findLastDot = argClassNameCanon.lastIndexOf('.');
        if (findLastDot > 0) {
            simpleName = argClassNameCanon.substring(0, findLastDot);
        }
        return simpleName;
    }
}
