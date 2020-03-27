package blanco.restgeneratorts.valueobject;

/**
 * BlancoRestGeneratorTsのなかで利用されるValueObjectです。
 */
public class BlancoRestGeneratorTsTelegramField {
    /**
     * 項目番号。省略可能です。
     *
     * フィールド: [no]。
     */
    private String fNo;

    /**
     * フィールド名を指定します。必須項目です。
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * 項目の説明，javadocで利用されます．
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * 型名をパッケージ名のフル修飾付で指定します。必須項目です。
     *
     * フィールド: [fieldType]。
     */
    private String fFieldType;

    /**
     * 型が期待する総称型の具体的な型名を指定します．
     *
     * フィールド: [fieldGeneric]。
     */
    private String fFieldGeneric;

    /**
     * 必須項目の場合はtrue
     *
     * フィールド: [fieldRequired]。
     */
    private Boolean fFieldRequired;

    /**
     * フィールド: [default]。
     */
    private String fDefault;

    /**
     * 長さmin
     *
     * フィールド: [minLength]。
     */
    private Integer fMinLength;

    /**
     * 長さmax
     *
     * フィールド: [maxLength]。
     */
    private Integer fMaxLength;

    /**
     * 値範囲min
     *
     * フィールド: [minInclusive]。
     */
    private String fMinInclusive;

    /**
     * 値範囲max
     *
     * フィールド: [maxInclusive]。
     */
    private String fMaxInclusive;

    /**
     * 形式Check正規表現
     *
     * フィールド: [pattern]。
     */
    private String fPattern;

    /**
     * 備考
     *
     * フィールド: [fieldBiko]。
     */
    private String fFieldBiko;

    /**
     * フィールド [no] の値を設定します。
     *
     * フィールドの説明: [項目番号。省略可能です。]。
     *
     * @param argNo フィールド[no]に設定する値。
     */
    public void setNo(final String argNo) {
        fNo = argNo;
    }

    /**
     * フィールド [no] の値を取得します。
     *
     * フィールドの説明: [項目番号。省略可能です。]。
     *
     * @return フィールド[no]から取得した値。
     */
    public String getNo() {
        return fNo;
    }

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [フィールド名を指定します。必須項目です。]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [フィールド名を指定します。必須項目です。]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [項目の説明，javadocで利用されます．]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [項目の説明，javadocで利用されます．]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [fieldType] の値を設定します。
     *
     * フィールドの説明: [型名をパッケージ名のフル修飾付で指定します。必須項目です。]。
     *
     * @param argFieldType フィールド[fieldType]に設定する値。
     */
    public void setFieldType(final String argFieldType) {
        fFieldType = argFieldType;
    }

    /**
     * フィールド [fieldType] の値を取得します。
     *
     * フィールドの説明: [型名をパッケージ名のフル修飾付で指定します。必須項目です。]。
     *
     * @return フィールド[fieldType]から取得した値。
     */
    public String getFieldType() {
        return fFieldType;
    }

    /**
     * フィールド [fieldGeneric] の値を設定します。
     *
     * フィールドの説明: [型が期待する総称型の具体的な型名を指定します．]。
     *
     * @param argFieldGeneric フィールド[fieldGeneric]に設定する値。
     */
    public void setFieldGeneric(final String argFieldGeneric) {
        fFieldGeneric = argFieldGeneric;
    }

    /**
     * フィールド [fieldGeneric] の値を取得します。
     *
     * フィールドの説明: [型が期待する総称型の具体的な型名を指定します．]。
     *
     * @return フィールド[fieldGeneric]から取得した値。
     */
    public String getFieldGeneric() {
        return fFieldGeneric;
    }

    /**
     * フィールド [fieldRequired] の値を設定します。
     *
     * フィールドの説明: [必須項目の場合はtrue]。
     *
     * @param argFieldRequired フィールド[fieldRequired]に設定する値。
     */
    public void setFieldRequired(final Boolean argFieldRequired) {
        fFieldRequired = argFieldRequired;
    }

    /**
     * フィールド [fieldRequired] の値を取得します。
     *
     * フィールドの説明: [必須項目の場合はtrue]。
     *
     * @return フィールド[fieldRequired]から取得した値。
     */
    public Boolean getFieldRequired() {
        return fFieldRequired;
    }

    /**
     * フィールド [default] の値を設定します。
     *
     * @param argDefault フィールド[default]に設定する値。
     */
    public void setDefault(final String argDefault) {
        fDefault = argDefault;
    }

    /**
     * フィールド [default] の値を取得します。
     *
     * @return フィールド[default]から取得した値。
     */
    public String getDefault() {
        return fDefault;
    }

    /**
     * フィールド [minLength] の値を設定します。
     *
     * フィールドの説明: [長さmin]。
     *
     * @param argMinLength フィールド[minLength]に設定する値。
     */
    public void setMinLength(final Integer argMinLength) {
        fMinLength = argMinLength;
    }

    /**
     * フィールド [minLength] の値を取得します。
     *
     * フィールドの説明: [長さmin]。
     *
     * @return フィールド[minLength]から取得した値。
     */
    public Integer getMinLength() {
        return fMinLength;
    }

    /**
     * フィールド [maxLength] の値を設定します。
     *
     * フィールドの説明: [長さmax]。
     *
     * @param argMaxLength フィールド[maxLength]に設定する値。
     */
    public void setMaxLength(final Integer argMaxLength) {
        fMaxLength = argMaxLength;
    }

    /**
     * フィールド [maxLength] の値を取得します。
     *
     * フィールドの説明: [長さmax]。
     *
     * @return フィールド[maxLength]から取得した値。
     */
    public Integer getMaxLength() {
        return fMaxLength;
    }

    /**
     * フィールド [minInclusive] の値を設定します。
     *
     * フィールドの説明: [値範囲min]。
     *
     * @param argMinInclusive フィールド[minInclusive]に設定する値。
     */
    public void setMinInclusive(final String argMinInclusive) {
        fMinInclusive = argMinInclusive;
    }

    /**
     * フィールド [minInclusive] の値を取得します。
     *
     * フィールドの説明: [値範囲min]。
     *
     * @return フィールド[minInclusive]から取得した値。
     */
    public String getMinInclusive() {
        return fMinInclusive;
    }

    /**
     * フィールド [maxInclusive] の値を設定します。
     *
     * フィールドの説明: [値範囲max]。
     *
     * @param argMaxInclusive フィールド[maxInclusive]に設定する値。
     */
    public void setMaxInclusive(final String argMaxInclusive) {
        fMaxInclusive = argMaxInclusive;
    }

    /**
     * フィールド [maxInclusive] の値を取得します。
     *
     * フィールドの説明: [値範囲max]。
     *
     * @return フィールド[maxInclusive]から取得した値。
     */
    public String getMaxInclusive() {
        return fMaxInclusive;
    }

    /**
     * フィールド [pattern] の値を設定します。
     *
     * フィールドの説明: [形式Check正規表現]。
     *
     * @param argPattern フィールド[pattern]に設定する値。
     */
    public void setPattern(final String argPattern) {
        fPattern = argPattern;
    }

    /**
     * フィールド [pattern] の値を取得します。
     *
     * フィールドの説明: [形式Check正規表現]。
     *
     * @return フィールド[pattern]から取得した値。
     */
    public String getPattern() {
        return fPattern;
    }

    /**
     * フィールド [fieldBiko] の値を設定します。
     *
     * フィールドの説明: [備考]。
     *
     * @param argFieldBiko フィールド[fieldBiko]に設定する値。
     */
    public void setFieldBiko(final String argFieldBiko) {
        fFieldBiko = argFieldBiko;
    }

    /**
     * フィールド [fieldBiko] の値を取得します。
     *
     * フィールドの説明: [備考]。
     *
     * @return フィールド[fieldBiko]から取得した値。
     */
    public String getFieldBiko() {
        return fFieldBiko;
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
        buf.append("blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramField[");
        buf.append("no=" + fNo);
        buf.append(",name=" + fName);
        buf.append(",description=" + fDescription);
        buf.append(",fieldType=" + fFieldType);
        buf.append(",fieldGeneric=" + fFieldGeneric);
        buf.append(",fieldRequired=" + fFieldRequired);
        buf.append(",default=" + fDefault);
        buf.append(",minLength=" + fMinLength);
        buf.append(",maxLength=" + fMaxLength);
        buf.append(",minInclusive=" + fMinInclusive);
        buf.append(",maxInclusive=" + fMaxInclusive);
        buf.append(",pattern=" + fPattern);
        buf.append(",fieldBiko=" + fFieldBiko);
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
    public void copyTo(final BlancoRestGeneratorTsTelegramField target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoRestGeneratorTsTelegramField#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fNo
        // Type: java.lang.String
        target.fNo = this.fNo;
        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fFieldType
        // Type: java.lang.String
        target.fFieldType = this.fFieldType;
        // Name: fFieldGeneric
        // Type: java.lang.String
        target.fFieldGeneric = this.fFieldGeneric;
        // Name: fFieldRequired
        // Type: java.lang.Boolean
        target.fFieldRequired = this.fFieldRequired;
        // Name: fDefault
        // Type: java.lang.String
        target.fDefault = this.fDefault;
        // Name: fMinLength
        // Type: java.lang.Integer
        target.fMinLength = this.fMinLength;
        // Name: fMaxLength
        // Type: java.lang.Integer
        target.fMaxLength = this.fMaxLength;
        // Name: fMinInclusive
        // Type: java.lang.String
        target.fMinInclusive = this.fMinInclusive;
        // Name: fMaxInclusive
        // Type: java.lang.String
        target.fMaxInclusive = this.fMaxInclusive;
        // Name: fPattern
        // Type: java.lang.String
        target.fPattern = this.fPattern;
        // Name: fFieldBiko
        // Type: java.lang.String
        target.fFieldBiko = this.fFieldBiko;
    }
}
