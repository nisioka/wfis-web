package jp.co.tis.logic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import jp.co.tis.App;
import jp.co.tis.form.WeatherStatisticsForm;
import jp.co.tis.model.Weather;
import jp.co.tis.model.WeatherStatisticsDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 天気統計Logicのテスト。
 *
 * @author Saito Takuma
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class WeatherStatisticsLogicTest {

    /** テスト対象クラス */
    @Autowired
    private WeatherStatisticsLogic target;

    /**
     * 正常系のバリデーションテスト。（全ての項目に正常な値が入力された場合）
     */
    @Test
    public void testValidationNormal() {
        WeatherStatisticsForm form = new WeatherStatisticsForm();
        form.setWeatherDate("2015/01/01");
        form.setPlace("東京");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.size(), is(0));
    }

    /**
     * 異常系のバリデーションテスト。（日付が日付形式でない場合）
     */
    @Test
    public void testValidationAbnormalDate() {
        WeatherStatisticsForm form = new WeatherStatisticsForm();
        form.setWeatherDate("20150101");
        form.setPlace("東京");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("日付は日付形式で入力してください。"));
    }

    /**
     * 異常系のバリデーションテスト。（場所が10文字を超えている場合）
     */
    @Test
    public void testValidationAbnormalPlace() {
        WeatherStatisticsForm form = new WeatherStatisticsForm();
        form.setWeatherDate("2015/01/01");
        form.setPlace("12345678901");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("場所は10文字以内で入力してください。"));
    }

    /**
     * 異常系のバリデーションテスト。（日付が入力されなかった場合）
     */
    @Test
    public void testValidationEmptyDate() {
        WeatherStatisticsForm form = new WeatherStatisticsForm();
        form.setPlace("東京");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("日付と場所は、必ず両方入力してください。"));
    }

    /**
     * 異常系のバリデーションテスト。（場所が入力されなかった場合）
     */
    @Test
    public void testValidationEmptyPlace() {
        WeatherStatisticsForm form = new WeatherStatisticsForm();
        form.setWeatherDate("2015/01/01");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("日付と場所は、必ず両方入力してください。"));
    }

    /**
     * 異常系のバリデーションテスト。(全ての項目に異常な値が入力された場合)
     */
    @Test
    public void testValidationAbnormlAll() {
        WeatherStatisticsForm form = new WeatherStatisticsForm();
        form.setWeatherDate("20150101");
        form.setPlace("12345678901");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("日付は日付形式で入力してください。"));
        assertThat(errorList.get(1), is("場所は10文字以内で入力してください。"));
    }

    /**
     * 検索テスト。（DBにテストでつなげるか確かめるためのもの）
     */
    @Test
    public void testSearch() {
        WeatherStatisticsForm form = new WeatherStatisticsForm();
        form.setWeatherDate("2015/01/01");
        form.setPlace("東京");
        List<Weather> resultWeatherList = target.createPastWeatherList(form);

        List<Weather> expectedList = new ArrayList<Weather>();
        Weather weather = new Weather();
        weather.setWeatherDate("2015/01/01");
        weather.setPlace("東京");
        weather.setWeather("曇り");
        weather.setMaxTemperature("8");
        weather.setMinTemperature("2");
        expectedList.add(weather);

        assertThat(resultWeatherList.get(0).getWeatherDate(), is(expectedList.get(0).getWeatherDate()));
        assertThat(resultWeatherList.get(0).getPlace(), is(expectedList.get(0).getPlace()));
        assertThat(resultWeatherList.get(0).getWeather(), is(expectedList.get(0).getWeather()));
        assertThat(resultWeatherList.get(0).getMaxTemperature(), is(expectedList.get(0).getMaxTemperature()));
        assertThat(resultWeatherList.get(0).getMinTemperature(), is(expectedList.get(0).getMinTemperature()));
    }

    /**
     *
     */
    @Test
    public void test() {
        WeatherStatisticsForm form = new WeatherStatisticsForm();
        form.setWeatherDate("01/01");
        form.setPlace("東京");

        Weather firstWeather = new Weather();
        firstWeather.setWeatherDate("2015/01/01");
        firstWeather.setPlace("東京");
        firstWeather.setWeather("晴れ");
        firstWeather.setMaxTemperature("10");
        firstWeather.setMinTemperature("0");

        Weather secondWeather = new Weather();
        secondWeather.setWeatherDate("2015/01/01");
        secondWeather.setPlace("東京");
        secondWeather.setWeather("曇り");
        secondWeather.setMaxTemperature("10");
        secondWeather.setMinTemperature("0");

        Weather thirdWeather = new Weather();
        thirdWeather.setWeatherDate("2015/01/01");
        thirdWeather.setPlace("東京");
        thirdWeather.setWeather("雨");
        thirdWeather.setMaxTemperature("10");
        thirdWeather.setMinTemperature("0");

        Weather fourthWeather = new Weather();
        fourthWeather.setWeatherDate("2015/01/01");
        fourthWeather.setPlace("東京");
        fourthWeather.setWeather("雪");
        fourthWeather.setMaxTemperature("10");
        fourthWeather.setMinTemperature("0");

        Weather fifthWeather = new Weather();
        fifthWeather.setWeatherDate("2015/01/01");
        fifthWeather.setPlace("東京");
        fifthWeather.setWeather("晴れ");
        fifthWeather.setMaxTemperature("10");
        fifthWeather.setMinTemperature("0");

        List<Weather> pastWeatherList = new ArrayList<Weather>();
        pastWeatherList.set(0, firstWeather);
        pastWeatherList.set(1, secondWeather);
        pastWeatherList.set(2, thirdWeather);
        pastWeatherList.set(3, fourthWeather);
        pastWeatherList.set(4, fifthWeather);

        WeatherStatisticsDto resultDto = target.createWeatherStatisticsDto(form, pastWeatherList);

        assertThat(resultDto.getWeatherDate(), is("01/01"));
        assertThat(resultDto.getPlace(), is("40"));
        assertThat(resultDto.getSunnyPercent(), is("20"));
        assertThat(resultDto.getCloudyPercent(), is("20"));
        assertThat(resultDto.getRainyPercent(), is("20"));
        assertThat(resultDto.getSnowPercent(), is("20"));
        assertThat(resultDto.getMaxTemperatureAve(), is("10"));
        assertThat(resultDto.getMinTemperatureAve(), is("0"));

    }

}
