package jp.co.tis.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import jp.co.tis.exception.FileFormatException;
import jp.co.tis.exception.SystemException;

/**
 * CsvReader実装クラス。
 *
 * @author Nomura Tomoka, Murakami Hiroyuki, Yoshiwara Masashi
 * @since 2.1
 */
public class CsvReaderImpl implements CsvReader {

    /** 読み込みのためのBufferedReaderクラス。 */
    private BufferedReader bufferedReader;

    /** ヘッダー部の項目を格納する配列。 */
    private String[] itemArray;

    /** CSVファイルのパスを格納する文字列。 */
    private String csvPath;

    /**
     * コンストラクタ。
     *
     * @param csvPath CSVファイルパス。
     */
    public CsvReaderImpl(String csvPath) {
        this.csvPath = csvPath;
    }

    @Override
    public void open() throws FileNotFoundException, FileFormatException {
        if (bufferedReader != null) {
            throw new IllegalStateException("すでにファイルが開かれています。");
        }
        bufferedReader = new BufferedReader(new FileReader(csvPath));
        // ※実行環境依存を避ける実装方法
        // 読みこみのエンコードを指定する。
        // bufferedReader = new BufferedReader(new InputStreamReader(
        // new FileInputStream(csvPath), Charset.forName("UTF-8")));

        String headerSection = null;
        try {
            headerSection = bufferedReader.readLine();
        } catch (IOException e) {
            // テストで到達不可能
            throw new SystemException("システム例外が発生しました。", e);
        }
        if (headerSection == null || headerSection.isEmpty()) {
            throw new FileFormatException("ヘッダー行が存在しません。");
        }
        itemArray = headerSection.split(",", -1);
        // 重複チェック開始
        Set<String> checkRepetition = new HashSet<String>();
        for (String item : itemArray) {
            if (!checkRepetition.add(item)) {
                throw new FileFormatException("ヘッダー行の項目が重複しています。");
            }
        }
        // 空項目チェック開始
        for (String item : itemArray) {
            if ("".equals(item)) {
                throw new FileFormatException("ヘッダー行に空項目が含まれています。");
            }
        }

    }

    @Override
    public void close() {
        if (bufferedReader == null) {
            return;
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            // テストで到達不可能
            throw new SystemException("システム例外が発生しました。", e);
        } finally {
            bufferedReader = null;
        }
    }

    @Override
    public Map<String, String> readLine() throws IOException, FileFormatException {
        if (bufferedReader == null) {
            throw new IOException("ファイルが開かれていません。");
        }

        String dataSection = null;
        try {
            dataSection = bufferedReader.readLine();
        } catch (IOException e) {
            // テストで到達不可能
            throw new SystemException("システム例外が発生しました。", e);
        }

        if (dataSection == null) {
            return null;
        }
        String[] dataArray = dataSection.split(",", -1);
        if (itemArray.length != dataArray.length) {
            throw new FileFormatException("ヘッダー部と項目数が異なっています。");
        }

        Map<String, String> keyValue = new LinkedHashMap<String, String>();
        for (int i = 0; i < dataArray.length; i++) {
            if (dataArray[i].isEmpty()) {
                keyValue.put(itemArray[i], null);
            } else {
                keyValue.put(itemArray[i], dataArray[i]);
            }
        }
        return keyValue;
    }
}
