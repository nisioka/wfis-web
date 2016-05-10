package jp.co.tis.logic;

import java.io.FileNotFoundException;
import java.io.IOException;
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

import jp.co.tis.exception.FileFormatException;
import jp.co.tis.exception.SystemException;
import jp.co.tis.form.CsvRegisterForm;
import jp.co.tis.form.WeatherSearchForm;
import jp.co.tis.form.WeatherStatisticsForm;
import jp.co.tis.model.Weather;
import jp.co.tis.model.WeatherDao;
import jp.co.tis.model.WeatherDto;
import jp.co.tis.util.CsvReaderImpl;

/**
 * 天気予報Logicクラス。<br/> コントローラーに直接メソッド切り出しを行うと行数が膨れるため<br/>
 * 業務ロジック部分はロジッククラスに切り出す。<br/> JUnitテストをしやすくするための目的もある。
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
     * 入力項目をバリデーションする。
     *
     * @param form フォーム
     * @return エラーリスト
     */
    public List<String> validateFormForSearch(WeatherSearchForm form) {
        List<String> errorList = new ArrayList<String>();

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
    public List<String> validateFormForSearchHard(WeatherSearchForm form) {
        List<String> errorList = new ArrayList<String>();

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
    public List<String> validateBetweenItemForSearchHard(WeatherSearchForm form) {
        List<String> errorList = new ArrayList<String>();

        if (!StringUtils.isEmpty(form.getWeatherDateFrom()) && !StringUtils.isEmpty(form.getWeatherDateTo())) {
            if (form.getWeatherDateFrom().compareTo(form.getWeatherDateTo()) > 0) {
                errorList.add("日付の範囲指定が不正です。");
            }
        }
        if (!StringUtils.isEmpty(form.getMaxTemperatureFrom()) && !StringUtils.isEmpty(form.getMaxTemperatureTo())) {
            if (form.getMaxTemperatureFrom().compareTo(form.getMaxTemperatureTo()) > 0) {
                errorList.add("最高気温の範囲指定が不正です。");
            }
        }
        if (!StringUtils.isEmpty(form.getMinTemperatureFrom()) && !StringUtils.isEmpty(form.getMinTemperatureTo())) {
            if (form.getMinTemperatureFrom().compareTo(form.getMinTemperatureTo()) > 0) {
                errorList.add("最低気温の範囲指定が不正です。");
            }
        }

        return errorList;
    }

    /**
     * 入力項目をバリデーションする（天気統計）。
     *
     * @param form フォーム
     * @return エラーリスト
     */
    public List<String> validateFormForStatistics(WeatherStatisticsForm form) {
        List<String> errorList = new ArrayList<String>();

        if (StringUtils.isEmpty(form.getWeatherDate()) || StringUtils.isEmpty(form.getPlace())) {
            errorList.add("日付と場所は、必ず両方入力してください。");
        }
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
    public List<String> validateFormForCsvRead(CsvRegisterForm form) {
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
     * 検索に使用するSQLを作成する。
     *
     * @param form フォーム
     * @return SQL
     */
    public String createSqlForSearch(WeatherSearchForm form) {
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
     * 検索に使用する条件を作成する。
     *
     * @param form フォーム
     * @return 検索条件
     */
    public Map<String, String> createConditionForSearch(WeatherSearchForm form) {
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("weatherDate", form.getWeatherDate());
        condition.put("place", form.getPlace());
        condition.put("weather", form.getWeather());
        condition.put("maxTemperature", form.getMaxTemperature());
        condition.put("minTemperature", form.getMinTemperature());

        return condition;
    }

    /**
     * 検索に使用するSQLを作成する（天気検索発展）。
     *
     * @param form フォーム
     * @return SQL
     */
    public String createSqlForSearchHard(WeatherSearchForm form) {
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
     * @return 検索条件
     */
    public Map<String, String> createConditionForSearchHard(WeatherSearchForm form) {
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("weatherDateFrom", form.getWeatherDateFrom());
        condition.put("weatherDateTo", form.getWeatherDateTo());
        condition.put("place", form.getPlace());
        condition.put("maxTemperatureFrom", form.getMaxTemperatureFrom());
        condition.put("maxTemperatureTo", form.getMaxTemperatureTo());
        condition.put("minTemperatureFrom", form.getMinTemperatureFrom());
        condition.put("minTemperatureTo", form.getMinTemperatureTo());

        // 天気項目の検索条件をチェックボックスの数に応じて設定する
        if (!StringUtils.isEmpty(form.getWeather())) {
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
            } else {
                // 全てにチェックが入っている場合は条件を指定しない。
            }
        }

        return condition;
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

        return weatherDao.findBySql(selectSql, condition);
    }

    /**
     * 天気統計のDtoを作成する。
     *
     * @param form フォーム
     * @param pastWeatherList 過去の天気のリスト
     * @return 天気統計のDto
     */
    public WeatherDto createWeatherDto(WeatherStatisticsForm form, List<Weather> pastWeatherList) {
        WeatherDto statisticsWeather = new WeatherDto();
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

    /**
     * CSVファイルの読み込み処理を行う。
     *
     * @param form フォーム
     * @throws Exception CSV読込中に何らかの例外が発生した場合にスローされる
     * @return 読み込んだCSVファイルのデータ部。
     */
    public List<String> createCsvDataList(CsvRegisterForm form) throws Exception {
        // CSVファイル読み込み処理
        CsvReaderImpl csvReaderImpl = new CsvReaderImpl(form.getFilePath());
        try {
            csvReaderImpl.open();
        } catch (FileNotFoundException | FileFormatException e) {
            throw new Exception(e.getMessage());
        }

        int rowCount = 0;
        List<String> csvDataList = new ArrayList<String>();
        try {
            while (true) {
                Map<String, String> row = csvReaderImpl.readLine();
                // 読み込む行がなくなった場合
                if (row == null) {
                    break;
                }

                StringBuilder csvData = new StringBuilder("");
                for (String key : row.keySet()) {
                    csvData.append("'").append(row.get(key)).append("',");
                }
                csvDataList.add(StringUtils.removeEnd(csvData.toString(), ","));
                rowCount++;
            }
        } catch (IOException e) {
            csvReaderImpl.close();
            throw new SystemException("システム例外が発生しました。", e);
        } catch (FileFormatException e) {
            // ヘッダーの行数も考慮するため
            rowCount += 2;
            csvReaderImpl.close();
            throw new Exception(rowCount + "行目 ：" + e.getMessage());
        }
        csvReaderImpl.close();
        return csvDataList;
    }

    /**
     * CSVファイルを読み込んでデータをテーブルに登録する。
     *
     * @param csvDataList 登録するCSVファイルのデータ部
     * @return 登録件数
     */
    public int insert(List<String> csvDataList) {
        // 一行ずつDBに登録
        for (String csvData : csvDataList) {
            StringBuilder insertSql = new StringBuilder("");
            insertSql.append("INSERT INTO WEATHER (WEATHER_DATE, PLACE, WEATHER, MAX_TEMPERATURE, MIN_TEMPERATURE) VALUES (");
            insertSql.append(csvData).append(")");
            weatherDao.insert(insertSql.toString());
        }
        return csvDataList.size();
    }

}
