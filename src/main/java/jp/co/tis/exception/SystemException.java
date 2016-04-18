/**
 * Java基礎演習
 * Copyright(C) 2013 TIS Inc. All rights reserved.
 */
package jp.co.tis.exception;

/**
 * システム例外クラス｡
 *
 * @author Shintaro Katafuchi
 * @since 1.0
 */
public class SystemException extends RuntimeException {

    /** SUID */
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     *
     * @param message 例外メッセージ
     * @param cause 原因となる例外またはエラー
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

}