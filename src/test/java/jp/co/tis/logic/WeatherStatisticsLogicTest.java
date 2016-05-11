package jp.co.tis.logic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import jp.co.tis.App;
import jp.co.tis.form.WeatherStatisticsForm;
import jp.co.tis.model.Weather;

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
    public void testWeatherStatisticsValidationNormal() {
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
    public void testWeatherStatisticsValidationAbnormalDate() {
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
    public void testWeatherStatisticsValidationAbnormalPlace() {
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
    public void testWeatherStatisticsValidationEmptyDate() {
        WeatherStatisticsForm form = new WeatherStatisticsForm();
        form.setPlace("東京");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("日付と場所は、必ず両方入力してください。"));
    }

    /**
     * 異常系のバリデーションテスト。（場所が入力されなかった場合）
     */
    @Test
    public void testWeatherStatisticsValidationEmptyPlace() {
        WeatherStatisticsForm form = new WeatherStatisticsForm();
        form.setWeatherDate("2015/01/01");
        List<String> errorList = target.validateForm(form);

        assertThat(errorList.get(0), is("日付と場所は、必ず両方入力してください。"));
    }

    /**
     * 異常系のバリデーションテスト。(全ての項目に異常な値が入力された場合)
     */
    @Test
    public void testWeatherStatisticsValidationAbnormlAll() {
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

}
