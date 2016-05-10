package jp.co.tis.logic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

import jp.co.tis.App;
import jp.co.tis.form.WeatherSearchForm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 天気検索Logicのテスト。
 *
 * @author Saito Takuma
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class WeatherLogicTest {

    /** テスト対象クラス */
    @Autowired
    private WeatherSearchLogic target;

    /**
     * 天気検索正常系のバリデーションテスト。(全ての項目に正常な値が入力された場合)
     */
    @Test
    public void testWeatherSearchValidationNormal() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDate("2015/01/01");
        form.setPlace("東京");
        form.setWeather("晴れ");
        form.setMaxTemperature("20");
        form.setMinTemperature("10");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.size(), is(0));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(日付が日付形式でない場合)
     */
    @Test
    public void testWeatherSearchValidationAbnormalDate() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDate("20150101");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("日付は日付形式で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(場所が10文字を超えている場合)
     */
    @Test
    public void testWeatherSearchValidationOverflowPlace() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setPlace("12345678901");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("場所は10文字以内で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(天気が10文字を超えている場合)
     */
    @Test
    public void testWeatherSearchValidationOverflowWeather() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeather("12345678901");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("天気は10文字以内で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(最高気温に数値以外のものが入力された場合)
     */
    @Test
    public void testWeatherSearchValidationAbnormalMaxTemperature() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMaxTemperature("あ");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("最高気温は数値で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(最高気温に3桁を超える値が入力された場合)
     */
    @Test
    public void testWeatherSearchValidationOverflowMaxTemperature() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMaxTemperature("1000");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("最高気温は3桁以内で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(最低気温に数値以外のものが入力された場合)
     */
    @Test
    public void testWeatherSearchValidationAbnormlMinTemperature() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMinTemperature("あ");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("最低気温は数値で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(最低気温に3桁を超える値が入力された場合)
     */
    @Test
    public void testWeatherSearchValidationOverflowMinTemperature() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMinTemperature("1000");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("最低気温は3桁以内で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(全ての項目に異常な値が入力された場合)
     */
    @Test
    public void testWeatherSearchValidationAbnormlAll() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDate("20150101");
        form.setPlace("12345678901");
        form.setWeather("12345678901");
        form.setMaxTemperature("あ");
        form.setMinTemperature("あ");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("日付は日付形式で入力してください。"));
        assertThat(errorList.get(1), is("場所は10文字以内で入力してください。"));
        assertThat(errorList.get(2), is("天気は10文字以内で入力してください。"));
        assertThat(errorList.get(3), is("最高気温は数値で入力してください。"));
        assertThat(errorList.get(4), is("最低気温は数値で入力してください。"));
    }

    /**
     * SQLテスト。(全ての項目が入力されなかった場合)
     */
    @Test
    public void testSqlAllEmpty() {
        WeatherSearchForm form = new WeatherSearchForm();
        String resultSql = target.createSql(form);
        Map<String, String> resultCondition = target.createCondition(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER"));
        assertThat(resultCondition.get("weatherDate"), is(nullValue()));
        assertThat(resultCondition.get("place"), is(nullValue()));
        assertThat(resultCondition.get("weather"), is(nullValue()));
        assertThat(resultCondition.get("maxTemperature"), is(nullValue()));
        assertThat(resultCondition.get("minTemperature"), is(nullValue()));
    }

    /**
     * SQLテスト。(日付だけ入力された場合)
     */
    @Test
    public void testSqlOnlyDate() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDate("2015/01/01");
        String resultSql = target.createSql(form);
        Map<String, String> resultCondition = target.createCondition(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE WEATHER_DATE = :weatherDate"));
        assertThat(resultCondition.get("weatherDate"), is("2015/01/01"));
    }

    /**
     * SQLテスト。(場所だけ入力された場合)
     */
    @Test
    public void testSqlOnlyPlace() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setPlace("東京");
        String resultSql = target.createSql(form);
        Map<String, String> resultCondition = target.createCondition(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE PLACE = :place"));
        assertThat(resultCondition.get("place"), is("東京"));
    }

    /**
     * SQLテスト。(天気だけ入力された場合)
     */
    @Test
    public void testSqlOnlyWeather() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeather("晴れ");
        String resultSql = target.createSql(form);
        Map<String, String> resultCondition = target.createCondition(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE WEATHER = :weather"));
        assertThat(resultCondition.get("weather"), is("晴れ"));
    }

    /**
     * SQLテスト。(最高気温だけ入力された場合)
     */
    @Test
    public void testSqlOnlyMaxTemperature() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMaxTemperature("30");
        String resultSql = target.createSql(form);
        Map<String, String> resultCondition = target.createCondition(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE MAX_TEMPERATURE = :maxTemperature"));
        assertThat(resultCondition.get("maxTemperature"), is("30"));
    }

    /**
     * SQLテスト。(最低気温だけ入力された場合)
     */
    @Test
    public void testSqlOnlyMinTemperature() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMinTemperature("0");
        String resultSql = target.createSql(form);
        Map<String, String> resultCondition = target.createCondition(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE MIN_TEMPERATURE = :minTemperature"));
        assertThat(resultCondition.get("minTemperature"), is("0"));
    }

    /**
     * SQLテスト。(全ての項目が入力された場合)
     */
    @Test
    public void testSqlAll() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDate("2015/01/01");
        form.setPlace("東京");
        form.setWeather("晴れ");
        form.setMaxTemperature("30");
        form.setMinTemperature("0");
        String resultSql = target.createSql(form);
        Map<String, String> resultCondition = target.createCondition(form);

        assertThat(
                resultSql,
                is("SELECT * FROM WEATHER WHERE WEATHER_DATE = :weatherDate and PLACE = :place and WEATHER = :weather and MAX_TEMPERATURE = :maxTemperature and MIN_TEMPERATURE = :minTemperature"));
        assertThat(resultCondition.get("weatherDate"), is("2015/01/01"));
        assertThat(resultCondition.get("place"), is("東京"));
        assertThat(resultCondition.get("weather"), is("晴れ"));
        assertThat(resultCondition.get("maxTemperature"), is("30"));
        assertThat(resultCondition.get("minTemperature"), is("0"));
    }

    /**
     * 天気検索発展正常系のバリデーションテスト。(全ての項目に正常な値が入力された場合)
     */
    @Test
    public void testWeatherSearchHardValidationNormal() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDateFrom("2015/01/01");
        form.setWeatherDateTo("2015/02/01");
        form.setPlace("東京");
        form.setWeather("晴れ");
        form.setMaxTemperatureFrom("10");
        form.setMaxTemperatureTo("20");
        form.setMinTemperatureFrom("0");
        form.setMinTemperatureTo("10");
        List<String> errorList = target.validateFormHard(form);

        assertThat(errorList.size(), is(0));
    }

    /**
     * 天気検索発展異常系のバリデーションテスト。(日付開始が日付形式でない場合)
     */
    @Test
    public void testWeatherSearchHardValidationAbnormalDateFrom() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDateFrom("20150101");
        List<String> errorList = target.validateFormHard(form);

        assertThat(errorList.get(0), is("日付は日付形式で入力してください。"));
    }

    /**
     * 天気検索発展異常系のバリデーションテスト。(日付終了が日付形式でない場合)
     */
    @Test
    public void tesWeatherSearchHardtValidationAbnormalDateTo() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDateFrom("20150101");
        List<String> errorList = target.validateFormHard(form);

        assertThat(errorList.get(0), is("日付は日付形式で入力してください。"));
    }

    /**
     * 天気検索発展異常系のバリデーションテスト。(場所が10文字を超えている場合)
     */
    @Test
    public void testWeatherSearchHardValidationOverflowPlace() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setPlace("12345678901");
        List<String> errorList = target.validateFormHard(form);

        assertThat(errorList.get(0), is("場所は10文字以内で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(天気が10文字を超えている場合)
     */
    @Test
    public void testWeatherSearchHardValidationOverflowWeather() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeather("12345678901");
        List<String> errorList = target.validateFormHard(form);

        assertThat(errorList.get(0), is("天気は10文字以内で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(最高気温開始に数値以外のものが入力された場合)
     */
    @Test
    public void testWeatherSearchHardValidationAbnormalMaxTemperatureFrom() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMaxTemperatureFrom("あ");
        List<String> errorList = target.validateFormHard(form);

        assertThat(errorList.get(0), is("最高気温は数値で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(最高気温終了に数値以外のものが入力された場合)
     */
    @Test
    public void testWeatherSearchHardValidationAbnormalMaxTemperatureTo() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMaxTemperatureTo("あ");
        List<String> errorList = target.validateFormHard(form);

        assertThat(errorList.get(0), is("最高気温は数値で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(最高気温開始に3桁を超える値が入力された場合)
     */
    @Test
    public void testWeatherSearchHardValidationOverflowMaxTemperatureFrom() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMaxTemperatureFrom("1000");
        List<String> errorList = target.validateFormHard(form);

        assertThat(errorList.get(0), is("最高気温は3桁以内で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(最高気温終了に3桁を超える値が入力された場合)
     */
    @Test
    public void testWeatherSearchHardValidationOverflowMaxTemperatureTo() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMaxTemperatureTo("1000");
        List<String> errorList = target.validateFormHard(form);

        assertThat(errorList.get(0), is("最高気温は3桁以内で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(最低気温開始に数値以外のものが入力された場合)
     */
    @Test
    public void testWeatherSearchHardValidationAbnormlMinTemperatureFrom() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMinTemperatureFrom("あ");
        List<String> errorList = target.validateFormHard(form);

        assertThat(errorList.get(0), is("最低気温は数値で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(最低気温終了に数値以外のものが入力された場合)
     */
    @Test
    public void testWeatherSearchHardValidationAbnormlMinTemperatureTo() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMinTemperatureTo("あ");
        List<String> errorList = target.validateFormHard(form);

        assertThat(errorList.get(0), is("最低気温は数値で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(最低気温開始に3桁を超える値が入力された場合)
     */
    @Test
    public void testWeatherSearchHardValidationOverflowMinTemperatureFrom() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMinTemperatureFrom("1000");
        List<String> errorList = target.validateFormHard(form);

        assertThat(errorList.get(0), is("最低気温は3桁以内で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(最低気温終了に3桁を超える値が入力された場合)
     */
    @Test
    public void testWeatherSearchHardValidationOverflowMinTemperatureTo() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMinTemperatureTo("1000");
        List<String> errorList = target.validateFormHard(form);

        assertThat(errorList.get(0), is("最低気温は3桁以内で入力してください。"));
    }

    /**
     * 天気検索異常系のバリデーションテスト。(全ての項目に異常な値が入力された場合)
     */
    @Test
    public void testWeatherSearchHardValidationAbnormlAll() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDateFrom("20150101");
        form.setWeatherDateTo("20150101");
        form.setPlace("12345678901");
        form.setWeather("12345678901");
        form.setMaxTemperatureFrom("あ");
        form.setMaxTemperatureTo("1000");
        form.setMinTemperatureFrom("あ");
        form.setMinTemperatureTo("1000");
        List<String> errorList = target.validateFormHard(form);

        assertThat(errorList.get(0), is("日付は日付形式で入力してください。"));
        assertThat(errorList.get(1), is("場所は10文字以内で入力してください。"));
        assertThat(errorList.get(2), is("天気は10文字以内で入力してください。"));
        assertThat(errorList.get(3), is("最高気温は数値で入力してください。"));
        assertThat(errorList.get(4), is("最低気温は数値で入力してください。"));
        assertThat(errorList.get(5), is("最高気温は3桁以内で入力してください。"));
        assertThat(errorList.get(6), is("最低気温は3桁以内で入力してください。"));
    }

    /**
     * 正常系の入力項目間のバリデーションテスト。()
     */
    @Test
    public void testWeatherSearchHardValidationNormalBetweenItem() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDateFrom("2015/01/01");
        form.setWeatherDateTo("2015/02/01");
        form.setPlace("東京");
        form.setWeather("晴れ");
        form.setMaxTemperatureFrom("20");
        form.setMaxTemperatureTo("30");
        form.setMinTemperatureFrom("10");
        form.setMinTemperatureTo("20");
        List<String> errorList = target.validateBetweenItem(form);

        assertThat(errorList.size(), is(0));
    }

    /**
     * 異常系の入力項目間のバリデーションテスト。(日付の範囲指定が不正な場合)
     */
    @Test
    public void testWeatherSearchHardValidationAbnormalBetweenDate() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDateFrom("2015/02/01");
        form.setWeatherDateTo("2015/01/01");
        List<String> errorList = target.validateBetweenItem(form);

        assertThat(errorList.get(0), is("日付の範囲指定が不正です。"));
    }

    /**
     * 異常系の入力項目間のバリデーションテスト。(最高気温の範囲指定が不正な場合)
     */
    @Test
    public void testWeatherSearchHardValidationAbnormalBetweenMaxTemperature() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMaxTemperatureFrom("20");
        form.setMaxTemperatureTo("10");
        List<String> errorList = target.validateBetweenItem(form);

        assertThat(errorList.get(0), is("最高気温の範囲指定が不正です。"));
    }

    /**
     * 異常系の入力項目間のバリデーションテスト。(最高気温の範囲指定が不正な場合)
     */
    @Test
    public void testWeatherSearchHardValidationAbnormalBetweenMinTemperature() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMinTemperatureFrom("20");
        form.setMinTemperatureTo("10");
        List<String> errorList = target.validateBetweenItem(form);

        assertThat(errorList.get(0), is("最低気温の範囲指定が不正です。"));
    }
}
