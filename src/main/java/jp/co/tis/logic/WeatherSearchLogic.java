package jp.co.tis.logic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    /** DB操作DAO */
    @Autowired
    private WeatherDao weatherDao;

    /**
     * 自己紹介を作成する。
     *
     * @return Person
     */
    public Person createPersonInfo() {
        Person person = new Person();

        return person;
    }

    /**
     * SQLから天気一覧を検索する。
     *
     * @return 検索結果
     */
    public List<Weather> findBySqlWeatherList() {

        return null;
    }

    /**
     * 検索に使用するSQLを作成する（天気簡易検索）。
     *
     * @param form フォーム
     * @return SQL
     */
    public String createSqlSimple(WeatherSearchForm form) {

        return null;
    }

    /**
     * 検索に使用する条件を作成する(天気簡易検索)。
     *
     * @param form フォーム
     * @return 検索結果
     */
    public Map<String, String> createConditionSimple(WeatherSearchForm form) {

        return null;
    }

    /**
     * SQLと条件から天気情報を検索する（天気簡易検索)。
     *
     * @param form フォーム
     * @return 検索結果
     */
    public List<Weather> findBySqlSimple(WeatherSearchForm form) {

        return null;
    }

    /**
     * 入力項目をバリデーションする。
     *
     * @param form フォーム
     * @return エラーリスト
     */
    public List<String> validateForm(WeatherSearchForm form) {

        return null;
    }

    /**
     * 検索に使用するSQLを作成する（天気検索）。
     *
     * @param form フォーム
     * @return SQL
     */
    public String createSql(WeatherSearchForm form) {

        return null;
    }

    /**
     * 検索に使用する条件を作成する(天気検索)。
     *
     * @param form フォーム
     * @return 検索条件
     */
    public Map<String, String> createCondition(WeatherSearchForm form) {

        return null;
    }

    /**
     * SQLと条件から天気情報を検索する(天気検索)。
     *
     * @param form フォーム
     * @return 検索結果
     */
    public List<Weather> findBySql(WeatherSearchForm form) {

        return null;
    }
}
