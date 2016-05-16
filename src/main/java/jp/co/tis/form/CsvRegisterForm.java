package jp.co.tis.form;

import java.io.Serializable;

/**
 * CSV登録用Form。<br/>
 * 画面の入力項目や画面から送られてくる項目をプロパティとして持つクラス。
 *
 * @author Saito Takuma
 * @since 1.0
 */
public class CsvRegisterForm implements Serializable {

    /** ファイルパス */
    private String filePath;

    /**
     * デフォルトコンストラクタ。
     */
    public CsvRegisterForm() {
    }

    /**
     * ファイルパスを取得する。
     *
     * @return ファイルパス
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * ファイルパスを設定する。
     *
     * @param filePath ファイルパス
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
