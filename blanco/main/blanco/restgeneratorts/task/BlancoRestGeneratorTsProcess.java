package blanco.restgeneratorts.task;

import java.io.IOException;

import blanco.restgeneratorts.task.valueobject.BlancoRestGeneratorTsProcessInput;

/**
 * 処理 [BlancoRestGeneratorTsProcess]インタフェース。
 *
 * このインタフェースを継承して [blanco.restgeneratorts.task]パッケージに[BlancoRestGeneratorTsProcess]クラスを作成して実際のバッチ処理を実装してください。<br>
 */
interface BlancoRestGeneratorTsProcess {
    /**
     * クラスをインスタンス化して処理を実行する際のエントリポイントです。
     *
     * @param input 処理の入力パラメータ。
     * @return 処理の実行結果。
     * @throws IOException 入出力例外が発生した場合。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    int execute(final BlancoRestGeneratorTsProcessInput input) throws IOException, IllegalArgumentException;

    /**
     * 処理の中でアイテムが処理されるたびに進捗報告としてコールバックします。
     *
     * @param argProgressMessage 現在処理しているアイテムに関するメッセージ。
     * @return 処理をそのまま継続する場合は false。処理中断をリクエストしたい場合は true。
     */
    boolean progress(final String argProgressMessage);
}
