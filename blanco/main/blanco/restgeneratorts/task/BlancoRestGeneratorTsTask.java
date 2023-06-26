package blanco.restgeneratorts.task;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import blanco.restgeneratorts.task.valueobject.BlancoRestGeneratorTsProcessInput;

/**
 * Apache Antタスク [BlancoRestGeneratorTs]のクラス。
 *
 * API定義書からAPIスタブクラスを自動生成するBlancoRestGeneratorTsのためのAntTaskです。<br>
 * このクラスでは、Apache Antタスクで一般的に必要なチェックなどのコーディングを肩代わりします。
 * 実際の処理は パッケージ[blanco.restgeneratorts.task]にBlancoRestGeneratorTsBatchProcessクラスを作成して記述してください。<br>
 * <br>
 * Antタスクへの組み込み例<br>
 * <pre>
 * &lt;taskdef name=&quot;blancorestgeneratorts&quot; classname=&quot;blanco.restgeneratorts.task.BlancoRestGeneratorTsTask">
 *     &lt;classpath&gt;
 *         &lt;fileset dir=&quot;lib&quot; includes=&quot;*.jar&quot; /&gt;
 *         &lt;fileset dir=&quot;lib.ant&quot; includes=&quot;*.jar&quot; /&gt;
 *     &lt;/classpath&gt;
 * &lt;/taskdef&gt;
 * </pre>
 */
public class BlancoRestGeneratorTsTask extends Task {
    /**
     * API定義書からAPIスタブクラスを自動生成するBlancoRestGeneratorTsのためのAntTaskです。
     */
    protected BlancoRestGeneratorTsProcessInput fInput = new BlancoRestGeneratorTsProcessInput();

    /**
     * フィールド [metadir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldMetadirProcessed = false;

    /**
     * フィールド [targetdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTargetdirProcessed = false;

    /**
     * フィールド [tmpdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTmpdirProcessed = false;

    /**
     * フィールド [nameAdjust] に値がセットされたかどうか。
     */
    protected boolean fIsFieldNameAdjustProcessed = false;

    /**
     * フィールド [encoding] に値がセットされたかどうか。
     */
    protected boolean fIsFieldEncodingProcessed = false;

    /**
     * フィールド [tabs] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTabsProcessed = false;

    /**
     * フィールド [xmlrootelement] に値がセットされたかどうか。
     */
    protected boolean fIsFieldXmlrootelementProcessed = false;

    /**
     * フィールド [sheetType] に値がセットされたかどうか。
     */
    protected boolean fIsFieldSheetTypeProcessed = false;

    /**
     * フィールド [targetStyle] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTargetStyleProcessed = false;

    /**
     * フィールド [client] に値がセットされたかどうか。
     */
    protected boolean fIsFieldClientProcessed = false;

    /**
     * フィールド [processlist] に値がセットされたかどうか。
     */
    protected boolean fIsFieldProcesslistProcessed = false;

    /**
     * フィールド [processlistBase] に値がセットされたかどうか。
     */
    protected boolean fIsFieldProcesslistBaseProcessed = false;

    /**
     * フィールド [lineSeparator] に値がセットされたかどうか。
     */
    protected boolean fIsFieldLineSeparatorProcessed = false;

    /**
     * フィールド [searchTmpdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldSearchTmpdirProcessed = false;

    /**
     * フィールド [generateToJson] に値がセットされたかどうか。
     */
    protected boolean fIsFieldGenerateToJsonProcessed = false;

    /**
     * フィールド [apiTelegramPackage] に値がセットされたかどうか。
     */
    protected boolean fIsFieldApiTelegramPackageProcessed = false;

    /**
     * フィールド [apiTelegramBase] に値がセットされたかどうか。
     */
    protected boolean fIsFieldApiTelegramBaseProcessed = false;

    /**
     * フィールド [telegramStyle] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTelegramStyleProcessed = false;

    /**
     * verboseモードで動作させるかどうか。
     *
     * @param arg verboseモードで動作させるかどうか。
     */
    public void setVerbose(final boolean arg) {
        fInput.setVerbose(arg);
    }

    /**
     * verboseモードで動作させるかどうか。
     *
     * @return verboseモードで動作させるかどうか。
     */
    public boolean getVerbose() {
        return fInput.getVerbose();
    }

    /**
     * Antタスクの[metadir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 1<br>
     * メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setMetadir(final String arg) {
        fInput.setMetadir(arg);
        fIsFieldMetadirProcessed = true;
    }

    /**
     * Antタスクの[metadir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 1<br>
     * メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getMetadir() {
        return fInput.getMetadir();
    }

    /**
     * Antタスクの[targetdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 2<br>
     * 出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。<br>
     *
     * @param arg セットしたい値
     */
    public void setTargetdir(final String arg) {
        fInput.setTargetdir(arg);
        fIsFieldTargetdirProcessed = true;
    }

    /**
     * Antタスクの[targetdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 2<br>
     * 出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。<br>
     * デフォルト値[blanco]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTargetdir() {
        return fInput.getTargetdir();
    }

    /**
     * Antタスクの[tmpdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 3<br>
     * テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。<br>
     *
     * @param arg セットしたい値
     */
    public void setTmpdir(final String arg) {
        fInput.setTmpdir(arg);
        fIsFieldTmpdirProcessed = true;
    }

    /**
     * Antタスクの[tmpdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 3<br>
     * テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。<br>
     * デフォルト値[tmp]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTmpdir() {
        return fInput.getTmpdir();
    }

    /**
     * Antタスクの[nameAdjust]アトリビュートのセッターメソッド。
     *
     * 項目番号: 4<br>
     * フィールド名やメソッド名を名前変形を実施するかどうか。<br>
     *
     * @param arg セットしたい値
     */
    public void setNameAdjust(final boolean arg) {
        fInput.setNameAdjust(arg);
        fIsFieldNameAdjustProcessed = true;
    }

    /**
     * Antタスクの[nameAdjust]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 4<br>
     * フィールド名やメソッド名を名前変形を実施するかどうか。<br>
     * デフォルト値[true]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public boolean getNameAdjust() {
        return fInput.getNameAdjust();
    }

    /**
     * Antタスクの[encoding]アトリビュートのセッターメソッド。
     *
     * 項目番号: 5<br>
     * 自動生成するソースファイルの文字エンコーディングを指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setEncoding(final String arg) {
        fInput.setEncoding(arg);
        fIsFieldEncodingProcessed = true;
    }

    /**
     * Antタスクの[encoding]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 5<br>
     * 自動生成するソースファイルの文字エンコーディングを指定します。<br>
     *
     * @return このフィールドの値
     */
    public String getEncoding() {
        return fInput.getEncoding();
    }

    /**
     * Antタスクの[tabs]アトリビュートのセッターメソッド。
     *
     * 項目番号: 6<br>
     * タブをwhite spaceいくつで置き換えるか、という値です。<br>
     *
     * @param arg セットしたい値
     */
    public void setTabs(final String arg) {
        try {
            fInput.setTabs(Integer.parseInt(arg));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Antタスクの[tabs]アトリビュートに与えられた値の数値解析に失敗しました。" + e.toString());
        }
        fIsFieldTabsProcessed = true;
    }

    /**
     * Antタスクの[tabs]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 6<br>
     * タブをwhite spaceいくつで置き換えるか、という値です。<br>
     * デフォルト値[4]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTabs() {
        return String.valueOf(fInput.getTabs());
    }

    /**
     * Antタスクの[xmlrootelement]アトリビュートのセッターメソッド。
     *
     * 項目番号: 7<br>
     * XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。<br>
     *
     * @param arg セットしたい値
     */
    public void setXmlrootelement(final boolean arg) {
        fInput.setXmlrootelement(arg);
        fIsFieldXmlrootelementProcessed = true;
    }

    /**
     * Antタスクの[xmlrootelement]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 7<br>
     * XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public boolean getXmlrootelement() {
        return fInput.getXmlrootelement();
    }

    /**
     * Antタスクの[sheetType]アトリビュートのセッターメソッド。
     *
     * 項目番号: 8<br>
     * meta定義書が期待しているプログラミング言語を指定します<br>
     *
     * @param arg セットしたい値
     */
    public void setSheetType(final String arg) {
        fInput.setSheetType(arg);
        fIsFieldSheetTypeProcessed = true;
    }

    /**
     * Antタスクの[sheetType]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 8<br>
     * meta定義書が期待しているプログラミング言語を指定します<br>
     * デフォルト値[java]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getSheetType() {
        return fInput.getSheetType();
    }

    /**
     * Antタスクの[targetStyle]アトリビュートのセッターメソッド。
     *
     * 項目番号: 9<br>
     * 出力先フォルダの書式を指定します。&lt;br&gt;\nblanco: [targetdir]/main&lt;br&gt;\nmaven: [targetdir]/main/java&lt;br&gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)<br>
     *
     * @param arg セットしたい値
     */
    public void setTargetStyle(final String arg) {
        fInput.setTargetStyle(arg);
        fIsFieldTargetStyleProcessed = true;
    }

    /**
     * Antタスクの[targetStyle]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 9<br>
     * 出力先フォルダの書式を指定します。&lt;br&gt;\nblanco: [targetdir]/main&lt;br&gt;\nmaven: [targetdir]/main/java&lt;br&gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)<br>
     * デフォルト値[blanco]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTargetStyle() {
        return fInput.getTargetStyle();
    }

    /**
     * Antタスクの[client]アトリビュートのセッターメソッド。
     *
     * 項目番号: 10<br>
     * trueの場合はサーバ用のメソッドを生成しません。<br>
     *
     * @param arg セットしたい値
     */
    public void setClient(final boolean arg) {
        fInput.setClient(arg);
        fIsFieldClientProcessed = true;
    }

    /**
     * Antタスクの[client]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 10<br>
     * trueの場合はサーバ用のメソッドを生成しません。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public boolean getClient() {
        return fInput.getClient();
    }

    /**
     * Antタスクの[processlist]アトリビュートのセッターメソッド。
     *
     * 項目番号: 11<br>
     * 生成した電文処理のインスタンスを文字列から取得するための配列を生成する場合は、ここにファイル名を指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setProcesslist(final String arg) {
        fInput.setProcesslist(arg);
        fIsFieldProcesslistProcessed = true;
    }

    /**
     * Antタスクの[processlist]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 11<br>
     * 生成した電文処理のインスタンスを文字列から取得するための配列を生成する場合は、ここにファイル名を指定します。<br>
     *
     * @return このフィールドの値
     */
    public String getProcesslist() {
        return fInput.getProcesslist();
    }

    /**
     * Antタスクの[processlistBase]アトリビュートのセッターメソッド。
     *
     * 項目番号: 12<br>
     * processList に記述する import 文のbasedirを指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setProcesslistBase(final String arg) {
        fInput.setProcesslistBase(arg);
        fIsFieldProcesslistBaseProcessed = true;
    }

    /**
     * Antタスクの[processlistBase]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 12<br>
     * processList に記述する import 文のbasedirを指定します。<br>
     * デフォルト値[%]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getProcesslistBase() {
        return fInput.getProcesslistBase();
    }

    /**
     * Antタスクの[lineSeparator]アトリビュートのセッターメソッド。
     *
     * 項目番号: 13<br>
     * 行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。<br>
     *
     * @param arg セットしたい値
     */
    public void setLineSeparator(final String arg) {
        fInput.setLineSeparator(arg);
        fIsFieldLineSeparatorProcessed = true;
    }

    /**
     * Antタスクの[lineSeparator]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 13<br>
     * 行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。<br>
     * デフォルト値[LF]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getLineSeparator() {
        return fInput.getLineSeparator();
    }

    /**
     * Antタスクの[searchTmpdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 14<br>
     * import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。<br>
     *
     * @param arg セットしたい値
     */
    public void setSearchTmpdir(final String arg) {
        fInput.setSearchTmpdir(arg);
        fIsFieldSearchTmpdirProcessed = true;
    }

    /**
     * Antタスクの[searchTmpdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 14<br>
     * import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。<br>
     *
     * @return このフィールドの値
     */
    public String getSearchTmpdir() {
        return fInput.getSearchTmpdir();
    }

    /**
     * Antタスクの[generateToJson]アトリビュートのセッターメソッド。
     *
     * 項目番号: 15<br>
     * toJSONメソッドを生成します<br>
     *
     * @param arg セットしたい値
     */
    public void setGenerateToJson(final boolean arg) {
        fInput.setGenerateToJson(arg);
        fIsFieldGenerateToJsonProcessed = true;
    }

    /**
     * Antタスクの[generateToJson]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 15<br>
     * toJSONメソッドを生成します<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public boolean getGenerateToJson() {
        return fInput.getGenerateToJson();
    }

    /**
     * Antタスクの[apiTelegramPackage]アトリビュートのセッターメソッド。
     *
     * 項目番号: 16<br>
     * 電文の親クラスの配置場所を、Javaのパッケージ形式で指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setApiTelegramPackage(final String arg) {
        fInput.setApiTelegramPackage(arg);
        fIsFieldApiTelegramPackageProcessed = true;
    }

    /**
     * Antタスクの[apiTelegramPackage]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 16<br>
     * 電文の親クラスの配置場所を、Javaのパッケージ形式で指定します。<br>
     *
     * @return このフィールドの値
     */
    public String getApiTelegramPackage() {
        return fInput.getApiTelegramPackage();
    }

    /**
     * Antタスクの[apiTelegramBase]アトリビュートのセッターメソッド。
     *
     * 項目番号: 17<br>
     * 電文の親クラスの配置場所のaliasを指定します。通常は % です。<br>
     *
     * @param arg セットしたい値
     */
    public void setApiTelegramBase(final String arg) {
        fInput.setApiTelegramBase(arg);
        fIsFieldApiTelegramBaseProcessed = true;
    }

    /**
     * Antタスクの[apiTelegramBase]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 17<br>
     * 電文の親クラスの配置場所のaliasを指定します。通常は % です。<br>
     * デフォルト値[%]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getApiTelegramBase() {
        return fInput.getApiTelegramBase();
    }

    /**
     * Antタスクの[telegramStyle]アトリビュートのセッターメソッド。
     *
     * 項目番号: 18<br>
     * 電文の形式を指定します。\nblanco: 電文をCommonRequest/CommonResponseでくるみます。\nplain: 電文を直接 payload に乗せます。GET は第一階層がクエリ文字列として定義されます。<br>
     *
     * @param arg セットしたい値
     */
    public void setTelegramStyle(final String arg) {
        fInput.setTelegramStyle(arg);
        fIsFieldTelegramStyleProcessed = true;
    }

    /**
     * Antタスクの[telegramStyle]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 18<br>
     * 電文の形式を指定します。\nblanco: 電文をCommonRequest/CommonResponseでくるみます。\nplain: 電文を直接 payload に乗せます。GET は第一階層がクエリ文字列として定義されます。<br>
     * デフォルト値[blanco]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTelegramStyle() {
        return fInput.getTelegramStyle();
    }

    /**
     * Antタスクのメイン処理。Apache Antから このメソッドが呼び出されます。
     *
     * @throws BuildException タスクとしての例外が発生した場合。
     */
    @Override
    public final void execute() throws BuildException {
        System.out.println("BlancoRestGeneratorTsTask begin.");

        // 項目番号[1], アトリビュート[metadir]は必須入力です。入力チェックを行います。
        if (fIsFieldMetadirProcessed == false) {
            throw new BuildException("必須アトリビュート[metadir]が設定されていません。処理を中断します。");
        }

        if (getVerbose()) {
            System.out.println("- verbose:[true]");
            System.out.println("- metadir:[" + getMetadir() + "]");
            System.out.println("- targetdir:[" + getTargetdir() + "]");
            System.out.println("- tmpdir:[" + getTmpdir() + "]");
            System.out.println("- nameAdjust:[" + getNameAdjust() + "]");
            System.out.println("- encoding:[" + getEncoding() + "]");
            System.out.println("- tabs:[" + getTabs() + "]");
            System.out.println("- xmlrootelement:[" + getXmlrootelement() + "]");
            System.out.println("- sheetType:[" + getSheetType() + "]");
            System.out.println("- targetStyle:[" + getTargetStyle() + "]");
            System.out.println("- client:[" + getClient() + "]");
            System.out.println("- processlist:[" + getProcesslist() + "]");
            System.out.println("- processlistBase:[" + getProcesslistBase() + "]");
            System.out.println("- lineSeparator:[" + getLineSeparator() + "]");
            System.out.println("- searchTmpdir:[" + getSearchTmpdir() + "]");
            System.out.println("- generateToJson:[" + getGenerateToJson() + "]");
            System.out.println("- apiTelegramPackage:[" + getApiTelegramPackage() + "]");
            System.out.println("- apiTelegramBase:[" + getApiTelegramBase() + "]");
            System.out.println("- telegramStyle:[" + getTelegramStyle() + "]");
        }

        try {
            // 実際のAntタスクの主処理を実行します。
            // If you get a compile error at this point, You may be able to solve it by implementing a BlancoRestGeneratorTsProcess interface and creating an BlancoRestGeneratorTsProcessImpl class in package blanco.restgeneratorts.task.
            final BlancoRestGeneratorTsProcess proc = new BlancoRestGeneratorTsProcessImpl();
            if (proc.execute(fInput) != BlancoRestGeneratorTsBatchProcess.END_SUCCESS) {
                throw new BuildException("The task has terminated abnormally.");
            }
        } catch (IllegalArgumentException e) {
            if (getVerbose()) {
                e.printStackTrace();
            }
            throw new BuildException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException("タスクを処理中に例外が発生しました。処理を中断します。" + e.toString());
        } catch (Error e) {
            e.printStackTrace();
            throw new BuildException("タスクを処理中にエラーが発生しました。処理を中断します。" + e.toString());
        }
    }
}
