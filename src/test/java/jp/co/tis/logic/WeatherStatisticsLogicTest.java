package jp.co.tis.logic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.co.tis.App;
import jp.co.tis.form.WeatherStatisticsForm;
import jp.co.tis.model.Weather;

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
