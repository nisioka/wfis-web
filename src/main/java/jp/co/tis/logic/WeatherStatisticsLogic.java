package jp.co.tis.logic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.tis.exception.NoDataResultsException;
import jp.co.tis.form.WeatherStatisticsForm;
import jp.co.tis.model.Weather;
import jp.co.tis.model.WeatherDao;
import jp.co.tis.model.WeatherStatisticsDto;

/**
 * 天気統計Logicクラス。<br/>
 * コントローラーに直接メソッド切り出しを行うと行数が膨れるため、<br/>
 * 業務ロジック部分はロジッククラスに切り出す。<br/>
 * JUnitテストをしやすくするための目的もある。
 *
 * @author Saito Takuma
 * @since 1.0
 */
@Component
public class WeatherStatisticsLogic {

    /** DB操作DAO */
    @Autowired
    private WeatherDao weatherDao;

    /**
     * 入力項目をバリデーションする。
     *
     * @param form フォーム
     * @return エラーリスト
     */
    public List<String> validateForm(WeatherStatisticsForm form) {
        List<String> errorList = new ArrayList<String>();

        if (StringUtils.isEmpty(form.getWeatherDate()) || StringUtils.isEmpty(form.getPlace())) {
            errorList.add("日付と場所は、必ず両方入力してください。");
        }
        DateFormat format = new SimpleDateFormat("MM/dd");
        try {
            if (!StringUtils.isEmpty(form.getWeatherDate())) {
                format.setLenient(false);
                format.parse(form.getWeatherDate());
            }
        } catch (ParseException e) {
            errorList.add("日付はMM/dd形式で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getPlace()) && form.getPlace().length() > 10) {
            errorList.add("場所は10文字以内で入力してください。");
        }

        return errorList;
    }

    /**
     * 過去5年分の天気のリストを作成する。
     *
     * @param form フォーム
     * @return 過去5年分の天気のリスト
     */
    public List<Weather> createPastWeatherList(WeatherStatisticsForm form) {
        String selectSql = "SELECT * FROM WEATHER WHERE WEATHER_DATE LIKE :percentMonthAndDay AND PLACE = :place";
        Map<String, String> condition = new HashMap<String, String>();
        String percentMonthAndDay = "%" + form.getWeatherDate();
        condition.put("percentMonthAndDay", percentMonthAndDay);
        condition.put("place", form.getPlace());
        List<Weather> results = weatherDao.findBySql(selectSql, condition);
        if(results.isEmpty()){
            throw new NoDataResultsException("データが存在しませんでした。");
        }
        return results;
    }

    /**
     * 天気統計のDtoを作成する。
     *
     * @param form フォーム
     * @param pastWeatherList 過去の天気のリスト
     * @return 天気統計のDto
     */
    public WeatherStatisticsDto createWeatherStatisticsDto(WeatherStatisticsForm form, List<Weather> pastWeatherList) {
        WeatherStatisticsDto statisticsWeather = new WeatherStatisticsDto();
        double sunnyCount = 0;
        double cloudyCount = 0;
        double rainyCount = 0;
        double snowCount = 0;
        int maxTemperatureSum = 0;
        int minTemperatureSum = 0;
        for (Weather pastWeather : pastWeatherList) {
            if ("晴れ".equals(pastWeather.getWeather())) {
                sunnyCount++;
            } else if ("曇り".equals(pastWeather.getWeather())) {
                cloudyCount++;
            } else if ("雨".equals(pastWeather.getWeather())) {
                rainyCount++;
            } else if ("雪".equals(pastWeather.getWeather())) {
                snowCount++;
            }
            maxTemperatureSum += Integer.parseInt(pastWeather.getMaxTemperature());
            minTemperatureSum += Integer.parseInt(pastWeather.getMinTemperature());
        }
        if (sunnyCount != 0) {
            Double percent = (sunnyCount / pastWeatherList.size()) * 100;
            statisticsWeather.setSunnyPercent(percent.intValue());
        }
        if (cloudyCount != 0) {
            Double percent = (cloudyCount / pastWeatherList.size()) * 100;
            statisticsWeather.setCloudyPercent(percent.intValue());
        }
        if (rainyCount != 0) {
            Double percent = (rainyCount / pastWeatherList.size()) * 100;
            statisticsWeather.setRainyPercent(percent.intValue());
        }
        if (snowCount != 0) {
            Double percent = (snowCount / pastWeatherList.size()) * 100;
            statisticsWeather.setSnowPercent(percent.intValue());
        }
        statisticsWeather.setMaxTemperatureAve(maxTemperatureSum / pastWeatherList.size());
        statisticsWeather.setMinTemperatureAve(minTemperatureSum / pastWeatherList.size());
        statisticsWeather.setWeatherDate(form.getWeatherDate());
        statisticsWeather.setPlace(form.getPlace());

        return statisticsWeather;
    }
}
