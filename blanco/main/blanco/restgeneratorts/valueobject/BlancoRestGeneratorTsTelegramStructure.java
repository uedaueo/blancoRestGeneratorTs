package blanco.restgeneratorts.valueobject;

import java.util.ArrayList;
import java.util.List;

/**
 * BlancoRestGeneratorTsのなかで利用されるValueObjectです。
 */
public class BlancoRestGeneratorTsTelegramStructure {
    /**
     * 電文ID
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * メッセージ定義の説明を記載します。
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * クラスの補助説明です。文字参照エンコード済みの値を格納してください。
     *
     * フィールド: [descriptionList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fDescriptionList = new java.util.ArrayList<java.lang.String>();

    /**
     * 電文種類
     *
     * フィールド: [telegramType]。
     */
    private String fTelegramType;

    /**
     * 電文のMETHOD
     *
     * フィールド: [telegramMethod]。
     */
    private String fTelegramMethod;

    /**
     * 電文の親クラス名
     *
     * フィールド: [telegramSuperClass]。
     */
    private String fTelegramSuperClass;

    /**
     * 名前空間
     *
     * フィールド: [namespace]。
     */
    private String fNamespace;

    /**
     * パッケージ名を指定します。必須項目です。
     *
     * フィールド: [package]。
     */
    private String fPackage;

    /**
     * 本番時にファイルを配置する歳のベースディレクトリ。主にTypeScriptのimport文生成時に使用する事を想定しています。
     *
     * フィールド: [basedir]。
     */
    private String fBasedir;

    /**
     * クラスのアノテーションを指定します。
     *
     * フィールド: [annotationList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fAnnotationList = new java.util.ArrayList<java.lang.String>();

    /**
     * TypeScript 独自。blancoで一括生成されたクラスについて、import文を自動生成します。
     *
     * フィールド: [createImportList]。
     * デフォルト: [true]。
     */
    private Boolean fCreateImportList = true;

    /**
     * 継承するクラスを指定します。
     *
     * フィールド: [extends]。
     */
    private String fExtends;

    /**
     * 実装するインタフェース(java.lang.String)の一覧。
     *
     * フィールド: [implementsList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fImplementsList = new java.util.ArrayList<java.lang.String>();

    /**
     * importを指定します。
     *
     * フィールド: [importList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fImportList = new java.util.ArrayList<java.lang.String>();

    /**
     * source コードの先頭に書かれるコード群です。
     *
     * フィールド: [headerList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fHeaderList = new java.util.ArrayList<java.lang.String>();

    /**
     * フィールドを保持するリスト
     *
     * フィールド: [listField]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramFieldStructure&gt;()]。
     */
    private ArrayList<BlancoRestGeneratorTsTelegramFieldStructure> fListField = new java.util.ArrayList<blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramFieldStructure>();

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [電文ID]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [電文ID]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [メッセージ定義の説明を記載します。]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [メッセージ定義の説明を記載します。]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [descriptionList] の値を設定します。
     *
     * フィールドの説明: [クラスの補助説明です。文字参照エンコード済みの値を格納してください。]。
     *
     * @param argDescriptionList フィールド[descriptionList]に設定する値。
     */
    public void setDescriptionList(final List<String> argDescriptionList) {
        fDescriptionList = argDescriptionList;
    }

    /**
     * フィールド [descriptionList] の値を取得します。
     *
     * フィールドの説明: [クラスの補助説明です。文字参照エンコード済みの値を格納してください。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[descriptionList]から取得した値。
     */
    public List<String> getDescriptionList() {
        return fDescriptionList;
    }

    /**
     * フィールド [telegramType] の値を設定します。
     *
     * フィールドの説明: [電文種類]。
     *
     * @param argTelegramType フィールド[telegramType]に設定する値。
     */
    public void setTelegramType(final String argTelegramType) {
        fTelegramType = argTelegramType;
    }

    /**
     * フィールド [telegramType] の値を取得します。
     *
     * フィールドの説明: [電文種類]。
     *
     * @return フィールド[telegramType]から取得した値。
     */
    public String getTelegramType() {
        return fTelegramType;
    }

    /**
     * フィールド [telegramMethod] の値を設定します。
     *
     * フィールドの説明: [電文のMETHOD]。
     *
     * @param argTelegramMethod フィールド[telegramMethod]に設定する値。
     */
    public void setTelegramMethod(final String argTelegramMethod) {
        fTelegramMethod = argTelegramMethod;
    }

    /**
     * フィールド [telegramMethod] の値を取得します。
     *
     * フィールドの説明: [電文のMETHOD]。
     *
     * @return フィールド[telegramMethod]から取得した値。
     */
    public String getTelegramMethod() {
        return fTelegramMethod;
    }

    /**
     * フィールド [telegramSuperClass] の値を設定します。
     *
     * フィールドの説明: [電文の親クラス名]。
     *
     * @param argTelegramSuperClass フィールド[telegramSuperClass]に設定する値。
     */
    public void setTelegramSuperClass(final String argTelegramSuperClass) {
        fTelegramSuperClass = argTelegramSuperClass;
    }

    /**
     * フィールド [telegramSuperClass] の値を取得します。
     *
     * フィールドの説明: [電文の親クラス名]。
     *
     * @return フィールド[telegramSuperClass]から取得した値。
     */
    public String getTelegramSuperClass() {
        return fTelegramSuperClass;
    }

    /**
     * フィールド [namespace] の値を設定します。
     *
     * フィールドの説明: [名前空間]。
     *
     * @param argNamespace フィールド[namespace]に設定する値。
     */
    public void setNamespace(final String argNamespace) {
        fNamespace = argNamespace;
    }

    /**
     * フィールド [namespace] の値を取得します。
     *
     * フィールドの説明: [名前空間]。
     *
     * @return フィールド[namespace]から取得した値。
     */
    public String getNamespace() {
        return fNamespace;
    }

    /**
     * フィールド [package] の値を設定します。
     *
     * フィールドの説明: [パッケージ名を指定します。必須項目です。]。
     *
     * @param argPackage フィールド[package]に設定する値。
     */
    public void setPackage(final String argPackage) {
        fPackage = argPackage;
    }

    /**
     * フィールド [package] の値を取得します。
     *
     * フィールドの説明: [パッケージ名を指定します。必須項目です。]。
     *
     * @return フィールド[package]から取得した値。
     */
    public String getPackage() {
        return fPackage;
    }

    /**
     * フィールド [basedir] の値を設定します。
     *
     * フィールドの説明: [本番時にファイルを配置する歳のベースディレクトリ。主にTypeScriptのimport文生成時に使用する事を想定しています。]。
     *
     * @param argBasedir フィールド[basedir]に設定する値。
     */
    public void setBasedir(final String argBasedir) {
        fBasedir = argBasedir;
    }

    /**
     * フィールド [basedir] の値を取得します。
     *
     * フィールドの説明: [本番時にファイルを配置する歳のベースディレクトリ。主にTypeScriptのimport文生成時に使用する事を想定しています。]。
     *
     * @return フィールド[basedir]から取得した値。
     */
    public String getBasedir() {
        return fBasedir;
    }

    /**
     * フィールド [annotationList] の値を設定します。
     *
     * フィールドの説明: [クラスのアノテーションを指定します。]。
     *
     * @param argAnnotationList フィールド[annotationList]に設定する値。
     */
    public void setAnnotationList(final List<String> argAnnotationList) {
        fAnnotationList = argAnnotationList;
    }

    /**
     * フィールド [annotationList] の値を取得します。
     *
     * フィールドの説明: [クラスのアノテーションを指定します。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[annotationList]から取得した値。
     */
    public List<String> getAnnotationList() {
        return fAnnotationList;
    }

    /**
     * フィールド [createImportList] の値を設定します。
     *
     * フィールドの説明: [TypeScript 独自。blancoで一括生成されたクラスについて、import文を自動生成します。]。
     *
     * @param argCreateImportList フィールド[createImportList]に設定する値。
     */
    public void setCreateImportList(final Boolean argCreateImportList) {
        fCreateImportList = argCreateImportList;
    }

    /**
     * フィールド [createImportList] の値を取得します。
     *
     * フィールドの説明: [TypeScript 独自。blancoで一括生成されたクラスについて、import文を自動生成します。]。
     * デフォルト: [true]。
     *
     * @return フィールド[createImportList]から取得した値。
     */
    public Boolean getCreateImportList() {
        return fCreateImportList;
    }

    /**
     * フィールド [extends] の値を設定します。
     *
     * フィールドの説明: [継承するクラスを指定します。]。
     *
     * @param argExtends フィールド[extends]に設定する値。
     */
    public void setExtends(final String argExtends) {
        fExtends = argExtends;
    }

    /**
     * フィールド [extends] の値を取得します。
     *
     * フィールドの説明: [継承するクラスを指定します。]。
     *
     * @return フィールド[extends]から取得した値。
     */
    public String getExtends() {
        return fExtends;
    }

    /**
     * フィールド [implementsList] の値を設定します。
     *
     * フィールドの説明: [実装するインタフェース(java.lang.String)の一覧。]。
     *
     * @param argImplementsList フィールド[implementsList]に設定する値。
     */
    public void setImplementsList(final List<String> argImplementsList) {
        fImplementsList = argImplementsList;
    }

    /**
     * フィールド [implementsList] の値を取得します。
     *
     * フィールドの説明: [実装するインタフェース(java.lang.String)の一覧。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[implementsList]から取得した値。
     */
    public List<String> getImplementsList() {
        return fImplementsList;
    }

    /**
     * フィールド [importList] の値を設定します。
     *
     * フィールドの説明: [importを指定します。]。
     *
     * @param argImportList フィールド[importList]に設定する値。
     */
    public void setImportList(final List<String> argImportList) {
        fImportList = argImportList;
    }

    /**
     * フィールド [importList] の値を取得します。
     *
     * フィールドの説明: [importを指定します。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[importList]から取得した値。
     */
    public List<String> getImportList() {
        return fImportList;
    }

    /**
     * フィールド [headerList] の値を設定します。
     *
     * フィールドの説明: [source コードの先頭に書かれるコード群です。]。
     *
     * @param argHeaderList フィールド[headerList]に設定する値。
     */
    public void setHeaderList(final List<String> argHeaderList) {
        fHeaderList = argHeaderList;
    }

    /**
     * フィールド [headerList] の値を取得します。
     *
     * フィールドの説明: [source コードの先頭に書かれるコード群です。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[headerList]から取得した値。
     */
    public List<String> getHeaderList() {
        return fHeaderList;
    }

    /**
     * フィールド [listField] の値を設定します。
     *
     * フィールドの説明: [フィールドを保持するリスト]。
     *
     * @param argListField フィールド[listField]に設定する値。
     */
    public void setListField(final ArrayList<BlancoRestGeneratorTsTelegramFieldStructure> argListField) {
        fListField = argListField;
    }

    /**
     * フィールド [listField] の値を取得します。
     *
     * フィールドの説明: [フィールドを保持するリスト]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramFieldStructure&gt;()]。
     *
     * @return フィールド[listField]から取得した値。
     */
    public ArrayList<BlancoRestGeneratorTsTelegramFieldStructure> getListField() {
        return fListField;
    }

    /**
     * このバリューオブジェクトの文字列表現を取得します。
     *
     * <P>使用上の注意</P>
     * <UL>
     * <LI>オブジェクトのシャロー範囲のみ文字列化の処理対象となります。
     * <LI>オブジェクトが循環参照している場合には、このメソッドは使わないでください。
     * </UL>
     *
     * @return バリューオブジェクトの文字列表現。
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramStructure[");
        buf.append("name=" + fName);
        buf.append(",description=" + fDescription);
        buf.append(",descriptionList=" + fDescriptionList);
        buf.append(",telegramType=" + fTelegramType);
        buf.append(",telegramMethod=" + fTelegramMethod);
        buf.append(",telegramSuperClass=" + fTelegramSuperClass);
        buf.append(",namespace=" + fNamespace);
        buf.append(",package=" + fPackage);
        buf.append(",basedir=" + fBasedir);
        buf.append(",annotationList=" + fAnnotationList);
        buf.append(",createImportList=" + fCreateImportList);
        buf.append(",extends=" + fExtends);
        buf.append(",implementsList=" + fImplementsList);
        buf.append(",importList=" + fImportList);
        buf.append(",headerList=" + fHeaderList);
        buf.append(",listField=" + fListField);
        buf.append("]");
        return buf.toString();
    }

    /**
     * このバリューオブジェクトを指定のターゲットに複写します。
     *
     * <P>使用上の注意</P>
     * <UL>
     * <LI>オブジェクトのシャロー範囲のみ複写処理対象となります。
     * <LI>オブジェクトが循環参照している場合には、このメソッドは使わないでください。
     * </UL>
     *
     * @param target target value object.
     */
    public void copyTo(final BlancoRestGeneratorTsTelegramStructure target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoRestGeneratorTsTelegramStructure#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fDescriptionList
        // Type: java.util.List
        // フィールド[fDescriptionList]はサポート外の型[java.util.Listjava.lang.String]です。
        // Name: fTelegramType
        // Type: java.lang.String
        target.fTelegramType = this.fTelegramType;
        // Name: fTelegramMethod
        // Type: java.lang.String
        target.fTelegramMethod = this.fTelegramMethod;
        // Name: fTelegramSuperClass
        // Type: java.lang.String
        target.fTelegramSuperClass = this.fTelegramSuperClass;
        // Name: fNamespace
        // Type: java.lang.String
        target.fNamespace = this.fNamespace;
        // Name: fPackage
        // Type: java.lang.String
        target.fPackage = this.fPackage;
        // Name: fBasedir
        // Type: java.lang.String
        target.fBasedir = this.fBasedir;
        // Name: fAnnotationList
        // Type: java.util.List
        // フィールド[fAnnotationList]はサポート外の型[java.util.Listjava.lang.String]です。
        // Name: fCreateImportList
        // Type: java.lang.Boolean
        target.fCreateImportList = this.fCreateImportList;
        // Name: fExtends
        // Type: java.lang.String
        target.fExtends = this.fExtends;
        // Name: fImplementsList
        // Type: java.util.List
        // フィールド[fImplementsList]はサポート外の型[java.util.Listjava.lang.String]です。
        // Name: fImportList
        // Type: java.util.List
        // フィールド[fImportList]はサポート外の型[java.util.Listjava.lang.String]です。
        // Name: fHeaderList
        // Type: java.util.List
        // フィールド[fHeaderList]はサポート外の型[java.util.Listjava.lang.String]です。
        // Name: fListField
        // Type: java.util.ArrayList
        // フィールド[fListField]はサポート外の型[java.util.ArrayListblanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramFieldStructure]です。
    }
}
