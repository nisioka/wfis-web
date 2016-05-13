package jp.co.tis.logic;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import jp.co.tis.App;
import jp.co.tis.form.CsvRegisterForm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * CSV登録Logicのテスト。
 *
 * @author Saito Takuma
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class CsvRegisterLogicTest {

    /** テスト対象クラス */
    @Autowired
    private CsvRegisterLogic target;

    /**
     * 正常系のバリデーションテスト。
     */
    @Test
    public void testValidationNormal() {
        CsvRegisterForm form = new CsvRegisterForm();
        form.setFilePath("src/test/java/jp/co/tis/util/testData/normal.csv");

        List<String> errorList = target.validateForm(form);

        assertThat(errorList.size(), is(0));
    }

    /**
     * 異常系のバリデーションテスト（ファイルパスが未入力の場合）。
     */
    @Test
    public void testValidationEmptyFilePath() {
        CsvRegisterForm form = new CsvRegisterForm();

        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("ファイルパスは必ず入力してください。"));
    }

    /**
     * 異常系のバリデーションテスト（ファイルパスで指定したファイルの拡張子が.csvでない場合）。
     */
    @Test
    public void testValidationNotCsv() {
        CsvRegisterForm form = new CsvRegisterForm();
        form.setFilePath("あいうえお");

        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("ファイルの拡張子はcsv形式にしてください。"));
    }

    /**
     * 正常な読み込み処理。
     */
    @Test
    public void testCsvReadNormal() {
        CsvRegisterForm form = new CsvRegisterForm();
        form.setFilePath("src/test/java/jp/co/tis/util/testData/normal.csv");

        try {
            List<String> csvDataList = target.createCsvDataList(form);
            assertThat(csvDataList.get(0), is("'1','ikeda','25'"));
            assertThat(csvDataList.get(1), is("'2','noda','22'"));
            assertThat(csvDataList.get(2), is("'3','nagamine','24'"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 読み込み処理（存在しないCSVファイルに読み込み処理を実行した場合）。
     */
    @Test
    public void testCsvReadNoFile() {
        CsvRegisterForm form = new CsvRegisterForm();
        form.setFilePath("noFile.csv");

        try {
            target.createCsvDataList(form);
        } catch (Exception e) {
            assertThat(e.getMessage(), is("noFile.csv (指定されたファイルが見つかりません。)"));
        }
    }

    /**
     * 読み込み処理（ヘッダー部が存在しないCSVファイルに読み込み処理を実行した場合）。
     */
    @Test
    public void testCsvReadNoHeader() {
        CsvRegisterForm form = new CsvRegisterForm();
        form.setFilePath("src/test/java/jp/co/tis/util/testData/testOpenInCaseOfFileFormatException1.csv");

        try {
            target.createCsvDataList(form);
        } catch (Exception e) {
            assertThat(e.getMessage(), is("ヘッダー部が存在しません。"));
        }
    }

    /**
     * 読み込み処理（ヘッダー部の項目名の重複があるCSVファイルに読み込み処理を実行した場合）。
     */
    @Test
    public void testCsvReadDuplicateHeader() {
        CsvRegisterForm form = new CsvRegisterForm();
        form.setFilePath("src/test/java/jp/co/tis/util/testData/testOpenInCaseOfFileFormatException2.csv");

        try {
            target.createCsvDataList(form);
        } catch (Exception e) {
            assertThat(e.getMessage(), is("ヘッダー部の項目が重複しています。"));
        }
    }

    /**
     * 読み込み処理（ヘッダー部に空項目があるCSVファイルに読み込み処理を実行した場合）。
     */
    @Test
    public void testCsvReadHeaderHasEmpty() {
        CsvRegisterForm form = new CsvRegisterForm();
        form.setFilePath("src/test/java/jp/co/tis/util/testData/testOpenInCaseOfFileFormatException3.csv");

        try {
            target.createCsvDataList(form);
        } catch (Exception e) {
            assertThat(e.getMessage(), is("ヘッダー部に空項目が含まれています。"));
        }
    }

    /**
     * 読み込み処理（ヘッダー部の項目名数とデータ部のデータ数が一致しないCSVファイルに読み込み処理を実行した場合）。
     */
    @Test
    public void testCsvReadDeferentNumberOfItemBetweenHeaderAndData() {
        CsvRegisterForm form = new CsvRegisterForm();
        form.setFilePath("src/test/java/jp/co/tis/util/testData/testReadLineInCaseOfFileFormatException.csv");

        try {
            target.createCsvDataList(form);
        } catch (Exception e) {
            assertThat(e.getMessage(), is("2行目 ：ヘッダー部と項目数が異なっています。"));
        }
    }

}
