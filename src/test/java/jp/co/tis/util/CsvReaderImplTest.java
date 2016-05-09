package jp.co.tis.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import jp.co.tis.exception.FileFormatException;

/**
 * CsvReaderImplをテストするクラス。
 *
 * @author Yoshiwara Masashi
 * @since 1.0
 */
public class CsvReaderImplTest {

    /**
     * ルール設定
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * テストケース仕様書項目番号1-1を参照。
     *
     * @throws Exception 例外。
     */
    @Test
    public void testOpenNormally() throws Exception {
        CsvReader csvReader = new CsvReaderImpl("src/test/java/jp/co/tis/util/testData/normal.csv");
        csvReader.open();
    }

    /**
     * テストケース仕様書項目番号1-2を参照。
     *
     * @throws Exception 例外。
     */
    @Test
    public void testOpenInCaseOfFileNotFoundException() throws Exception {
        CsvReader csvReader = new CsvReaderImpl("noFile.csv");
        exception.expect(FileNotFoundException.class);
        // JavaAPIからthrowされる例外なので、例外発生のみチェックしメッセージ内容のチェックはしない。
        csvReader.open();
    }

    /**
     * テストケース仕様書項目番号1-3を参照。
     *
     * @throws Exception 例外。
     */
    @Test
    public void testOpenInCaseOfFileFormatException() throws Exception {
        CsvReader csvReader = new CsvReaderImpl("src/test/java/jp/co/tis/util/testData/testOpenInCaseOfFileFormatException1.csv");
        exception.expect(FileFormatException.class);
        exception.expectMessage("ヘッダー行が存在しません。");
        csvReader.open();
    }

    /**
     * テストケース仕様書項目番号1-4を参照。
     *
     * @throws Exception 例外。
     */
    @Test
    public void testOpenInCaseOfFileFormatException2() throws Exception {
        CsvReader csvReader = new CsvReaderImpl("src/test/java/jp/co/tis/util/testData/testOpenInCaseOfFileFormatException2.csv");
        exception.expect(FileFormatException.class);
        exception.expectMessage("ヘッダー行の項目が重複しています。");
        csvReader.open();
    }

    /**
     * テストケース仕様書項目番号1-5を参照。
     *
     * @throws Exception 例外。
     */
    @Test
    public void testOpenInCaseOfFileFormatException3() throws Exception {
        CsvReader csvReader = new CsvReaderImpl("src/test/java/jp/co/tis/util/testData/testOpenInCaseOfFileFormatException3.csv");
        exception.expect(FileFormatException.class);
        exception.expectMessage("ヘッダー行に空項目が含まれています。");
        csvReader.open();
    }

    /**
     * テストケース仕様書項目番号2-1を参照。
     *
     * @throws Exception 例外。
     */
    @Test
    public void testCloseAfterOpen() throws Exception {
        CsvReader csvReader = new CsvReaderImpl("src/test/java/jp/co/tis/util/testData/normal.csv");
        csvReader.open();
        csvReader.close();
        exception.expect(IOException.class);
        exception.expectMessage("ファイルが開かれていません。");
        csvReader.readLine();
    }

    /**
     * テストケース仕様書項目番号3-1を参照。
     *
     * @throws Exception 例外。
     */
    @Test
    public void testReadLineOneTime() throws Exception {
        CsvReader csvReader = new CsvReaderImpl("src/test/java/jp/co/tis/util/testData/normal.csv");
        csvReader.open();
        Map<String, String> map = csvReader.readLine();
        assertThat(map.get("id"), is("1"));
        assertThat(map.get("name"), is("ikeda"));
        assertThat(map.get("age"), is("25"));

    }

    /**
     * テストケース仕様書項目番号3-2を参照。
     *
     * @throws Exception 例外。
     */
    @Test
    public void testReadLineAtFileEnd() throws Exception {
        CsvReader csvReader = new CsvReaderImpl("src/test/java/jp/co/tis/util/testData/normal.csv");
        csvReader.open();
        csvReader.readLine();
        csvReader.readLine();
        csvReader.readLine();
        assertThat(csvReader.readLine(), is(nullValue()));
    }

    /**
     * テストケース仕様書項目番号3-3を参照。
     *
     * @throws Exception 例外。
     */
    @Test
    public void testReadLineInCaseOfFileFormatException() throws Exception {
        CsvReader csvReader = new CsvReaderImpl("src/test/java/jp/co/tis/util/testData/testReadLineInCaseOfFileFormatException.csv");
        csvReader.open();
        exception.expect(FileFormatException.class);
        exception.expectMessage("ヘッダー部と項目数が異なっています。");
        csvReader.readLine();
    }

    /**
     * テストケース仕様書項目番号3-4を参照。
     *
     * @throws Exception 例外。
     */
    @Test
    public void testReadLineWhenMapIsNull() throws Exception {
        CsvReader csvReader = new CsvReaderImpl("src/test/java/jp/co/tis/util/testData/testReadLineWhenMapIsNull.csv");
        csvReader.open();
        assertThat(csvReader.readLine(), is(nullValue()));
    }

    /**
     * テストケース仕様書項目番号3-5を参照。
     *
     * @throws Exception 例外。
     */
    @Test
    public void testReadLineWhenDataHaveBlank() throws Exception {
        CsvReader csvReader = new CsvReaderImpl("src/test/java/jp/co/tis/util/testData/testReadLineWhenDataHaveBlank.csv");
        csvReader.open();
        Map<String, String> map = csvReader.readLine();
        assertThat(map.get("A"), is(nullValue()));
        assertThat(map.get("B"), is(" "));
        assertThat(map.get("C"), is("  "));
    }

}
