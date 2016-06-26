package jp.co.tis.logic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.tis.exception.NoDataResultsException;
import jp.co.tis.form.WeatherSearchForm;
import jp.co.tis.model.Person;
import jp.co.tis.model.Weather;
import jp.co.tis.model.WeatherDao;

/**
 * 天気検索Logicクラス。<br/>
 * コントローラーに直接メソッド切り出しを行うと行数が膨れるため、<br/>
 * 業務ロジック部分はロジッククラスに切り出す。<br/>
 * JUnitテストをしやすくするための目的もある。
 *
 * @author Saito Takuma
 * @since 1.0
 */
@Component
public class WeatherSearchLogic {

    /** DB操作DAO. */
    @Autowired
    private WeatherDao weatherDao;

    /**
     * 自己紹介を作成する。
     *
     * @return Person
     */
    public Person createPersonInfo() {
        Person person = new Person();
        person.setMyName("TIS 太郎");
        person.setAge("25");
        person.setHobby("読書・映画・ショッピング");
        person.setSkill("空手5段");

        return person;
    }

    /**
     * 入力項目をバリデーションする（天気検索）。
     *
     * @param form フォーム
     * @return エラーリスト
     */
    public List<String> validateForm(WeatherSearchForm form) {
        List<String> errorList = new ArrayList<String>();

        if (!StringUtils.isEmpty(form.getWeatherDate()) && !dateValidation(form.getWeatherDate(), "yyyy/MM/dd")) {
            errorList.add("日付は日付形式で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getPlace()) && form.getPlace().length() > WeatherSearchForm.PLACE_MAX_LENGTH) {
            errorList.add("場所は10文字以内で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getWeather()) && form.getWeather().length() > WeatherSearchForm.WEATHER_MAX_LENGTH) {
            errorList.add("天気は10文字以内で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getMaxTemperature()) && !NumberUtils.isNumber(form.getMaxTemperature())) {
            errorList.add("最高気温は数値で入力してください。");
        } else if (!StringUtils.isEmpty(form.getMaxTemperature()) && numberLenghtValidate(form.getMaxTemperature())) {
            errorList.add("最高気温は3桁以内で入力してください。");
        }
        if (!StringUtils.isEmpty(form.getMinTemperature()) && !NumberUtils.isNumber(form.getMinTemperature())) {
            errorList.add("最低気温は数値で入力してください。");
        } else if (!StringUtils.isEmpty(form.getMinTemperature()) && numberLenghtValidate(form.getMinTemperature())) {
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
    public List<String> validateFormHard(WeatherSearchForm form) {
        List<String> errorList = new ArrayList<String>();

        validateSingleItem(form, errorList);

        // 単項目精査エラーがない場合のみ、項目間精査を行う。
        if(errorList.isEmpty()){
            validateBetweenItem(form, errorList);
        }

        return errorList;
    }

    /**
     * SQLと条件から天気情報を検索する。
     *
     * @param form フォーム
     * @return 検索結果
     */
    public List<Weather> findBySql(WeatherSearchForm form) {
        String selectSql = createSql(form);
        Map<String, String> condition = createCondition(form);
        List<Weather> results = weatherDao.findBySql(selectSql, condition);
        if(results.isEmpty()){
            throw new NoDataResultsException("検索結果がありません。");
        }
        return results;
    }

    /**
     * 検索に使用するSQLを作成する。
     *
     * @param form フォーム
     * @return SQL
     */
    public String createSql(WeatherSearchForm form) {
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
    public Map<String, String> createCondition(WeatherSearchForm form) {
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("weatherDate", form.getWeatherDate());
        condition.put("place", form.getPlace());
        condition.put("weather", form.getWeather());
        condition.put("maxTemperature", form.getMaxTemperature());
        condition.put("minTemperature", form.getMinTemperature());

        return condition;
    }

    /**
     * SQLと条件から天気情報を検索する（天気検索発展）。
     *
     * @param form フォーム
     * @return 検索結果
     */
    public List<Weather> findBySqlHard(WeatherSearchForm form) {
        String selectSql = createSqlHard(form);
        Map<String, String> condition = createConditionHard(form);
        List<Weather> results = weatherDao.findBySql(selectSql, condition);
        if(results.isEmpty()){
            throw new NoDataResultsException("検索結果がありません。");
        }
        return results;
    }

    /**
     * 検索に使用するSQLを作成する（天気検索発展）。
     *
     * @param form フォーム
     * @return SQL
     */
    public String createSqlHard(WeatherSearchForm form) {
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
    public Map<String, String> createConditionHard(WeatherSearchForm form) {
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
     * 日付形式のバリデーションを行う。
     *
     * @param date 日付文字列
     * @param dateFormat 日付フォーマット形式
     * @return 精査結果
     */
    private boolean dateValidation(String date, String dateFormat) {
        DateFormat format = new SimpleDateFormat(dateFormat);
        format.setLenient(false);
        try {
            Date formatedDate = format.parse(date);
            if (!date.equals(formatedDate.toString())) {
                // パースした結果と元の文字列が異なる場合は精査エラー
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 数値の桁数精査を行う。<br/>
     * マイナスの有無に関わらず、数値のみの桁数が3文字以内であることを精査。
     * @param number 数値文字列
     * @return 精査結果
     */
    private boolean numberLenghtValidate(String number) {
        Pattern patern = Pattern.compile("^-?\\d{1,3}$");
        return patern.matcher(number).matches();
    }

    /**
     * 入力項目のバリデーションする（天気検索発展）。
     *
     * @param form フォーム
     * @param errorList エラーリスト
     */
    private void validateSingleItem(WeatherSearchForm form, List<String> errorList) {
        // 開始日付もしくは終了日付が精査エラーの場合
        if ((!StringUtils.isEmpty(form.getWeatherDateFrom()) && !dateValidation(form.getWeatherDateFrom(), "yyyy/MM/dd"))
                || (!StringUtils.isEmpty(form.getWeatherDateTo()) && !dateValidation(form.getWeatherDateTo(), "yyyy/MM/dd"))) {
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
    }

    /**
     * 入力項目間のバリデーションする（天気検索発展）。
     *
     * @param form フォーム
     * @param errorList エラーリスト
     */
    private void validateBetweenItem(WeatherSearchForm form, List<String> errorList) {
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
    }
}
