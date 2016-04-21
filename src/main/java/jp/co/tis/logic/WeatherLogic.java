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

import jp.co.tis.form.WeatherSearchForm;
import jp.co.tis.model.Weather;
import jp.co.tis.model.WeatherDao;
import jp.co.tis.model.WeatherDto;

/**
 * 天気予報Logicクラス。
 *
 * @author Saito Takuma
 * @since 1.0
 */
@Component
public class WeatherLogic {

    /** DB操作DAO */
    @Autowired
    private WeatherDao weatherDao;

    /**
     * 入力項目をバリデーションする（天気検索初級・標準用）。
     *
     * @param form フォーム
     * @return エラーリスト
     */
    public List<String> validateFormEasy(WeatherSearchForm form) {
        List<String> errorList = new ArrayList<String>();
        // 日付精査
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            if (!StringUtils.isEmpty(form.getWeatherDate())) {
                format.parse(form.getWeatherDate());
            }
        } catch (ParseException e) {
            errorList.add("日付は日付形式で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getPlace()) && form.getPlace().length() > 10) {
            errorList.add("場所は10文字以内で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getWeather()) && form.getWeather().length() > 10) {
            errorList.add("天気は10文字以内で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getMaxTemperature()) && !StringUtils.isNumeric(form.getMaxTemperature())) {
            errorList.add("最高気温は数値で入力してください。");
        } else if (!StringUtils.isEmpty(form.getMaxTemperature()) && form.getMaxTemperature().length() > 3) {
            errorList.add("最高気温は3桁以内で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getMinTemperature()) && !StringUtils.isNumeric(form.getMinTemperature())) {
            errorList.add("最低気温は数値で入力してください。");
        } else if (!StringUtils.isEmpty(form.getMinTemperature()) && form.getMinTemperature().length() > 3) {
            errorList.add("最低気温は3桁以内で入力してください。");
        }

        return errorList;
    }

    /**
     * 入力項目をバリデーションする（天気検索発展）。
     *
     * @param form フォーム
     * @return エラーリスト
     */
    public List<String> validateForm(WeatherSearchForm form) {
        List<String> errorList = new ArrayList<String>();

        // 日付精査
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            if (!StringUtils.isEmpty(form.getWeatherDateFrom())) {
                format.parse(form.getWeatherDateFrom());
            }
            if (!StringUtils.isEmpty(form.getWeatherDateTo())) {
                format.parse(form.getWeatherDateTo());
            }
        } catch (ParseException e) {
            errorList.add("日付は日付形式で入力してください。");
        }

        if (!StringUtils.isEmpty(form.getPlace()) && form.getPlace().length() > 10) {
            errorList.add("場所は10文字以内で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getWeather()) && form.getWeather().length() > 10) {
            errorList.add("天気は10文字以内で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getMaxTemperatureFrom()) && !StringUtils.isNumeric(form.getMaxTemperatureFrom())) {
            errorList.add("最高気温は数値で入力してください。");
        } else if (!StringUtils.isEmpty(form.getMaxTemperatureTo()) && !StringUtils.isNumeric(form.getMaxTemperatureTo())) {
            errorList.add("最高気温は数値で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getMinTemperatureFrom()) && !StringUtils.isNumeric(form.getMinTemperatureFrom())) {
            errorList.add("最低気温は数値で入力してください。");
        } else if (!StringUtils.isEmpty(form.getMinTemperatureTo()) && !StringUtils.isNumeric(form.getMinTemperatureTo())) {
            errorList.add("最低気温は数値で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getMaxTemperatureFrom()) && form.getMaxTemperatureFrom().length() > 3) {
            errorList.add("最高気温は3桁以内で入力してください。");
        } else if (!StringUtils.isEmpty(form.getMaxTemperatureTo()) && form.getMaxTemperatureTo().length() > 3) {
            errorList.add("最高気温は3桁以内で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getMinTemperatureFrom()) && form.getMinTemperatureFrom().length() > 3) {
            errorList.add("最低気温は3桁以内で入力してください。");
        } else if (!StringUtils.isEmpty(form.getMinTemperatureTo()) && form.getMinTemperatureTo().length() > 3) {
            errorList.add("最低気温は3桁以内で入力してください。");
        }

        return errorList;
    }

    /**
     * 入力項目間のバリデーションする（天気検索発展）。
     *
     * @param form フォーム
     * @return エラーリスト
     */
    public List<String> validateBetweenItem(WeatherSearchForm form) {
        List<String> errorList = new ArrayList<String>();

        if (form.getWeatherDateFrom().compareTo(form.getWeatherDateTo()) > 0) {
            errorList.add("日付の範囲指定が不正です。");
        }

        if (form.getMaxTemperatureFrom().compareTo(form.getMaxTemperatureTo()) > 0) {
            errorList.add("最高気温の範囲指定が不正です。");
        }

        if (form.getMinTemperatureFrom().compareTo(form.getMinTemperatureTo()) > 0) {
            errorList.add("最低気温の範囲指定が不正です。");
        }

        return errorList;
    }

    /**
     * 入力項目をバリデーションする（天気予測）。
     *
     * @param form フォーム
     * @return エラーリスト
     */
    public List<String> validateFormForExpect(WeatherSearchForm form) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isEmpty(form.getWeatherDate()) || StringUtils.isEmpty(form.getPlace())) {
            errorList.add("日付と場所は、必ず両方入力してください。");
        }
        // 日付精査
        DateFormat format = new SimpleDateFormat("MM/dd");
        try {
            if (!StringUtils.isEmpty(form.getWeatherDate())) {
                format.parse(form.getWeatherDate());
            }
        } catch (ParseException e) {
            errorList.add("日付は日付形式で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getPlace()) && form.getPlace().length() > 10) {
            errorList.add("場所は10文字以内で入力してください。");
        }

        return errorList;
    }

    /**
     * 入力項目をバリデーションする（CSVデータ登録）。
     *
     * @param form フォーム
     * @return エラーリスト
     */
    public List<String> validateFormForCsvRead(WeatherSearchForm form) {
        List<String> errorList = new ArrayList<String>();

        String filePath = form.getFilePath();
        if (StringUtils.isEmpty(filePath)) {
            errorList.add("ファイルパスは必ず入力してください。");
        } else if (!StringUtils.endsWith(filePath, ".csv")) {
            errorList.add("ファイルの拡張子はcsv形式にしてください。");
        }

        return errorList;
    }

    /**
     * 検索に使用するSQLを作成する（天気検索初級・標準用）。
     *
     * @param form フォーム
     * @return SQL
     */
    public String createSqlEasy(WeatherSearchForm form) {
        boolean isFirstCondition = true;
        StringBuilder selectSql = new StringBuilder("SELECT * FROM WEATHER");
        if (!StringUtils.isEmpty(form.getWeatherDate())) {
            selectSql.append(" WHERE WEATHER_DATE = :weatherDate");
            isFirstCondition = false;
        }
        if (!StringUtils.isEmpty(form.getPlace())) {
            if (isFirstCondition) {
                selectSql.append(" WHERE PLACE = :place");
                isFirstCondition = false;
            } else {
                selectSql.append(" and PLACE = :place");
            }
        }
        if (!StringUtils.isEmpty(form.getWeather())) {
            if (isFirstCondition) {
                selectSql.append(" WHERE WEATHER = :weather");
                isFirstCondition = false;
            } else {
                selectSql.append(" and WEATHER = :weather");
            }
        }
        if (!StringUtils.isEmpty(form.getMaxTemperature())) {
            if (isFirstCondition) {
                selectSql.append(" WHERE MAX_TEMPERATURE = :maxTemperature");
                isFirstCondition = false;
            } else {
                selectSql.append(" and MAX_TEMPERATURE = :maxTemperature");
            }
        }
        if (!StringUtils.isEmpty(form.getMinTemperature())) {
            if (isFirstCondition) {
                selectSql.append(" WHERE MIN_TEMPERATURE = :minTemperature");
                isFirstCondition = false;
            } else {
                selectSql.append(" and MIN_TEMPERATURE = :minTemperature");
            }
        }
        return selectSql.toString();
    }

    /**
     * 検索に使用する条件を作成する（天気検索初級・標準用）。
     *
     * @param form フォーム
     * @return SQL
     */
    public Map<String, String> createConditionEasy(WeatherSearchForm form) {
        Map<String, String> condition = new HashMap<String, String>();
        if (!StringUtils.isEmpty(form.getWeatherDate())) {
            condition.put("weatherDate", form.getWeatherDate());
        }
        if (!StringUtils.isEmpty(form.getPlace())) {
            condition.put("place", form.getPlace());
        }
        if (!StringUtils.isEmpty(form.getWeather())) {
            condition.put("weather", form.getWeather());
        }
        if (!StringUtils.isEmpty(form.getMaxTemperature())) {
            condition.put("maxTemperature", form.getMaxTemperature());
        }
        if (!StringUtils.isEmpty(form.getMinTemperature())) {
            condition.put("minTemperature", form.getMinTemperature());
        }

        return condition;
    }

    /**
     * 検索に使用するSQLを作成する（天気検索発展）。
     *
     * @param form フォーム
     * @return SQL
     */
    public String createSql(WeatherSearchForm form) {
        boolean isFirstCondition = true;
        StringBuilder selectSql = new StringBuilder("SELECT * FROM WEATHER");
        if (!StringUtils.isEmpty(form.getWeatherDateFrom())) {
            selectSql.append(" WHERE WEATHER_DATE >= :weatherDateFrom");
            isFirstCondition = false;
        }
        if (!StringUtils.isEmpty(form.getWeatherDateTo())) {
            if (isFirstCondition) {
                selectSql.append(" WHERE WEATHER_DATE <= :weatherDateTo");
                isFirstCondition = false;
            } else {
                selectSql.append(" and WEATHER_DATE <= :weatherDateTo");
            }
        }
        if (!StringUtils.isEmpty(form.getPlace())) {
            if (isFirstCondition) {
                selectSql.append(" WHERE PLACE = :place");
                isFirstCondition = false;
            } else {
                selectSql.append(" and PLACE = :place");
            }
        }
        if (!StringUtils.isEmpty(form.getWeather()) && StringUtils.split(form.getWeather(), ",").length != 4) {
            String[] weatherArray = StringUtils.split(form.getWeather(), ",");

            if (isFirstCondition) {
                selectSql.append(" WHERE (WEATHER = :weather");
                isFirstCondition = false;
            } else {
                selectSql.append(" and (WEATHER = :weather");
            }
            if (weatherArray.length == 1) {
                selectSql.append(")");
            } else if (weatherArray.length == 2) {
                selectSql.append(" OR WEATHER = :weather2)");
            } else if (weatherArray.length == 3) {
                selectSql.append(" OR WEATHER = :weather2 OR WEATHER = :weather3)");
            }
        }
        if (!StringUtils.isEmpty(form.getMaxTemperatureFrom())) {
            if (isFirstCondition) {
                selectSql.append(" WHERE MAX_TEMPERATURE >= :maxTemperatureFrom");
                isFirstCondition = false;
            } else {
                selectSql.append(" and MAX_TEMPERATURE >= :maxTemperatureFrom");
            }
        }
        if (!StringUtils.isEmpty(form.getMaxTemperatureTo())) {
            if (isFirstCondition) {
                selectSql.append(" WHERE MAX_TEMPERATURE <= :maxTemperatureTo");
                isFirstCondition = false;
            } else {
                selectSql.append(" and MAX_TEMPERATURE <= :maxTemperatureTo");
            }
        }
        if (!StringUtils.isEmpty(form.getMinTemperatureFrom())) {
            if (isFirstCondition) {
                selectSql.append(" WHERE MIN_TEMPERATURE >= :minTemperatureFrom");
                isFirstCondition = false;
            } else {
                selectSql.append(" and MIN_TEMPERATURE >= :minTemperatureFrom");
            }
        }
        if (!StringUtils.isEmpty(form.getMinTemperatureTo())) {
            if (isFirstCondition) {
                selectSql.append(" WHERE MIN_TEMPERATURE <= :minTemperatureTo");
                isFirstCondition = false;
            } else {
                selectSql.append(" and MIN_TEMPERATURE <= :minTemperatureTo");
            }
        }

        return selectSql.toString();
    }

    /**
     * 検索に使用する条件を作成する（天気検索発展）。
     *
     * @param form フォーム
     * @return SQL
     */
    public Map<String, String> createCondition(WeatherSearchForm form) {
        Map<String, String> condition = new HashMap<String, String>();
        if (!StringUtils.isEmpty(form.getWeatherDateFrom())) {
            condition.put("weatherDateFrom", form.getWeatherDateFrom());
        }
        if (!StringUtils.isEmpty(form.getWeatherDateTo())) {
            condition.put("weatherDateTo", form.getWeatherDateTo());
        }
        if (!StringUtils.isEmpty(form.getPlace())) {
            condition.put("place", form.getPlace());
        }
        if (!StringUtils.isEmpty(form.getWeather()) && StringUtils.split(form.getWeather(), ",").length != 4) {
            String[] weatherArray = StringUtils.split(form.getWeather(), ",");
            if (weatherArray.length == 1) {
                condition.put("weather", weatherArray[0]);
            } else if (weatherArray.length == 2) {
                condition.put("weather", weatherArray[0]);
                condition.put("weather2", weatherArray[1]);
            } else if (weatherArray.length == 3) {
                condition.put("weather", weatherArray[0]);
                condition.put("weather2", weatherArray[1]);
                condition.put("weather3", weatherArray[2]);
            }
        }
        if (!StringUtils.isEmpty(form.getMaxTemperatureFrom())) {
            condition.put("maxTemperatureFrom", form.getMaxTemperatureFrom());
        }
        if (!StringUtils.isEmpty(form.getMaxTemperatureTo())) {
            condition.put("maxTemperatureTo", form.getMaxTemperatureTo());
        }
        if (!StringUtils.isEmpty(form.getMinTemperatureFrom())) {
            condition.put("minTemperatureFrom", form.getMinTemperatureFrom());
        }
        if (!StringUtils.isEmpty(form.getMinTemperatureTo())) {
            condition.put("minTemperatureTo", form.getMinTemperatureTo());
        }

        return condition;
    }

    /**
     * 過去5年分の天気のリストを作成する。
     *
     * @param form フォーム
     * @return 過去5年分の天気のリスト
     */
    public List<Weather> createPastWeatherList(WeatherSearchForm form) {
        String selectSql = "SELECT * FROM WEATHER WHERE WEATHER_DATE LIKE :monthAndDay AND PLACE = :place";
        Map<String, String> condition = new HashMap<String, String>();
        // 月と日のみを検索に使用する
        String monthAndDay = "%" + StringUtils.right(form.getWeatherDate(), 5);
        String place = form.getPlace();
        condition.put("monthAndDay", monthAndDay);
        condition.put("place", place);

        return weatherDao.findBySql(selectSql, condition);
    }

    /**
     * 天気予測のDtoを作成する。
     *
     * @param form フォーム
     * @param pastWeatherList 過去の天気のリスト
     * @return 天気予測のDto
     */
    public WeatherDto createWeatherDto(WeatherSearchForm form, List<Weather> pastWeatherList) {
        WeatherDto expectWeather = new WeatherDto();
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
            expectWeather.setSunnyPercent(percent.intValue());
        }
        if (cloudyCount != 0) {
            Double percent = (cloudyCount / pastWeatherList.size()) * 100;
            expectWeather.setCloudyPercent(percent.intValue());
        }
        if (rainyCount != 0) {
            Double percent = (rainyCount / pastWeatherList.size()) * 100;
            expectWeather.setRainyPercent(percent.intValue());
        }
        if (snowCount != 0) {
            Double percent = (snowCount / pastWeatherList.size()) * 100;
            expectWeather.setSnowPercent(percent.intValue());
        }
        expectWeather.setMaxTemperatureAve(maxTemperatureSum / pastWeatherList.size());
        expectWeather.setMinTemperatureAve(minTemperatureSum / pastWeatherList.size());
        expectWeather.setWeatherDate(form.getWeatherDate());
        expectWeather.setPlace(form.getPlace());

        return expectWeather;
    }
}
