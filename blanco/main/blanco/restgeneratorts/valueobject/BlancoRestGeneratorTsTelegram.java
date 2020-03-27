package blanco.restgeneratorts.valueobject;

import java.util.ArrayList;

/**
 * BlancoRestGeneratorTsのなかで利用されるValueObjectです。
 */
public class BlancoRestGeneratorTsTelegram {
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
     * フィールドを保持するリスト
     *
     * フィールド: [listField]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramField&gt;()]。
     */
    private ArrayList<blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramField> fListField = new java.util.ArrayList<blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramField>();

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
     * フィールド [listField] の値を設定します。
     *
     * フィールドの説明: [フィールドを保持するリスト]。
     *
     * @param argListField フィールド[listField]に設定する値。
     */
    public void setListField(final ArrayList<blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramField> argListField) {
        fListField = argListField;
    }

    /**
     * フィールド [listField] の値を取得します。
     *
     * フィールドの説明: [フィールドを保持するリスト]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramField&gt;()]。
     *
     * @return フィールド[listField]から取得した値。
     */
    public ArrayList<blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramField> getListField() {
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
        buf.append("blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegram[");
        buf.append("name=" + fName);
        buf.append(",description=" + fDescription);
        buf.append(",telegramType=" + fTelegramType);
        buf.append(",telegramMethod=" + fTelegramMethod);
        buf.append(",telegramSuperClass=" + fTelegramSuperClass);
        buf.append(",namespace=" + fNamespace);
        buf.append(",package=" + fPackage);
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
    public void copyTo(final BlancoRestGeneratorTsTelegram target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoRestGeneratorTsTelegram#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
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
        // Name: fListField
        // Type: java.util.ArrayList
        // フィールド[fListField]はサポート外の型[java.util.ArrayList<blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramField>]です。
    }
}
