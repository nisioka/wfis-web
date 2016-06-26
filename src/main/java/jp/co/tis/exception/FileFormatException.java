package jp.co.tis.exception;

/**
 * FileFormat例外クラス｡
 *
 * @author Shintaro Katafuchi
 * @since 1.0
 */
public class FileFormatException extends Exception {

    /** SUID */
    private static final long serialVersionUID = 1L;

    /**
     * 引数なしコンストラクタ。
     */
    public FileFormatException() {
        super();
    }

    /**
     * メッセージとネスト例外設定用コンストラクタ。
     *
     * @param message 例外メッセージ
     * @param cause 原因となる例外またはエラー
     */
    public FileFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * メッセージ設定用コンストラクタ。
     *
     * @param message 例外メッセージ
     */
    public FileFormatException(String message) {
        super(message);
    }

    /**
     * ネスト例外設定用コンストラクタ。
     *
     * @param cause 原因となる例外またはエラー
     */
    public FileFormatException(Throwable cause) {
        super(cause);
    }

}