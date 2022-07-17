package blanco.restgeneratorts.task.valueobject;

/**
 * An input value object class for the processing class [BlancoRestGeneratorTsProcess].
 */
public class BlancoRestGeneratorTsProcessInput {
    /**
     * Whether to run in verbose mode.
     *
     * フィールド: [verbose]。
     * デフォルト: [false]。
     */
    private boolean fVerbose = false;

    /**
     * メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。
     *
     * フィールド: [metadir]。
     */
    private String fMetadir;

    /**
     * 出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。
     *
     * フィールド: [targetdir]。
     * デフォルト: [blanco]。
     */
    private String fTargetdir = "blanco";

    /**
     * テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。
     *
     * フィールド: [tmpdir]。
     * デフォルト: [tmp]。
     */
    private String fTmpdir = "tmp";

    /**
     * フィールド名やメソッド名を名前変形を実施するかどうか。
     *
     * フィールド: [nameAdjust]。
     * デフォルト: [true]。
     */
    private boolean fNameAdjust = true;

    /**
     * 自動生成するソースファイルの文字エンコーディングを指定します。
     *
     * フィールド: [encoding]。
     */
    private String fEncoding;

    /**
     * タブをwhite spaceいくつで置き換えるか、という値です。
     *
     * フィールド: [tabs]。
     * デフォルト: [4]。
     */
    private int fTabs = 4;

    /**
     * XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。
     *
     * フィールド: [xmlrootelement]。
     * デフォルト: [false]。
     */
    private boolean fXmlrootelement = false;

    /**
     * meta定義書が期待しているプログラミング言語を指定します
     *
     * フィールド: [sheetType]。
     * デフォルト: [java]。
     */
    private String fSheetType = "java";

    /**
     * 出力先フォルダの書式を指定します。&amp;lt;br&amp;gt;\nblanco: [targetdir]/main&amp;lt;br&amp;gt;\nmaven: [targetdir]/main/java&amp;lt;br&amp;gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)
     *
     * フィールド: [targetStyle]。
     * デフォルト: [blanco]。
     */
    private String fTargetStyle = "blanco";

    /**
     * trueの場合はサーバ用のメソッドを生成しません。
     *
     * フィールド: [client]。
     * デフォルト: [false]。
     */
    private boolean fClient = false;

    /**
     * 生成した電文処理のインスタンスを文字列から取得するための配列を生成する場合は、ここにファイル名を指定します。
     *
     * フィールド: [processlist]。
     */
    private String fProcesslist;

    /**
     * processList に記述する import 文のbasedirを指定します。
     *
     * フィールド: [processlistBase]。
     * デフォルト: [%]。
     */
    private String fProcesslistBase = "%";

    /**
     * 行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。
     *
     * フィールド: [lineSeparator]。
     * デフォルト: [LF]。
     */
    private String fLineSeparator = "LF";

    /**
     * import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。
     *
     * フィールド: [searchTmpdir]。
     */
    private String fSearchTmpdir;

    /**
     * toJSONメソッドを生成します
     *
     * フィールド: [generateToJson]。
     * デフォルト: [false]。
     */
    private boolean fGenerateToJson = false;

    /**
     * 電文の親クラスの配置場所を、Javaのパッケージ形式で指定します。
     *
     * フィールド: [apiTelegramPackage]。
     */
    private String fApiTelegramPackage;

    /**
     * 電文の親クラスの配置場所のaliasを指定します。通常は % です。
     *
     * フィールド: [apiTelegramBase]。
     * デフォルト: [%]。
     */
    private String fApiTelegramBase = "%";

    /**
     * フィールド [verbose] の値を設定します。
     *
     * フィールドの説明: [Whether to run in verbose mode.]。
     *
     * @param argVerbose フィールド[verbose]に設定する値。
     */
    public void setVerbose(final boolean argVerbose) {
        fVerbose = argVerbose;
    }

    /**
     * フィールド [verbose] の値を取得します。
     *
     * フィールドの説明: [Whether to run in verbose mode.]。
     * デフォルト: [false]。
     *
     * @return フィールド[verbose]から取得した値。
     */
    public boolean getVerbose() {
        return fVerbose;
    }

    /**
     * フィールド [metadir] の値を設定します。
     *
     * フィールドの説明: [メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。]。
     *
     * @param argMetadir フィールド[metadir]に設定する値。
     */
    public void setMetadir(final String argMetadir) {
        fMetadir = argMetadir;
    }

    /**
     * フィールド [metadir] の値を取得します。
     *
     * フィールドの説明: [メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。]。
     *
     * @return フィールド[metadir]から取得した値。
     */
    public String getMetadir() {
        return fMetadir;
    }

    /**
     * フィールド [targetdir] の値を設定します。
     *
     * フィールドの説明: [出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。]。
     *
     * @param argTargetdir フィールド[targetdir]に設定する値。
     */
    public void setTargetdir(final String argTargetdir) {
        fTargetdir = argTargetdir;
    }

    /**
     * フィールド [targetdir] の値を取得します。
     *
     * フィールドの説明: [出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。]。
     * デフォルト: [blanco]。
     *
     * @return フィールド[targetdir]から取得した値。
     */
    public String getTargetdir() {
        return fTargetdir;
    }

    /**
     * フィールド [tmpdir] の値を設定します。
     *
     * フィールドの説明: [テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。]。
     *
     * @param argTmpdir フィールド[tmpdir]に設定する値。
     */
    public void setTmpdir(final String argTmpdir) {
        fTmpdir = argTmpdir;
    }

    /**
     * フィールド [tmpdir] の値を取得します。
     *
     * フィールドの説明: [テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。]。
     * デフォルト: [tmp]。
     *
     * @return フィールド[tmpdir]から取得した値。
     */
    public String getTmpdir() {
        return fTmpdir;
    }

    /**
     * フィールド [nameAdjust] の値を設定します。
     *
     * フィールドの説明: [フィールド名やメソッド名を名前変形を実施するかどうか。]。
     *
     * @param argNameAdjust フィールド[nameAdjust]に設定する値。
     */
    public void setNameAdjust(final boolean argNameAdjust) {
        fNameAdjust = argNameAdjust;
    }

    /**
     * フィールド [nameAdjust] の値を取得します。
     *
     * フィールドの説明: [フィールド名やメソッド名を名前変形を実施するかどうか。]。
     * デフォルト: [true]。
     *
     * @return フィールド[nameAdjust]から取得した値。
     */
    public boolean getNameAdjust() {
        return fNameAdjust;
    }

    /**
     * フィールド [encoding] の値を設定します。
     *
     * フィールドの説明: [自動生成するソースファイルの文字エンコーディングを指定します。]。
     *
     * @param argEncoding フィールド[encoding]に設定する値。
     */
    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    /**
     * フィールド [encoding] の値を取得します。
     *
     * フィールドの説明: [自動生成するソースファイルの文字エンコーディングを指定します。]。
     *
     * @return フィールド[encoding]から取得した値。
     */
    public String getEncoding() {
        return fEncoding;
    }

    /**
     * フィールド [tabs] の値を設定します。
     *
     * フィールドの説明: [タブをwhite spaceいくつで置き換えるか、という値です。]。
     *
     * @param argTabs フィールド[tabs]に設定する値。
     */
    public void setTabs(final int argTabs) {
        fTabs = argTabs;
    }

    /**
     * フィールド [tabs] の値を取得します。
     *
     * フィールドの説明: [タブをwhite spaceいくつで置き換えるか、という値です。]。
     * デフォルト: [4]。
     *
     * @return フィールド[tabs]から取得した値。
     */
    public int getTabs() {
        return fTabs;
    }

    /**
     * フィールド [xmlrootelement] の値を設定します。
     *
     * フィールドの説明: [XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。]。
     *
     * @param argXmlrootelement フィールド[xmlrootelement]に設定する値。
     */
    public void setXmlrootelement(final boolean argXmlrootelement) {
        fXmlrootelement = argXmlrootelement;
    }

    /**
     * フィールド [xmlrootelement] の値を取得します。
     *
     * フィールドの説明: [XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。]。
     * デフォルト: [false]。
     *
     * @return フィールド[xmlrootelement]から取得した値。
     */
    public boolean getXmlrootelement() {
        return fXmlrootelement;
    }

    /**
     * フィールド [sheetType] の値を設定します。
     *
     * フィールドの説明: [meta定義書が期待しているプログラミング言語を指定します]。
     *
     * @param argSheetType フィールド[sheetType]に設定する値。
     */
    public void setSheetType(final String argSheetType) {
        fSheetType = argSheetType;
    }

    /**
     * フィールド [sheetType] の値を取得します。
     *
     * フィールドの説明: [meta定義書が期待しているプログラミング言語を指定します]。
     * デフォルト: [java]。
     *
     * @return フィールド[sheetType]から取得した値。
     */
    public String getSheetType() {
        return fSheetType;
    }

    /**
     * フィールド [targetStyle] の値を設定します。
     *
     * フィールドの説明: [出力先フォルダの書式を指定します。&lt;br&gt;\nblanco: [targetdir]/main&lt;br&gt;\nmaven: [targetdir]/main/java&lt;br&gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)]。
     *
     * @param argTargetStyle フィールド[targetStyle]に設定する値。
     */
    public void setTargetStyle(final String argTargetStyle) {
        fTargetStyle = argTargetStyle;
    }

    /**
     * フィールド [targetStyle] の値を取得します。
     *
     * フィールドの説明: [出力先フォルダの書式を指定します。&lt;br&gt;\nblanco: [targetdir]/main&lt;br&gt;\nmaven: [targetdir]/main/java&lt;br&gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)]。
     * デフォルト: [blanco]。
     *
     * @return フィールド[targetStyle]から取得した値。
     */
    public String getTargetStyle() {
        return fTargetStyle;
    }

    /**
     * フィールド [client] の値を設定します。
     *
     * フィールドの説明: [trueの場合はサーバ用のメソッドを生成しません。]。
     *
     * @param argClient フィールド[client]に設定する値。
     */
    public void setClient(final boolean argClient) {
        fClient = argClient;
    }

    /**
     * フィールド [client] の値を取得します。
     *
     * フィールドの説明: [trueの場合はサーバ用のメソッドを生成しません。]。
     * デフォルト: [false]。
     *
     * @return フィールド[client]から取得した値。
     */
    public boolean getClient() {
        return fClient;
    }

    /**
     * フィールド [processlist] の値を設定します。
     *
     * フィールドの説明: [生成した電文処理のインスタンスを文字列から取得するための配列を生成する場合は、ここにファイル名を指定します。]。
     *
     * @param argProcesslist フィールド[processlist]に設定する値。
     */
    public void setProcesslist(final String argProcesslist) {
        fProcesslist = argProcesslist;
    }

    /**
     * フィールド [processlist] の値を取得します。
     *
     * フィールドの説明: [生成した電文処理のインスタンスを文字列から取得するための配列を生成する場合は、ここにファイル名を指定します。]。
     *
     * @return フィールド[processlist]から取得した値。
     */
    public String getProcesslist() {
        return fProcesslist;
    }

    /**
     * フィールド [processlistBase] の値を設定します。
     *
     * フィールドの説明: [processList に記述する import 文のbasedirを指定します。]。
     *
     * @param argProcesslistBase フィールド[processlistBase]に設定する値。
     */
    public void setProcesslistBase(final String argProcesslistBase) {
        fProcesslistBase = argProcesslistBase;
    }

    /**
     * フィールド [processlistBase] の値を取得します。
     *
     * フィールドの説明: [processList に記述する import 文のbasedirを指定します。]。
     * デフォルト: [%]。
     *
     * @return フィールド[processlistBase]から取得した値。
     */
    public String getProcesslistBase() {
        return fProcesslistBase;
    }

    /**
     * フィールド [lineSeparator] の値を設定します。
     *
     * フィールドの説明: [行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。]。
     *
     * @param argLineSeparator フィールド[lineSeparator]に設定する値。
     */
    public void setLineSeparator(final String argLineSeparator) {
        fLineSeparator = argLineSeparator;
    }

    /**
     * フィールド [lineSeparator] の値を取得します。
     *
     * フィールドの説明: [行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。]。
     * デフォルト: [LF]。
     *
     * @return フィールド[lineSeparator]から取得した値。
     */
    public String getLineSeparator() {
        return fLineSeparator;
    }

    /**
     * フィールド [searchTmpdir] の値を設定します。
     *
     * フィールドの説明: [import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。]。
     *
     * @param argSearchTmpdir フィールド[searchTmpdir]に設定する値。
     */
    public void setSearchTmpdir(final String argSearchTmpdir) {
        fSearchTmpdir = argSearchTmpdir;
    }

    /**
     * フィールド [searchTmpdir] の値を取得します。
     *
     * フィールドの説明: [import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。]。
     *
     * @return フィールド[searchTmpdir]から取得した値。
     */
    public String getSearchTmpdir() {
        return fSearchTmpdir;
    }

    /**
     * フィールド [generateToJson] の値を設定します。
     *
     * フィールドの説明: [toJSONメソッドを生成します]。
     *
     * @param argGenerateToJson フィールド[generateToJson]に設定する値。
     */
    public void setGenerateToJson(final boolean argGenerateToJson) {
        fGenerateToJson = argGenerateToJson;
    }

    /**
     * フィールド [generateToJson] の値を取得します。
     *
     * フィールドの説明: [toJSONメソッドを生成します]。
     * デフォルト: [false]。
     *
     * @return フィールド[generateToJson]から取得した値。
     */
    public boolean getGenerateToJson() {
        return fGenerateToJson;
    }

    /**
     * フィールド [apiTelegramPackage] の値を設定します。
     *
     * フィールドの説明: [電文の親クラスの配置場所を、Javaのパッケージ形式で指定します。]。
     *
     * @param argApiTelegramPackage フィールド[apiTelegramPackage]に設定する値。
     */
    public void setApiTelegramPackage(final String argApiTelegramPackage) {
        fApiTelegramPackage = argApiTelegramPackage;
    }

    /**
     * フィールド [apiTelegramPackage] の値を取得します。
     *
     * フィールドの説明: [電文の親クラスの配置場所を、Javaのパッケージ形式で指定します。]。
     *
     * @return フィールド[apiTelegramPackage]から取得した値。
     */
    public String getApiTelegramPackage() {
        return fApiTelegramPackage;
    }

    /**
     * フィールド [apiTelegramBase] の値を設定します。
     *
     * フィールドの説明: [電文の親クラスの配置場所のaliasを指定します。通常は % です。]。
     *
     * @param argApiTelegramBase フィールド[apiTelegramBase]に設定する値。
     */
    public void setApiTelegramBase(final String argApiTelegramBase) {
        fApiTelegramBase = argApiTelegramBase;
    }

    /**
     * フィールド [apiTelegramBase] の値を取得します。
     *
     * フィールドの説明: [電文の親クラスの配置場所のaliasを指定します。通常は % です。]。
     * デフォルト: [%]。
     *
     * @return フィールド[apiTelegramBase]から取得した値。
     */
    public String getApiTelegramBase() {
        return fApiTelegramBase;
    }

    /**
     * Gets the string representation of this value object.
     *
     * <P>Precautions for use</P>
     * <UL>
     * <LI>Only the shallow range of the object will be subject to the stringification process.
     * <LI>Do not use this method if the object has a circular reference.
     * </UL>
     *
     * @return String representation of a value object.
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("blanco.restgeneratorts.task.valueobject.BlancoRestGeneratorTsProcessInput[");
        buf.append("verbose=" + fVerbose);
        buf.append(",metadir=" + fMetadir);
        buf.append(",targetdir=" + fTargetdir);
        buf.append(",tmpdir=" + fTmpdir);
        buf.append(",nameAdjust=" + fNameAdjust);
        buf.append(",encoding=" + fEncoding);
        buf.append(",tabs=" + fTabs);
        buf.append(",xmlrootelement=" + fXmlrootelement);
        buf.append(",sheetType=" + fSheetType);
        buf.append(",targetStyle=" + fTargetStyle);
        buf.append(",client=" + fClient);
        buf.append(",processlist=" + fProcesslist);
        buf.append(",processlistBase=" + fProcesslistBase);
        buf.append(",lineSeparator=" + fLineSeparator);
        buf.append(",searchTmpdir=" + fSearchTmpdir);
        buf.append(",generateToJson=" + fGenerateToJson);
        buf.append(",apiTelegramPackage=" + fApiTelegramPackage);
        buf.append(",apiTelegramBase=" + fApiTelegramBase);
        buf.append("]");
        return buf.toString();
    }

    /**
     * Copies this value object to the specified target.
     *
     * <P>Cautions for use</P>
     * <UL>
     * <LI>Only the shallow range of the object will be subject to the copying process.
     * <LI>Do not use this method if the object has a circular reference.
     * </UL>
     *
     * @param target target value object.
     */
    public void copyTo(final BlancoRestGeneratorTsProcessInput target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoRestGeneratorTsProcessInput#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fVerbose
        // Type: boolean
        target.fVerbose = this.fVerbose;
        // Name: fMetadir
        // Type: java.lang.String
        target.fMetadir = this.fMetadir;
        // Name: fTargetdir
        // Type: java.lang.String
        target.fTargetdir = this.fTargetdir;
        // Name: fTmpdir
        // Type: java.lang.String
        target.fTmpdir = this.fTmpdir;
        // Name: fNameAdjust
        // Type: boolean
        target.fNameAdjust = this.fNameAdjust;
        // Name: fEncoding
        // Type: java.lang.String
        target.fEncoding = this.fEncoding;
        // Name: fTabs
        // Type: int
        target.fTabs = this.fTabs;
        // Name: fXmlrootelement
        // Type: boolean
        target.fXmlrootelement = this.fXmlrootelement;
        // Name: fSheetType
        // Type: java.lang.String
        target.fSheetType = this.fSheetType;
        // Name: fTargetStyle
        // Type: java.lang.String
        target.fTargetStyle = this.fTargetStyle;
        // Name: fClient
        // Type: boolean
        target.fClient = this.fClient;
        // Name: fProcesslist
        // Type: java.lang.String
        target.fProcesslist = this.fProcesslist;
        // Name: fProcesslistBase
        // Type: java.lang.String
        target.fProcesslistBase = this.fProcesslistBase;
        // Name: fLineSeparator
        // Type: java.lang.String
        target.fLineSeparator = this.fLineSeparator;
        // Name: fSearchTmpdir
        // Type: java.lang.String
        target.fSearchTmpdir = this.fSearchTmpdir;
        // Name: fGenerateToJson
        // Type: boolean
        target.fGenerateToJson = this.fGenerateToJson;
        // Name: fApiTelegramPackage
        // Type: java.lang.String
        target.fApiTelegramPackage = this.fApiTelegramPackage;
        // Name: fApiTelegramBase
        // Type: java.lang.String
        target.fApiTelegramBase = this.fApiTelegramBase;
    }
}
