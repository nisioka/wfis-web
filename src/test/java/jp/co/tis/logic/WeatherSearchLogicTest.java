package jp.co.tis.logic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tis.App;
import jp.co.tis.form.WeatherSearchForm;
import jp.co.tis.model.Person;
import jp.co.tis.model.Weather;
import jp.co.tis.model.WeatherDao;

/**
 * 天気検索Logicのテスト。
 *
 * @author Yoshiwara Masashi
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@TransactionConfiguration
@Transactional
public class WeatherSearchLogicTest extends AbstractTransactionalJUnit4SpringContextTests {

    /** テスト対象クラス */
    @Autowired
    private WeatherSearchLogic target;

    /** DB操作DAO */
    @Autowired
    private WeatherDao weatherDao;

    /** INSERT文の雛形 */
    private String insertSql = "INSERT INTO WEATHER (WEATHER_DATE, PLACE, WEATHER, MAX_TEMPERATURE, MIN_TEMPERATURE) "
            + "VALUES ('%s','%s','%s','%s','%s')";

    /**
     * 各テストメソッドの前に呼ばれるセットアップメソッド。<br/>
     * WEATHERテーブルの中身を空にする。<br/>
     * DB関連のテストを行う場合、各テストメソッドで事前データをDBに登録する必要がある。<br/>
     * テスト終了後にはロールバックが行われるため、テスト実施前後でDBの中身は変わらない。
     */
    @Before
    public void setUp() {
        super.deleteFromTables("WEATHER");
    }

    /**
     * ウォーミングアップのテスト。
     */
    @Test
    public void testWarmingUp() {
        Person person = target.createPersonInfo();

        assertThat(person.getMyName(), is("TIS 太郎"));
        assertThat(person.getAge(), is("25"));
        assertThat(person.getHobby(), is("読書・映画・ショッピング"));
        assertThat(person.getSkill(), is("空手5段"));
    }

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
     * 天気検索SQLテスト。(全ての項目が入力されなかった場合)
     */
    @Test
    public void testWeatherSearchSqlAllEmpty() {
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
     * 天気検索SQLテスト。(日付だけ入力された場合)
     */
    @Test
    public void testWeatherSearchSqlOnlyDate() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDate("2015/01/01");
        String resultSql = target.createSql(form);
        Map<String, String> resultCondition = target.createCondition(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE WEATHER_DATE = :weatherDate"));
        assertThat(resultCondition.get("weatherDate"), is("2015/01/01"));
    }

    /**
     * 天気検索SQLテスト。(場所だけ入力された場合)
     */
    @Test
    public void testWeatherSearchSqlOnlyPlace() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setPlace("東京");
        String resultSql = target.createSql(form);
        Map<String, String> resultCondition = target.createCondition(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE PLACE = :place"));
        assertThat(resultCondition.get("place"), is("東京"));
    }

    /**
     * 天気検索SQLテスト。(天気だけ入力された場合)
     */
    @Test
    public void testWeatherSearchSqlOnlyWeather() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeather("晴れ");
        String resultSql = target.createSql(form);
        Map<String, String> resultCondition = target.createCondition(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE WEATHER = :weather"));
        assertThat(resultCondition.get("weather"), is("晴れ"));
    }

    /**
     * 天気検索SQLテスト。(最高気温だけ入力された場合)
     */
    @Test
    public void testWeatherSearchSqlOnlyMaxTemperature() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMaxTemperature("30");
        String resultSql = target.createSql(form);
        Map<String, String> resultCondition = target.createCondition(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE MAX_TEMPERATURE = :maxTemperature"));
        assertThat(resultCondition.get("maxTemperature"), is("30"));
    }

    /**
     * 天気検索SQLテスト。(最低気温だけ入力された場合)
     */
    @Test
    public void testWeatherSearchSqlOnlyMinTemperature() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMinTemperature("0");
        String resultSql = target.createSql(form);
        Map<String, String> resultCondition = target.createCondition(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE MIN_TEMPERATURE = :minTemperature"));
        assertThat(resultCondition.get("minTemperature"), is("0"));
    }

    /**
     * 天気検索SQLテスト。(全ての項目が入力された場合)
     */
    @Test
    public void testWeatherSearchSqlAll() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDate("2015/01/01");
        form.setPlace("東京");
        form.setWeather("晴れ");
        form.setMaxTemperature("30");
        form.setMinTemperature("0");
        String resultSql = target.createSql(form);
        Map<String, String> resultCondition = target.createCondition(form);

        assertThat(resultSql, is(
                "SELECT * FROM WEATHER WHERE WEATHER_DATE = :weatherDate and PLACE = :place and WEATHER = :weather and MAX_TEMPERATURE = :maxTemperature and MIN_TEMPERATURE = :minTemperature"));
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
     * 天気検索発展異常系のバリデーションテスト。(日付1つ目が日付形式でない場合)
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
     * 天気検索異常系のバリデーションテスト。(最高気温1つ目に数値以外のものが入力された場合)
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
     * 天気検索異常系のバリデーションテスト。(最高気温1つ目に3桁を超える値が入力された場合)
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
     * 天気検索異常系のバリデーションテスト。(最低気温1つ目に数値以外のものが入力された場合)
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
     * 天気検索異常系のバリデーションテスト。(最低気温1つ目に3桁を超える値が入力された場合)
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
     * 天気検索発展正常系の入力項目間のバリデーションテスト。(全ての項目に正常な値が入力された場合)。
     */
    @Test
    public void testWeatherSearchHardValidationNormalBetweenItem() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDateFrom("2015/01/01");
        form.setWeatherDateTo("2015/02/01");
        form.setMaxTemperatureFrom("20");
        form.setMaxTemperatureTo("30");
        form.setMinTemperatureFrom("10");
        form.setMinTemperatureTo("20");
        List<String> errorList = target.validateBetweenItem(form);

        assertThat(errorList.size(), is(0));
    }

    /**
     * 天気検索発展正常系の入力項目間のバリデーションテスト。(日付、最高気温、最低気温の1つ目の項目に値が入力された場合)。
     */
    @Test
    public void testValidationNormalBetweenItemOnlyFirstItem() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDateFrom("2015/01/01");
        form.setMaxTemperatureFrom("20");
        form.setMinTemperatureFrom("10");
        List<String> errorList = target.validateBetweenItem(form);

        assertThat(errorList.size(), is(0));
    }

    /**
     * 天気検索発展正常系の入力項目間のバリデーションテスト。(日付、最高気温、最低気温の2つ目の項目に値が入力された場合)。
     */
    @Test
    public void testValidationNormalBetweenItemOnlySecondItem() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDateTo("2015/01/01");
        form.setMaxTemperatureTo("20");
        form.setMinTemperatureTo("10");
        List<String> errorList = target.validateBetweenItem(form);

        assertThat(errorList.size(), is(0));
    }

    /**
     * 天気検索発展異常系の入力項目間のバリデーションテスト。(日付の範囲指定が不正な場合)
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
     * 天気検索発展異常系の入力項目間のバリデーションテスト。(最高気温の範囲指定が不正な場合)
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
     * 天気検索発展異常系の入力項目間のバリデーションテスト。(最高気温の範囲指定が不正な場合)
     */
    @Test
    public void testWeatherSearchHardValidationAbnormalBetweenMinTemperature() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMinTemperatureFrom("20");
        form.setMinTemperatureTo("10");
        List<String> errorList = target.validateBetweenItem(form);

        assertThat(errorList.get(0), is("最低気温の範囲指定が不正です。"));
    }

    /**
     * 天気検索発展SQLテスト。(全ての項目が入力されなかった場合)
     */
    @Test
    public void testWeatherSearchHardSqlAllEmpty() {
        WeatherSearchForm form = new WeatherSearchForm();
        String resultSql = target.createSqlHard(form);
        Map<String, String> resultCondition = target.createConditionHard(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER"));
        assertThat(resultCondition.get("weatherDateFrom"), is(nullValue()));
        assertThat(resultCondition.get("weatherDateTo"), is(nullValue()));
        assertThat(resultCondition.get("place"), is(nullValue()));
        assertThat(resultCondition.get("weather"), is(nullValue()));
        assertThat(resultCondition.get("maxTemperatureFrom"), is(nullValue()));
        assertThat(resultCondition.get("maxTemperatureTo"), is(nullValue()));
        assertThat(resultCondition.get("minTemperatureFrom"), is(nullValue()));
        assertThat(resultCondition.get("minTemperatureTo"), is(nullValue()));
    }

    /**
     * 天気検索発展SQLテスト。(日付1つ目だけ入力された場合)
     */
    @Test
    public void testWeatherSearchHardSqlOnlyDateFrom() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDateFrom("2015/01/01");
        String resultSql = target.createSqlHard(form);
        Map<String, String> resultCondition = target.createConditionHard(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE WEATHER_DATE >= :weatherDateFrom"));
        assertThat(resultCondition.get("weatherDateFrom"), is("2015/01/01"));
    }

    /**
     * 天気検索発展SQLテスト。(日付2つ目だけ入力された場合)
     */
    @Test
    public void testWeatherSearchHardSqlOnlyDateTo() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDateTo("2015/01/01");
        String resultSql = target.createSqlHard(form);
        Map<String, String> resultCondition = target.createConditionHard(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE WEATHER_DATE <= :weatherDateTo"));
        assertThat(resultCondition.get("weatherDateTo"), is("2015/01/01"));
    }

    /**
     * 天気検索発展SQLテスト。(場所だけ入力された場合)
     */
    @Test
    public void testWeatherSearchHardSqlOnlyPlace() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setPlace("東京");
        String resultSql = target.createSqlHard(form);
        Map<String, String> resultCondition = target.createConditionHard(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE PLACE = :place"));
        assertThat(resultCondition.get("place"), is("東京"));
    }

    /**
     * 天気検索発展SQLテスト。(天気が1つだけ入力された場合)
     */
    @Test
    public void testWeatherSearchHardSqlOnlyOneWeather() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeather("晴れ");
        String resultSql = target.createSqlHard(form);
        Map<String, String> resultCondition = target.createConditionHard(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE (WEATHER = :weather)"));
        assertThat(resultCondition.get("weather"), is("晴れ"));
    }

    /**
     * 天気検索発展SQLテスト。(天気が2つ入力された場合)
     */
    @Test
    public void testWeatherSearchHardSqlOnlyTwoWeather() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeather("晴れ,曇り");
        String resultSql = target.createSqlHard(form);
        Map<String, String> resultCondition = target.createConditionHard(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE (WEATHER = :weather OR WEATHER = :weather2)"));
        assertThat(resultCondition.get("weather"), is("晴れ"));
        assertThat(resultCondition.get("weather2"), is("曇り"));
    }

    /**
     * 天気検索発展SQLテスト。(天気が3つ入力された場合)
     */
    @Test
    public void testWeatherSearchHardSqlOnlyThreeWeather() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeather("晴れ,曇り,雨");
        String resultSql = target.createSqlHard(form);
        Map<String, String> resultCondition = target.createConditionHard(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE (WEATHER = :weather OR WEATHER = :weather2 OR WEATHER = :weather3)"));
        assertThat(resultCondition.get("weather"), is("晴れ"));
        assertThat(resultCondition.get("weather2"), is("曇り"));
        assertThat(resultCondition.get("weather3"), is("雨"));
    }

    /**
     * 天気検索発展SQLテスト。(天気が4つ入力された場合)
     */
    @Test
    public void testWeatherSearchHardSqlOnlyFourWeather() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeather("晴れ,曇り,雨,雪");
        String resultSql = target.createSqlHard(form);
        Map<String, String> resultCondition = target.createConditionHard(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER"));
        assertThat(resultCondition.get("weather"), is(nullValue()));
    }

    /**
     * 天気検索発展SQLテスト。(最高気温1つ目だけ入力された場合)
     */
    @Test
    public void testWeatherSearchHardSqlOnlyMaxTemperatureFrom() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMaxTemperatureFrom("20");
        String resultSql = target.createSqlHard(form);
        Map<String, String> resultCondition = target.createConditionHard(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE MAX_TEMPERATURE >= :maxTemperatureFrom"));
        assertThat(resultCondition.get("maxTemperatureFrom"), is("20"));
    }

    /**
     * 天気検索発展SQLテスト。(最高気温2つ目だけ入力された場合)
     */
    @Test
    public void testWeatherSearchHardSqlOnlyMaxTemperatureTo() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMaxTemperatureTo("20");
        String resultSql = target.createSqlHard(form);
        Map<String, String> resultCondition = target.createConditionHard(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE MAX_TEMPERATURE <= :maxTemperatureTo"));
        assertThat(resultCondition.get("maxTemperatureTo"), is("20"));
    }

    /**
     * 天気検索発展SQLテスト。(最低気温1つ目だけ入力された場合)
     */
    @Test
    public void testWeatherSearchHardSqlOnlyMinTemperatureFrom() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMinTemperatureFrom("20");
        String resultSql = target.createSqlHard(form);
        Map<String, String> resultCondition = target.createConditionHard(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE MIN_TEMPERATURE >= :minTemperatureFrom"));
        assertThat(resultCondition.get("minTemperatureFrom"), is("20"));
    }

    /**
     * 天気検索発展SQLテスト。(最低気温2つ目だけ入力された場合)
     */
    @Test
    public void testWeatherSearchHardSqlOnlyMinTemperatureTo() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMinTemperatureTo("20");
        String resultSql = target.createSqlHard(form);
        Map<String, String> resultCondition = target.createConditionHard(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER WHERE MIN_TEMPERATURE <= :minTemperatureTo"));
        assertThat(resultCondition.get("minTemperatureTo"), is("20"));
    }

    /**
     * 天気検索発展SQLテスト。(全ての項目が入力されていて、天気が1つ選択されている場合)
     */
    @Test
    public void testSqlAllOnlyOneWeatherHard() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDateFrom("2015/01/01");
        form.setWeatherDateTo("2015/02/01");
        form.setPlace("東京");
        form.setWeather("晴れ");
        form.setMaxTemperatureFrom("20");
        form.setMaxTemperatureTo("30");
        form.setMinTemperatureFrom("0");
        form.setMinTemperatureTo("10");
        String resultSql = target.createSqlHard(form);
        Map<String, String> resultCondition = target.createConditionHard(form);

        assertThat(resultSql, is(
                "SELECT * FROM WEATHER WHERE WEATHER_DATE >= :weatherDateFrom and WEATHER_DATE <= :weatherDateTo and PLACE = :place and (WEATHER = :weather) and MAX_TEMPERATURE >= :maxTemperatureFrom and MAX_TEMPERATURE <= :maxTemperatureTo and MIN_TEMPERATURE >= :minTemperatureFrom and MIN_TEMPERATURE <= :minTemperatureTo"));
        assertThat(resultCondition.get("weatherDateFrom"), is("2015/01/01"));
        assertThat(resultCondition.get("weatherDateTo"), is("2015/02/01"));
        assertThat(resultCondition.get("place"), is("東京"));
        assertThat(resultCondition.get("weather"), is("晴れ"));
        assertThat(resultCondition.get("maxTemperatureFrom"), is("20"));
        assertThat(resultCondition.get("maxTemperatureTo"), is("30"));
        assertThat(resultCondition.get("minTemperatureFrom"), is("0"));
        assertThat(resultCondition.get("minTemperatureTo"), is("10"));
    }

    /**
     * 天気検索の天気情報検索のテスト。
     */
    @Test
    public void testSearchWeather() {
        // 事前データ準備
        weatherDao.insert(String.format(insertSql, "2015/01/01", "群馬", "晴れ", "7", "-3"));
        weatherDao.insert(String.format(insertSql, "2015/01/02", "群馬", "曇り", "11", "6"));

        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDate("2015/01/01");
        form.setPlace("群馬");
        form.setWeather("晴れ");
        form.setMaxTemperature("7");
        form.setMinTemperature("-3");

        List<Weather> resultWeatherList = target.findBySql(form);
        assertThat(resultWeatherList.size(), is(1));
        assertThat(resultWeatherList.get(0).getWeatherDate(), is("2015/01/01"));
        assertThat(resultWeatherList.get(0).getPlace(), is("群馬"));
        assertThat(resultWeatherList.get(0).getWeather(), is("晴れ"));
        assertThat(resultWeatherList.get(0).getMaxTemperature(), is("7"));
        assertThat(resultWeatherList.get(0).getMinTemperature(), is("-3"));
    }

    /**
     * 天気検索発展の天気情報検索のテスト。
     */
    @Test
    public void testSearchWeatherHard() {
        // 事前データ準備
        weatherDao.insert(String.format(insertSql, "2015/01/01", "東京", "晴れ", "10", "5"));
        weatherDao.insert(String.format(insertSql, "2015/01/02", "東京", "曇り", "11", "6"));

        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDateFrom("2010/01/01");
        form.setWeatherDateTo("2016/01/01");
        form.setPlace("東京");
        form.setWeather("晴れ");
        form.setMaxTemperatureFrom("7");
        form.setMaxTemperatureTo("10");
        form.setMinTemperatureFrom("-3");
        form.setMinTemperatureTo("5");

        List<Weather> resultWeatherList = target.findBySqlHard(form);
        assertThat(resultWeatherList.size(), is(1));
        assertThat(resultWeatherList.get(0).getWeatherDate(), is("2015/01/01"));
        assertThat(resultWeatherList.get(0).getPlace(), is("東京"));
        assertThat(resultWeatherList.get(0).getWeather(), is("晴れ"));
        assertThat(resultWeatherList.get(0).getMaxTemperature(), is("10"));
        assertThat(resultWeatherList.get(0).getMinTemperature(), is("5"));
    }
}
