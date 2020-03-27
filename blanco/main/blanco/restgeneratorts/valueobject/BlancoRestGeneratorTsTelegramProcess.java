package blanco.restgeneratorts.valueobject;

/**
 * BlancoRestGeneratorTsのなかで利用されるValueObjectです。
 */
public class BlancoRestGeneratorTsTelegramProcess {
    /**
     * 電文ID
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * 説明
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * 要求電文ID（GETメソッド）
     *
     * フィールド: [getRequestId]。
     */
    private String fGetRequestId;

    /**
     * 応答電文ID（GETメソッド）
     *
     * フィールド: [getResponseId]。
     */
    private String fGetResponseId;

    /**
     * 要求電文ID（POSTメソッド）
     *
     * フィールド: [postRequestId]。
     */
    private String fPostRequestId;

    /**
     * 応答電文ID（POSTメソッド）
     *
     * フィールド: [postResponseId]。
     */
    private String fPostResponseId;

    /**
     * 要求電文ID（PUTメソッド）
     *
     * フィールド: [putRequestId]。
     */
    private String fPutRequestId;

    /**
     * 応答電文ID（PUTメソッド）
     *
     * フィールド: [putResponseId]。
     */
    private String fPutResponseId;

    /**
     * 要求電文ID（DELETEメソッド）
     *
     * フィールド: [deleteRequestId]。
     */
    private String fDeleteRequestId;

    /**
     * 応答電文ID（DELETEメソッド）
     *
     * フィールド: [deleteResponseId]。
     */
    private String fDeleteResponseId;

    /**
     * 認証が不要な場合はtrue
     *
     * フィールド: [noAuthentication]。
     * デフォルト: [false]。
     */
    private Boolean fNoAuthentication = false;

    /**
     * WebサービスID
     *
     * フィールド: [serviceId]。
     */
    private String fServiceId;

    /**
     * 名前空間
     *
     * フィールド: [namespace]。
     */
    private String fNamespace;

    /**
     * パッケージ
     *
     * フィールド: [package]。
     */
    private String fPackage;

    /**
     * ロケーション
     *
     * フィールド: [location]。
     */
    private String fLocation;

    /**
     * 電文処理の親クラス
     *
     * フィールド: [superClass]。
     */
    private String fSuperClass;

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
     * フィールドの説明: [説明]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [説明]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [getRequestId] の値を設定します。
     *
     * フィールドの説明: [要求電文ID（GETメソッド）]。
     *
     * @param argGetRequestId フィールド[getRequestId]に設定する値。
     */
    public void setGetRequestId(final String argGetRequestId) {
        fGetRequestId = argGetRequestId;
    }

    /**
     * フィールド [getRequestId] の値を取得します。
     *
     * フィールドの説明: [要求電文ID（GETメソッド）]。
     *
     * @return フィールド[getRequestId]から取得した値。
     */
    public String getGetRequestId() {
        return fGetRequestId;
    }

    /**
     * フィールド [getResponseId] の値を設定します。
     *
     * フィールドの説明: [応答電文ID（GETメソッド）]。
     *
     * @param argGetResponseId フィールド[getResponseId]に設定する値。
     */
    public void setGetResponseId(final String argGetResponseId) {
        fGetResponseId = argGetResponseId;
    }

    /**
     * フィールド [getResponseId] の値を取得します。
     *
     * フィールドの説明: [応答電文ID（GETメソッド）]。
     *
     * @return フィールド[getResponseId]から取得した値。
     */
    public String getGetResponseId() {
        return fGetResponseId;
    }

    /**
     * フィールド [postRequestId] の値を設定します。
     *
     * フィールドの説明: [要求電文ID（POSTメソッド）]。
     *
     * @param argPostRequestId フィールド[postRequestId]に設定する値。
     */
    public void setPostRequestId(final String argPostRequestId) {
        fPostRequestId = argPostRequestId;
    }

    /**
     * フィールド [postRequestId] の値を取得します。
     *
     * フィールドの説明: [要求電文ID（POSTメソッド）]。
     *
     * @return フィールド[postRequestId]から取得した値。
     */
    public String getPostRequestId() {
        return fPostRequestId;
    }

    /**
     * フィールド [postResponseId] の値を設定します。
     *
     * フィールドの説明: [応答電文ID（POSTメソッド）]。
     *
     * @param argPostResponseId フィールド[postResponseId]に設定する値。
     */
    public void setPostResponseId(final String argPostResponseId) {
        fPostResponseId = argPostResponseId;
    }

    /**
     * フィールド [postResponseId] の値を取得します。
     *
     * フィールドの説明: [応答電文ID（POSTメソッド）]。
     *
     * @return フィールド[postResponseId]から取得した値。
     */
    public String getPostResponseId() {
        return fPostResponseId;
    }

    /**
     * フィールド [putRequestId] の値を設定します。
     *
     * フィールドの説明: [要求電文ID（PUTメソッド）]。
     *
     * @param argPutRequestId フィールド[putRequestId]に設定する値。
     */
    public void setPutRequestId(final String argPutRequestId) {
        fPutRequestId = argPutRequestId;
    }

    /**
     * フィールド [putRequestId] の値を取得します。
     *
     * フィールドの説明: [要求電文ID（PUTメソッド）]。
     *
     * @return フィールド[putRequestId]から取得した値。
     */
    public String getPutRequestId() {
        return fPutRequestId;
    }

    /**
     * フィールド [putResponseId] の値を設定します。
     *
     * フィールドの説明: [応答電文ID（PUTメソッド）]。
     *
     * @param argPutResponseId フィールド[putResponseId]に設定する値。
     */
    public void setPutResponseId(final String argPutResponseId) {
        fPutResponseId = argPutResponseId;
    }

    /**
     * フィールド [putResponseId] の値を取得します。
     *
     * フィールドの説明: [応答電文ID（PUTメソッド）]。
     *
     * @return フィールド[putResponseId]から取得した値。
     */
    public String getPutResponseId() {
        return fPutResponseId;
    }

    /**
     * フィールド [deleteRequestId] の値を設定します。
     *
     * フィールドの説明: [要求電文ID（DELETEメソッド）]。
     *
     * @param argDeleteRequestId フィールド[deleteRequestId]に設定する値。
     */
    public void setDeleteRequestId(final String argDeleteRequestId) {
        fDeleteRequestId = argDeleteRequestId;
    }

    /**
     * フィールド [deleteRequestId] の値を取得します。
     *
     * フィールドの説明: [要求電文ID（DELETEメソッド）]。
     *
     * @return フィールド[deleteRequestId]から取得した値。
     */
    public String getDeleteRequestId() {
        return fDeleteRequestId;
    }

    /**
     * フィールド [deleteResponseId] の値を設定します。
     *
     * フィールドの説明: [応答電文ID（DELETEメソッド）]。
     *
     * @param argDeleteResponseId フィールド[deleteResponseId]に設定する値。
     */
    public void setDeleteResponseId(final String argDeleteResponseId) {
        fDeleteResponseId = argDeleteResponseId;
    }

    /**
     * フィールド [deleteResponseId] の値を取得します。
     *
     * フィールドの説明: [応答電文ID（DELETEメソッド）]。
     *
     * @return フィールド[deleteResponseId]から取得した値。
     */
    public String getDeleteResponseId() {
        return fDeleteResponseId;
    }

    /**
     * フィールド [noAuthentication] の値を設定します。
     *
     * フィールドの説明: [認証が不要な場合はtrue]。
     *
     * @param argNoAuthentication フィールド[noAuthentication]に設定する値。
     */
    public void setNoAuthentication(final Boolean argNoAuthentication) {
        fNoAuthentication = argNoAuthentication;
    }

    /**
     * フィールド [noAuthentication] の値を取得します。
     *
     * フィールドの説明: [認証が不要な場合はtrue]。
     * デフォルト: [false]。
     *
     * @return フィールド[noAuthentication]から取得した値。
     */
    public Boolean getNoAuthentication() {
        return fNoAuthentication;
    }

    /**
     * フィールド [serviceId] の値を設定します。
     *
     * フィールドの説明: [WebサービスID]。
     *
     * @param argServiceId フィールド[serviceId]に設定する値。
     */
    public void setServiceId(final String argServiceId) {
        fServiceId = argServiceId;
    }

    /**
     * フィールド [serviceId] の値を取得します。
     *
     * フィールドの説明: [WebサービスID]。
     *
     * @return フィールド[serviceId]から取得した値。
     */
    public String getServiceId() {
        return fServiceId;
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
     * フィールドの説明: [パッケージ]。
     *
     * @param argPackage フィールド[package]に設定する値。
     */
    public void setPackage(final String argPackage) {
        fPackage = argPackage;
    }

    /**
     * フィールド [package] の値を取得します。
     *
     * フィールドの説明: [パッケージ]。
     *
     * @return フィールド[package]から取得した値。
     */
    public String getPackage() {
        return fPackage;
    }

    /**
     * フィールド [location] の値を設定します。
     *
     * フィールドの説明: [ロケーション]。
     *
     * @param argLocation フィールド[location]に設定する値。
     */
    public void setLocation(final String argLocation) {
        fLocation = argLocation;
    }

    /**
     * フィールド [location] の値を取得します。
     *
     * フィールドの説明: [ロケーション]。
     *
     * @return フィールド[location]から取得した値。
     */
    public String getLocation() {
        return fLocation;
    }

    /**
     * フィールド [superClass] の値を設定します。
     *
     * フィールドの説明: [電文処理の親クラス]。
     *
     * @param argSuperClass フィールド[superClass]に設定する値。
     */
    public void setSuperClass(final String argSuperClass) {
        fSuperClass = argSuperClass;
    }

    /**
     * フィールド [superClass] の値を取得します。
     *
     * フィールドの説明: [電文処理の親クラス]。
     *
     * @return フィールド[superClass]から取得した値。
     */
    public String getSuperClass() {
        return fSuperClass;
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
        buf.append("blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramProcess[");
        buf.append("name=" + fName);
        buf.append(",description=" + fDescription);
        buf.append(",getRequestId=" + fGetRequestId);
        buf.append(",getResponseId=" + fGetResponseId);
        buf.append(",postRequestId=" + fPostRequestId);
        buf.append(",postResponseId=" + fPostResponseId);
        buf.append(",putRequestId=" + fPutRequestId);
        buf.append(",putResponseId=" + fPutResponseId);
        buf.append(",deleteRequestId=" + fDeleteRequestId);
        buf.append(",deleteResponseId=" + fDeleteResponseId);
        buf.append(",noAuthentication=" + fNoAuthentication);
        buf.append(",serviceId=" + fServiceId);
        buf.append(",namespace=" + fNamespace);
        buf.append(",package=" + fPackage);
        buf.append(",location=" + fLocation);
        buf.append(",superClass=" + fSuperClass);
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
    public void copyTo(final BlancoRestGeneratorTsTelegramProcess target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoRestGeneratorTsTelegramProcess#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fGetRequestId
        // Type: java.lang.String
        target.fGetRequestId = this.fGetRequestId;
        // Name: fGetResponseId
        // Type: java.lang.String
        target.fGetResponseId = this.fGetResponseId;
        // Name: fPostRequestId
        // Type: java.lang.String
        target.fPostRequestId = this.fPostRequestId;
        // Name: fPostResponseId
        // Type: java.lang.String
        target.fPostResponseId = this.fPostResponseId;
        // Name: fPutRequestId
        // Type: java.lang.String
        target.fPutRequestId = this.fPutRequestId;
        // Name: fPutResponseId
        // Type: java.lang.String
        target.fPutResponseId = this.fPutResponseId;
        // Name: fDeleteRequestId
        // Type: java.lang.String
        target.fDeleteRequestId = this.fDeleteRequestId;
        // Name: fDeleteResponseId
        // Type: java.lang.String
        target.fDeleteResponseId = this.fDeleteResponseId;
        // Name: fNoAuthentication
        // Type: java.lang.Boolean
        target.fNoAuthentication = this.fNoAuthentication;
        // Name: fServiceId
        // Type: java.lang.String
        target.fServiceId = this.fServiceId;
        // Name: fNamespace
        // Type: java.lang.String
        target.fNamespace = this.fNamespace;
        // Name: fPackage
        // Type: java.lang.String
        target.fPackage = this.fPackage;
        // Name: fLocation
        // Type: java.lang.String
        target.fLocation = this.fLocation;
        // Name: fSuperClass
        // Type: java.lang.String
        target.fSuperClass = this.fSuperClass;
    }
}
