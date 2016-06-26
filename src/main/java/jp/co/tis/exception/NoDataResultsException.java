package jp.co.tis.exception;

/**
 * 結果なし例外クラス。
 *
 * @author Nishioka Daisuke
 * @since 1.0
 */
public class NoDataResultsException extends RuntimeException {

    /** SUID */
    private static final long serialVersionUID = 1L;

    /**
     * 引数なしコンストラクタ。
     */
    public NoDataResultsException() {
        super();
    }

    /**
     * メッセージ設定用コンストラクタ。
     *
     * @param message 例外メッセージ
     */
    public NoDataResultsException(String message) {
        super(message);
    }

}