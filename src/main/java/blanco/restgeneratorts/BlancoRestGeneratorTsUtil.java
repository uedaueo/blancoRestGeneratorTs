package blanco.restgeneratorts;

import blanco.cg.BlancoCgSupportedLang;
import blanco.restgeneratorts.resourcebundle.BlancoRestGeneratorTsResourceBundle;
import blanco.restgeneratorts.task.valueobject.BlancoRestGeneratorTsProcessInput;
import blanco.valueobjectts.BlancoValueObjectTsXmlParser;
import blanco.valueobjectts.valueobject.BlancoValueObjectTsClassStructure;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * BlancoValueObject で作成されているObjectの一覧を XML から取得し，保持しておきます
 *
 * Created by tueda on 15/07/05.
 */
public class BlancoRestGeneratorTsUtil {
    /**
     * ValueObject 用リソースバンドルへのアクセスオブジェクト。
     */
    private final static BlancoRestGeneratorTsResourceBundle fBundle = new BlancoRestGeneratorTsResourceBundle();

    public static Map<String, Integer> mapCommons = new HashMap<String, Integer>() {
        {put(fBundle.getMeta2xmlElementCommon(), BlancoCgSupportedLang.JAVA);}
        {put(fBundle.getMeta2xmlElementCommonCs(), BlancoCgSupportedLang.CS);}
        {put(fBundle.getMeta2xmlElementCommonJs(), BlancoCgSupportedLang.JS);}
        {put(fBundle.getMeta2xmlElementCommonVb(), BlancoCgSupportedLang.VB);}
        {put(fBundle.getMeta2xmlElementCommonPhp(), BlancoCgSupportedLang.PHP);}
        {put(fBundle.getMeta2xmlElementCommonRuby(), BlancoCgSupportedLang.RUBY);}
        {put(fBundle.getMeta2xmlElementCommonPython(), BlancoCgSupportedLang.PYTHON);}
        {put(fBundle.getMeta2xmlElementCommonPython(), BlancoCgSupportedLang.KOTLIN);}
        {put(fBundle.getMeta2xmlElementCommonPython(), BlancoCgSupportedLang.TS);}
    };

    /**
     * フィールド名やメソッド名の名前変形を行うかどうか。
     */
    private boolean fNameAdjust = true;

    /**
     * 自動生成するソースファイルの文字エンコーディング。
     */
    private String fEncoding = null;

    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    private boolean fVerbose = false;
    public void setVerbose(boolean argVerbose) {
        this.fVerbose = argVerbose;
    }
    public boolean isVerbose() {
        return this.fVerbose;
    }

    public static HashMap<String, BlancoValueObjectTsClassStructure> objects = new HashMap<>();

    public void process(final BlancoRestGeneratorTsProcessInput input) throws IOException {
        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorTsObjectsInfo : process start !");
        }

        // XML化された中間ファイルから情報を読み込む
        final File[] fileMeta3 = new File(input.getTmpdir()
                + BlancoRestGeneratorTsConstants.OBJECT_SUBDIRECTORY)
                .listFiles();

        if (fileMeta3 == null) {
            System.out.println("!!! NO FILES in " + input.getTmpdir()
                    + BlancoRestGeneratorTsConstants.OBJECT_SUBDIRECTORY);
            throw new IllegalArgumentException("!!! NO FILES in " + input.getTmpdir()
                    + BlancoRestGeneratorTsConstants.OBJECT_SUBDIRECTORY);
        }

        for (int index = 0; index < fileMeta3.length; index++) {
            if (fileMeta3[index].getName().endsWith(".xml") == false) {
                continue;
            }

            BlancoValueObjectTsXmlParser parser = new BlancoValueObjectTsXmlParser();
//            parser.setVerbose(this.isVerbose());
            final BlancoValueObjectTsClassStructure[] structures = parser.parse(fileMeta3[index]);

            if (structures != null ) {
                for (int index2 = 0; index2 < structures.length; index2++) {
                    BlancoValueObjectTsClassStructure structure = structures[index2];
                    if (structure != null) {
                        if (this.isVerbose()) {
                            System.out.println("objectInfo: " + structure.getName());
                        }
                        objects.put(structure.getName(), structure);
                    } else {
                        System.out.println("BlancoRestGeneratorTsObjectsInfo: a structure is NULL!!!");
                    }
                }
            } else {
                System.out.println("BlancoRestGeneratorTsObjectsInfo: structures are NULL!!!");
            }
        }
    }

    /**
     * メソッド毎の電文の親クラスを返します。
     * @param method
     * @return
     */
    static public String getDefaultRequestTelegramId(String method) {
        String telegramId = null;

        if (method == null) {
            throw new IllegalArgumentException("!!! METHOD CANNOT BE NULL !!! " + method);
        }

        if (BlancoRestGeneratorTsConstants.HTTP_METHOD_GET.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_GET_REQUESTID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_POST.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_POST_REQUESTID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_PUT.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_PUT_REQUESTID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_DELETE.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_DELETE_REQUESTID;
        } else {
            throw new IllegalArgumentException("!!! NO SUCH METHOD !!! " + method);
        }

        return telegramId;
    }

    /**
     * メソッド毎の電文の親クラスを返します。
     * @param method
     * @return
     */
    static public String getDefaultResponseTelegramId(String method) {
        String telegramId = null;

        if (method == null) {
            throw new IllegalArgumentException("!!! METHOD CANNOT BE NULL !!! " + method);
        }

        if (BlancoRestGeneratorTsConstants.HTTP_METHOD_GET.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_GET_RESPONSEID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_POST.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_POST_RESPONSEID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_PUT.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_PUT_RESPONSEID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_DELETE.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_DELETE_RESPONSEID;
        } else {
            throw new IllegalArgumentException("!!! NO SUCH METHOD !!! " + method);
        }

        return telegramId;
    }
}
