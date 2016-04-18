package jp.co.tis.logic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.co.tis.App;
import jp.co.tis.form.WeatherSearchForm;
import jp.co.tis.model.Weather;

/**
 * 天気予報コントローラのテスト。
 *
 * @author Takuma Saito
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class WeatherLogicTest {

    /** テスト対象クラス */
    @Autowired
    private WeatherLogic target;

    /**
     * 正常系のバリデーションテスト。(全ての項目に正常な値が入力された場合)
     */
    @Test
    public void testValidationNormal() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDate("2015/01/01");
        form.setPlace("東京");
        form.setWeather("晴れ");
        form.setMaxTemperature("20");
        form.setMinTemperature("10");
        List<String> errorList = target.validateFormEasy(form);

        assertThat(errorList.size(), is(0));
    }

    /**
     * 異常系のバリデーションテスト。(日付が日付形式でない場合)
     */
    @Test
    public void testValidationAbnormalDate() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDate("20150101");
        List<String> errorList = target.validateFormEasy(form);

        assertThat(errorList.get(0), is("日付は日付形式で入力してください。"));
    }

    /**
     * 異常系のバリデーションテスト。(場所が255文字を超えている場合)
     */
    @Test
    public void testValidationAbnormalPlace() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setPlace(
                "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ");
        List<String> errorList = target.validateFormEasy(form);

        assertThat(errorList.get(0), is("場所は255文字以内で入力してください。"));
    }

    /**
     * 異常系のバリデーションテスト。(天気が255文字を超えている場合)
     */
    @Test
    public void testValidationAbnormalWeather() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeather(
                "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ");
        List<String> errorList = target.validateFormEasy(form);

        assertThat(errorList.get(0), is("天気は255文字以内で入力してください。"));
    }

    /**
     * 異常系のバリデーションテスト。(最高気温に数値以外のものが入力された場合)
     */
    @Test
    public void testValidationaAbnormlMaxTemperature() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMaxTemperature("あ");
        List<String> errorList = target.validateFormEasy(form);

        assertThat(errorList.get(0), is("最高気温は数値で入力してください。"));
    }

    /**
     * 異常系のバリデーションテスト。(最低気温に数値以外のものが入力された場合)
     */
    @Test
    public void testValidationAbnormlMinTemperature() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setMinTemperature("あ");
        List<String> errorList = target.validateFormEasy(form);

        assertThat(errorList.get(0), is("最低気温は数値で入力してください。"));
    }

    /**
     * 異常系のバリデーションテスト。(全ての項目に異常な値が入力された場合)
     */
    @Test
    public void testValidationAbnormlAll() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDate("20150101");
        form.setPlace(
                "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ");
        form.setWeather(
                "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ");
        form.setMaxTemperature("あ");
        form.setMinTemperature("あ");
        List<String> errorList = target.validateFormEasy(form);

        assertThat(errorList.get(0), is("日付は日付形式で入力してください。"));
        assertThat(errorList.get(1), is("場所は255文字以内で入力してください。"));
        assertThat(errorList.get(2), is("天気は255文字以内で入力してください。"));
        assertThat(errorList.get(3), is("最高気温は数値で入力してください。"));
        assertThat(errorList.get(4), is("最低気温は数値で入力してください。"));
    }

    /**
     * SQLテスト。(全ての項目が入力されなかった場合)
     */
    @Test
    public void testSqlAllEmpty() {
        WeatherSearchForm form = new WeatherSearchForm();
        String resultSql = target.createSqlEasy(form);
        Map<String, String> resultCondition = target.createConditionEasy(form);

        assertThat(resultSql, is("SELECT * FROM WEATHER"));
        assertThat(resultCondition.size(), is(0));
    }

    /**
     * SQLテスト。(日付だけ入力された場合)
     */
    @Test
    public void testSqlOnlyDate() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDate("2015/01/01");
        String resultSql = target.createSqlEasy(form);
        Map<String, String> resultCondition = target.createConditionEasy(form);

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
        String resultSql = target.createSqlEasy(form);
        Map<String, String> resultCondition = target.createConditionEasy(form);

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
        String resultSql = target.createSqlEasy(form);
        Map<String, String> resultCondition = target.createConditionEasy(form);

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
        String resultSql = target.createSqlEasy(form);
        Map<String, String> resultCondition = target.createConditionEasy(form);

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
        String resultSql = target.createSqlEasy(form);
        Map<String, String> resultCondition = target.createConditionEasy(form);

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
        String resultSql = target.createSqlEasy(form);
        Map<String, String> resultCondition = target.createConditionEasy(form);

        assertThat(resultSql, is(
                "SELECT * FROM WEATHER WHERE WEATHER_DATE = :weatherDate and PLACE = :place and WEATHER = :weather and MAX_TEMPERATURE = :maxTemperature and MIN_TEMPERATURE = :minTemperature"));
        assertThat(resultCondition.get("weatherDate"), is("2015/01/01"));
        assertThat(resultCondition.get("place"), is("東京"));
        assertThat(resultCondition.get("weather"), is("晴れ"));
        assertThat(resultCondition.get("maxTemperature"), is("30"));
        assertThat(resultCondition.get("minTemperature"), is("0"));
    }

    /**
     * 検索テスト。
     */
    @Test
    public void testSearch() {
        WeatherSearchForm form = new WeatherSearchForm();
        form.setWeatherDate("2015/01/01");
        form.setPlace("東京");
        List<Weather> resultWeatherList = target.createPastWeatherList(form);

        List<Weather> expectedList = new ArrayList<Weather>();
        Weather weather = new Weather();
        weather.setWeatherDate("2010/01/01");
        weather.setPlace("東京");
        weather.setWeather("晴れ");
        weather.setMaxTemperature("10");
        weather.setMinTemperature("0");
        expectedList.add(weather);

        assertThat(resultWeatherList.get(0).getWeatherDate(), is(expectedList.get(0).getWeatherDate()));
        assertThat(resultWeatherList.get(0).getPlace(), is(expectedList.get(0).getPlace()));
        assertThat(resultWeatherList.get(0).getWeather(), is(expectedList.get(0).getWeather()));
        assertThat(resultWeatherList.get(0).getMaxTemperature(), is(expectedList.get(0).getMaxTemperature()));
        assertThat(resultWeatherList.get(0).getMinTemperature(), is(expectedList.get(0).getMinTemperature()));
    }
}
