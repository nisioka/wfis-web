package jp.co.tis.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import jp.co.tis.exception.FileFormatException;

/**
 * CSV読込みインタフェース｡
 *
 * @author Shintaro Katafuchi, Murakami Hiroyuki
 * @since 2.0
 */
public interface CsvReader {

    /**
     * ファイルをオープンする。<br/>
     * ヘッダー部の読み込みを行う。ヘッダー部が設定されていない場合、項目名に重複がある場合は例外を送出する。<br/>
     * {@link #close}を呼び出さずに再度本メソッドを呼び出すと、IllegalStateExceptionを送出する。
     *
     * @throws FileNotFoundException ファイルが存在しない場合
     * @throws FileFormatException ヘッダー部が設定されていない場合、項目名に重複がある場合
     */
    void open() throws FileNotFoundException, FileFormatException;

    /**
     * 行単位の読み込みを行う。<br/>
     * CSVを一行読込み、項目名をキーとしたMapに変換し返却する。<br/>
     * ファイル末端に達している場合には{@code null}を返却する。<br/>
     * 項目名に対応するデータがないときは、マップの値に{@code null}を設定する｡<br/>
     * 読み込みが終了した場合は、必ず{@link #close}を呼び出すこと。<br/>
     * ファイルがオープンしていない場合、IOExceptionを送出する。
     *
     * @return 一行分のCSVデータを保持するMap。
     * @throws IOException 入出力エラー(ファイル未オープンなど)が発生した場合。
     * @throws FileFormatException 見出し行の項目数とデータ個数に差異がある場合
     */
    Map<String, String> readLine() throws IOException, FileFormatException;

    /**
     * ファイルをクローズする。<br/>
     * 繰り返し呼び出しても何も起こらない。 {@link #open}を呼び出す前に本メソッドを呼び出しても何も起こらない。
     */
    void close();
}