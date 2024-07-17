package blanco.restgeneratorts.task;

import java.io.IOException;

import blanco.restgeneratorts.task.valueobject.BlancoRestGeneratorTsProcessInput;

/**
 * Batch process class [BlancoRestGeneratorTsBatchProcess].
 *
 * <P>Example of a batch processing call.</P>
 * <code>
 * java -classpath (classpath) blanco.restgeneratorts.task.BlancoRestGeneratorTsBatchProcess -help
 * </code>
 */
public class BlancoRestGeneratorTsBatchProcess {
    /**
     * Normal end.
     */
    public static final int END_SUCCESS = 0;

    /**
     * Termination due to abnormal input. In the case that java.lang.IllegalArgumentException is raised internally.
     */
    public static final int END_ILLEGAL_ARGUMENT_EXCEPTION = 7;

    /**
     * Termination due to I/O exception. In the case that java.io.IOException is raised internally.
     */
    public static final int END_IO_EXCEPTION = 8;

    /**
     * Abnormal end. In the case that batch process fails to start or java.lang.Error or java.lang.RuntimeException is raised internally.
     */
    public static final int END_ERROR = 9;

    /**
     * The entry point when executed from the command line.
     *
     * @param args Agruments inherited from the console.
     */
    public static final void main(final String[] args) {
        final BlancoRestGeneratorTsBatchProcess batchProcess = new BlancoRestGeneratorTsBatchProcess();

        // Arguments for batch process.
        final BlancoRestGeneratorTsProcessInput input = new BlancoRestGeneratorTsProcessInput();

        boolean isNeedUsage = false;
        boolean isFieldMetadirProcessed = false;

        // Parses command line arguments.
        for (int index = 0; index < args.length; index++) {
            String arg = args[index];
            if (arg.startsWith("-verbose=")) {
                input.setVerbose(Boolean.valueOf(arg.substring(9)).booleanValue());
            } else if (arg.startsWith("-metadir=")) {
                input.setMetadir(arg.substring(9));
                isFieldMetadirProcessed = true;
            } else if (arg.startsWith("-targetdir=")) {
                input.setTargetdir(arg.substring(11));
            } else if (arg.startsWith("-tmpdir=")) {
                input.setTmpdir(arg.substring(8));
            } else if (arg.startsWith("-nameAdjust=")) {
                input.setNameAdjust(Boolean.valueOf(arg.substring(12)).booleanValue());
            } else if (arg.startsWith("-encoding=")) {
                input.setEncoding(arg.substring(10));
            } else if (arg.startsWith("-tabs=")) {
                try {
                    input.setTabs(Integer.parseInt(arg.substring(6)));
                } catch (NumberFormatException e) {
                    System.out.println("BlancoRestGeneratorTsBatchProcess: Failed to start the process. Tried to parse the field [tabs] of the input parameter[input] as a number (int), but it failed.: " + e.toString());
                    System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
                }
            } else if (arg.startsWith("-xmlrootelement=")) {
                input.setXmlrootelement(Boolean.valueOf(arg.substring(16)).booleanValue());
            } else if (arg.startsWith("-sheetType=")) {
                input.setSheetType(arg.substring(11));
            } else if (arg.startsWith("-targetStyle=")) {
                input.setTargetStyle(arg.substring(13));
            } else if (arg.startsWith("-client=")) {
                input.setClient(Boolean.valueOf(arg.substring(8)).booleanValue());
            } else if (arg.startsWith("-processlist=")) {
                input.setProcesslist(arg.substring(13));
            } else if (arg.startsWith("-processlistBase=")) {
                input.setProcesslistBase(arg.substring(17));
            } else if (arg.startsWith("-lineSeparator=")) {
                input.setLineSeparator(arg.substring(15));
            } else if (arg.startsWith("-searchTmpdir=")) {
                input.setSearchTmpdir(arg.substring(14));
            } else if (arg.startsWith("-generateToJson=")) {
                input.setGenerateToJson(Boolean.valueOf(arg.substring(16)).booleanValue());
            } else if (arg.startsWith("-apiTelegramPackage=")) {
                input.setApiTelegramPackage(arg.substring(20));
            } else if (arg.startsWith("-apiTelegramBase=")) {
                input.setApiTelegramBase(arg.substring(17));
            } else if (arg.startsWith("-telegramStyle=")) {
                input.setTelegramStyle(arg.substring(15));
            } else if (arg.startsWith("-preferAlias=")) {
                input.setPreferAlias(Boolean.valueOf(arg.substring(13)).booleanValue());
            } else if (arg.equals("-?") || arg.equals("-help")) {
                usage();
                System.exit(END_SUCCESS);
            } else {
                System.out.println("BlancoRestGeneratorTsBatchProcess: The input parameter[" + arg + "] was ignored.");
                isNeedUsage = true;
            }
        }

        if (isNeedUsage) {
            usage();
        }

        if( isFieldMetadirProcessed == false) {
            System.out.println("BlancoRestGeneratorTsBatchProcess: Failed to start the process. The required field value[metadir] in the input parameter[input] is not set to a value.");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }

        int retCode = batchProcess.execute(input);

        // Returns the exit code.
        // Note: Please note that calling System.exit().
        System.exit(retCode);
    }

    /**
     * A method to describe the specific batch processing contents.
     *
     * This method is used to describe the actual process.
     *
     * @param input Input parameters for batch process.
     * @return The exit code for batch process. Returns one of the values END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR
     * @throws IOException If an I/O exception occurs.
     * @throws IllegalArgumentException If an invalid input value is found.
     */
    public int process(final BlancoRestGeneratorTsProcessInput input) throws IOException, IllegalArgumentException {
        // Checks the input parameters.
        validateInput(input);

        // If you get a compile error at this point, You may be able to solve it by implementing a BlancoRestGeneratorTsProcess interface and creating an BlancoRestGeneratorTsProcessImpl class in package blanco.restgeneratorts.task.
        final BlancoRestGeneratorTsProcess process = new BlancoRestGeneratorTsProcessImpl();

        // Executes the main body of the process.
        final int retCode = process.execute(input);

        return retCode;
    }

    /**
     * The entry point for instantiating a class and running a batch.
     *
     * This method provides the following specifications.
     * <ul>
     * <li>Checks the contents of the input parameters of the method.
     * <li>Catches exceptions such as IllegalArgumentException, RuntimeException, Error, etc. and converts them to return values.
     * </ul>
     *
     * @param input Input parameters for batch process.
     * @return The exit code for batch process. Returns one of the values END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR
     * @throws IllegalArgumentException If an invalid input value is found.
     */
    public final int execute(final BlancoRestGeneratorTsProcessInput input) throws IllegalArgumentException {
        try {
            // Executes the main body of the batch process.
            int retCode = process(input);

            return retCode;
        } catch (IllegalArgumentException ex) {
            System.out.println("BlancoRestGeneratorTsBatchProcess: An input exception has occurred. Abort the batch process.:" + ex.toString());
            // Termination due to abnormal input.
            return END_ILLEGAL_ARGUMENT_EXCEPTION;
        } catch (IOException ex) {
            System.out.println("BlancoRestGeneratorTsBatchProcess: An I/O exception has occurred. Abort the batch process.:" + ex.toString());
            // Termination due to abnormal input.
            return END_IO_EXCEPTION;
        } catch (RuntimeException ex) {
            System.out.println("BlancoRestGeneratorTsBatchProcess: A runtime exception has occurred. Abort the batch process.:" + ex.toString());
            ex.printStackTrace();
            // Abnormal end.
            return END_ERROR;
        } catch (Error er) {
            System.out.println("BlancoRestGeneratorTsBatchProcess: A runtime exception has occurred. Abort the batch process.:" + er.toString());
            er.printStackTrace();
            // Abnormal end.
            return END_ERROR;
        }
    }

    /**
     * A method to show an explanation of how to use this batch processing class on the stdout.
     */
    public static final void usage() {
        System.out.println("BlancoRestGeneratorTsBatchProcess: Usage:");
        System.out.println("  java blanco.restgeneratorts.task.BlancoRestGeneratorTsBatchProcess -verbose=value1 -metadir=value2 -targetdir=value3 -tmpdir=value4 -nameAdjust=value5 -encoding=value6 -tabs=value7 -xmlrootelement=value8 -sheetType=value9 -targetStyle=value10 -client=value11 -processlist=value12 -processlistBase=value13 -lineSeparator=value14 -searchTmpdir=value15 -generateToJson=value16 -apiTelegramPackage=value17 -apiTelegramBase=value18 -telegramStyle=value19 -preferAlias=value20");
        System.out.println("    -verbose");
        System.out.println("      explanation[Whether to run in verbose mode.]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -metadir");
        System.out.println("      explanation[メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。]");
        System.out.println("      type[string]");
        System.out.println("      a required parameter");
        System.out.println("    -targetdir");
        System.out.println("      explanation[出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。]");
        System.out.println("      type[string]");
        System.out.println("      default value[blanco]");
        System.out.println("    -tmpdir");
        System.out.println("      explanation[テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。]");
        System.out.println("      type[string]");
        System.out.println("      default value[tmp]");
        System.out.println("    -nameAdjust");
        System.out.println("      explanation[フィールド名やメソッド名を名前変形を実施するかどうか。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[true]");
        System.out.println("    -encoding");
        System.out.println("      explanation[自動生成するソースファイルの文字エンコーディングを指定します。]");
        System.out.println("      type[string]");
        System.out.println("    -tabs");
        System.out.println("      explanation[タブをwhite spaceいくつで置き換えるか、という値です。]");
        System.out.println("      type[number(int)]");
        System.out.println("      default value[4]");
        System.out.println("    -xmlrootelement");
        System.out.println("      explanation[XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -sheetType");
        System.out.println("      explanation[meta定義書が期待しているプログラミング言語を指定します]");
        System.out.println("      type[string]");
        System.out.println("      default value[java]");
        System.out.println("    -targetStyle");
        System.out.println("      explanation[出力先フォルダの書式を指定します。<br>\nblanco: [targetdir]/main<br>\nmaven: [targetdir]/main/java<br>\nfree: [targetdir](targetdirが無指定の場合はblanco/main)]");
        System.out.println("      type[string]");
        System.out.println("      default value[blanco]");
        System.out.println("    -client");
        System.out.println("      explanation[trueの場合はサーバ用のメソッドを生成しません。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -processlist");
        System.out.println("      explanation[生成した電文処理のインスタンスを文字列から取得するための配列を生成する場合は、ここにファイル名を指定します。]");
        System.out.println("      type[string]");
        System.out.println("    -processlistBase");
        System.out.println("      explanation[processList に記述する import 文のbasedirを指定します。]");
        System.out.println("      type[string]");
        System.out.println("      default value[%]");
        System.out.println("    -lineSeparator");
        System.out.println("      explanation[行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。]");
        System.out.println("      type[string]");
        System.out.println("      default value[LF]");
        System.out.println("    -searchTmpdir");
        System.out.println("      explanation[import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。]");
        System.out.println("      type[string]");
        System.out.println("    -generateToJson");
        System.out.println("      explanation[toJSONメソッドを生成します]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -apiTelegramPackage");
        System.out.println("      explanation[電文の親クラスの配置場所を、Javaのパッケージ形式で指定します。]");
        System.out.println("      type[string]");
        System.out.println("    -apiTelegramBase");
        System.out.println("      explanation[電文の親クラスの配置場所のaliasを指定します。通常は % です。]");
        System.out.println("      type[string]");
        System.out.println("      default value[%]");
        System.out.println("    -telegramStyle");
        System.out.println("      explanation[電文の形式を指定します。\nblanco: 電文をCommonRequest/CommonResponseでくるみます。\nplain: 電文を直接 payload に乗せます。GET は第一階層がクエリ文字列として定義されます。]");
        System.out.println("      type[string]");
        System.out.println("      default value[blanco]");
        System.out.println("    -preferAlias");
        System.out.println("      explanation[プロパティ値に別名が設定されていた場合、name を alias で上書きする。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -? , -help");
        System.out.println("      explanation[show the usage.]");
    }

    /**
     * A method to check the validity of input parameters for this batch processing class.
     *
     * @param input Input parameters for batch process.
     * @throws IllegalArgumentException If an invalid input value is found.
     */
    public void validateInput(final BlancoRestGeneratorTsProcessInput input) throws IllegalArgumentException {
        if (input == null) {
            throw new IllegalArgumentException("BlancoBatchProcessBatchProcess: Failed to start the process. The input parameter[input] was given as null.");
        }
        if (input.getMetadir() == null) {
            throw new IllegalArgumentException("BlancoRestGeneratorTsBatchProcess: Failed to start the process. The required field value[metadir] in the input parameter[input] is not set to a value.");
        }
    }
}
