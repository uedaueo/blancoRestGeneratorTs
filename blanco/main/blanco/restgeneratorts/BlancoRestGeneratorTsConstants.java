package blanco.restgeneratorts;

/**
 * BlancoRestGeneratorTs が利用する定数を蓄えます。
 */
public class BlancoRestGeneratorTsConstants {
    /**
     * 項目番号:1<br>
     * プロダクト名。英字で指定します。
     */
    public static final String PRODUCT_NAME = "blancoRestGeneratorTs";

    /**
     * 項目番号:2<br>
     * プロダクト名の小文字版。英字で指定します。
     */
    public static final String PRODUCT_NAME_LOWER = "blancorestgeneratorts";

    /**
     * 項目番号:3<br>
     * バージョン番号。
     */
    public static final String VERSION = "3.1.13";

    /**
     * 項目番号:4<br>
     * 処理の過程で利用されるサブディレクトリ。
     */
    public static final String TARGET_SUBDIRECTORY = "/restgeneratorts";

    /**
     * 項目番号:5<br>
     * valueobjectが格納されているサブディレクトリ
     */
    public static final String OBJECT_SUBDIRECTORY = "/valueobjectts";

    /**
     * 項目番号:6<br>
     * targetdirに設定される文字列
     */
    public static final String TARGET_STYLE_BLANCO = "blanco";

    /**
     * 項目番号:7<br>
     * targetdirに設定される文字列
     */
    public static final String TARGET_STYLE_MAVEN = "maven";

    /**
     * 項目番号:8<br>
     * targetdirに設定される文字列
     */
    public static final String TARGET_STYLE_FREE = "free";

    /**
     * 項目番号:9<br>
     * 生成したソースコードを保管するディレクトリのsuffix
     */
    public static final String TARGET_DIR_SUFFIX_BLANCO = "main";

    /**
     * 項目番号:10<br>
     * 生成したソースコードを保管するディレクトリのsuffix
     */
    public static final String TARGET_DIR_SUFFIX_MAVEN = "main/typescript";

    /**
     * 項目番号:11<br>
     * 生成されるAbstractクラスのプレフィックス
     */
    public static final String PREFIX_ABSTRACT = "Abstract";

    /**
     * 項目番号:12<br>
     * APIが継承するベースクラス名
     */
    public static final String BASE_CLASS = "blanco.rest.common.ApiBase";

    /**
     * 項目番号:13<br>
     * ベースクラスから呼ばれる実行メソッド名
     */
    public static final String BASE_EXECUTOR_METHOD = "execute";

    /**
     * 項目番号:14<br>
     * API実装クラスの初期化メソッド名
     */
    public static final String API_INITIALIZER_METHOD = "initialize";

    /**
     * 項目番号:15<br>
     * API実装クラスの処理メソッド名
     */
    public static final String API_PROCESS_METHOD = "process";

    /**
     * 項目番号:16<br>
     * APIが認証を必要とする場合のフラグ変数名
     */
    public static final String API_AUTHENTICATION_REQUIRED = "isAuthenticationRequired";

    /**
     * 項目番号:17<br>
     * APIのリクエストID名を取得するメソッド名
     */
    public static final String API_GET_REQUESTID_METHOD = "getGetRequestId";

    /**
     * 項目番号:18<br>
     * APIのリクエストID名を取得するメソッド名
     */
    public static final String API_POST_REQUESTID_METHOD = "getPostRequestId";

    /**
     * 項目番号:19<br>
     * APIのリクエストID名を取得するメソッド名
     */
    public static final String API_PUT_REQUESTID_METHOD = "getPutRequestId";

    /**
     * 項目番号:20<br>
     * APIのリクエストID名を取得するメソッド名
     */
    public static final String API_DELETE_REQUESTID_METHOD = "getDeleteRequestId";

    /**
     * 項目番号:21<br>
     * APIのレスポンスID名を取得するメソッド名
     */
    public static final String API_GET_RESPONSEID_METHOD = "getGetResponseId";

    /**
     * 項目番号:22<br>
     * APIのレスポンスID名を取得するメソッド名
     */
    public static final String API_POST_RESPONSEID_METHOD = "getPostResponseId";

    /**
     * 項目番号:23<br>
     * APIのレスポンスID名を取得するメソッド名
     */
    public static final String API_PUT_RESPONSEID_METHOD = "getPutResponseId";

    /**
     * 項目番号:24<br>
     * APIのレスポンスID名を取得するメソッド名
     */
    public static final String API_DELETE_RESPONSEID_METHOD = "getDeleteResponseId";

    /**
     * 項目番号:25<br>
     * プロダクトのデフォルトパッケージ名です．
     */
    public static final String DEFAULT_PACKAGE = "blanco.restgeneratorts";

    /**
     * 項目番号:26<br>
     * ValueObjectのパッケージ名です．
     */
    public static final String VALUEOBJECT_PACKAGE = "blanco.restgeneratorts.valueobject";

    /**
     * 項目番号:27<br>
     * BlancoRestの設定ファイル名です．
     */
    public static final String CONFIG_FILE = "/etc/blancorest/config.xml";

    /**
     * 項目番号:28<br>
     * BlancoRestの標準例外クラス名です．
     */
    public static final String DEFAULT_EXCEPTION = "Error";

    /**
     * 項目番号:29<br>
     * APIの戻りステータス（Accepted）
     */
    public static final String API_STATUS_ACCEPTED = "accept";

    /**
     * 項目番号:30<br>
     * APIの戻りステータス（Dismiss）
     */
    public static final String API_STATUS_DISMISS = "dismiss";

    /**
     * 項目番号:31<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_GET_REQUESTID = "ApiGetTelegram";

    /**
     * 項目番号:32<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_POST_REQUESTID = "ApiPostTelegram";

    /**
     * 項目番号:33<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_PUT_REQUESTID = "ApiPutTelegram";

    /**
     * 項目番号:34<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_DELETE_REQUESTID = "ApiDeleteTelegram";

    /**
     * 項目番号:35<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_GET_RESPONSEID = "ApiGetTelegram";

    /**
     * 項目番号:36<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_POST_RESPONSEID = "ApiPostTelegram";

    /**
     * 項目番号:37<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_PUT_RESPONSEID = "ApiPutTelegram";

    /**
     * 項目番号:38<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_DELETE_RESPONSEID = "ApiDeleteTelegram";

    /**
     * 項目番号:39<br>
     * HTTPメソッドGETを表す文字列
     */
    public static final String HTTP_METHOD_GET = "HTTP_METHOD_GET";

    /**
     * 項目番号:40<br>
     * HTTPメソッドPOSTを表す文字列
     */
    public static final String HTTP_METHOD_POST = "HTTP_METHOD_POST";

    /**
     * 項目番号:41<br>
     * HTTPメソッドPUTを表す文字列
     */
    public static final String HTTP_METHOD_PUT = "HTTP_METHOD_PUT";

    /**
     * 項目番号:42<br>
     * HTTPメソッドDELETEを表す文字列
     */
    public static final String HTTP_METHOD_DELETE = "HTTP_METHOD_DELETE";

    /**
     * 項目番号:43<br>
     * 電文種別INPUTを表す文字列
     */
    public static final String TELEGRAM_INPUT = "TELEGRAM_INPUT";

    /**
     * 項目番号:44<br>
     * 電文種別OUTPUTを表す文字列
     */
    public static final String TELEGRAM_OUTPUT = "TELEGRAM_OUTPUT";

    /**
     * 項目番号:45<br>
     * 電文種別ERRORを表す文字列
     */
    public static final String TELEGRAM_ERROR = "TELEGRAM_ERROR";

    /**
     * 項目番号:46<br>
     * 電文の基底クラス
     */
    public static final String API_TELEGRAM_BASE = "ApiTelegram";

    /**
     * 項目番号:47<br>
     * 電文定義書に要求電文として定義された電文種別を表す文字列
     */
    public static final String TELEGRAM_TYPE_INPUT = "Input";

    /**
     * 項目番号:48<br>
     * 電文定義書に応答電文として定義された電文種別を表す文字列
     */
    public static final String TELEGRAM_TYPE_OUTPUT = "Output";

    /**
     * 項目番号:49<br>
     * 電文定義書にエラー電文として定義された電文種別を表す文字列
     */
    public static final String TELEGRAM_TYPE_ERROR = "Error";

    /**
     * 項目番号:50<br>
     * Task引数として渡される電文スタイル文字列: blanco
     */
    public static final String TELEGRAM_STYLE_BLANCO = "blanco";

    /**
     * 項目番号:51<br>
     * Task引数として渡される電文スタイル文字列: plain
     */
    public static final String TELEGRAM_STYLE_PLAIN = "plain";

    /**
     * 項目番号:52<br>
     * エラー電文に追加されるstatusCodeのプロパティ名
     */
    public static final String TELEGRAM_STATUS_CODE = "statusCode";

    /**
     * 項目番号:53<br>
     * BODYで渡されたプロパティをURIパスで渡されたプロパティで上書きする
     */
    public static final String ON_CONFLICT_PREFER_PATH = "PREFER_PATH";

    /**
     * 項目番号:54<br>
     * BODYで渡されたプロパティをURIパスより優先する
     */
    public static final String ON_CONFLICT_PREFER_BODY = "PREFER_BODY";

    /**
     * 項目番号:55<br>
     * BODYとURIパスでプロパティが重複した場合はREJECTする
     */
    public static final String ON_CONFLICT_REJECT = "REJECT";

    /**
     * 項目番号:56<br>
     * 電文プロパティをQuery文字列として受け取る際の種別
     */
    public static final String TELEGRAM_QUERY_KIND_PATH = "path";

    /**
     * 項目番号:57<br>
     * 電文プロパティをQuery文字列として受け取る際の種別
     */
    public static final String TELEGRAM_QUERY_KIND_QUERY = "query";

    /**
     * 項目番号:58<br>
     * 定義書で定義されているHTTPメソッド文字列
     */
    public static final String TELEGRAM_METHOD_GET = "GET";

    /**
     * 項目番号:59<br>
     * 定義書で定義されているHTTPメソッド文字列
     */
    public static final String TELEGRAM_METHOD_POST = "POST";

    /**
     * 項目番号:60<br>
     * 定義書で定義されているHTTPメソッド文字列
     */
    public static final String TELEGRAM_METHOD_PUT = "PUT";

    /**
     * 項目番号:61<br>
     * 定義書で定義されているHTTPメソッド文字列
     */
    public static final String TELEGRAM_METHOD_DELETE = "DELETE";
}
