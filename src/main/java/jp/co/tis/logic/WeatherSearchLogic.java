package jp.co.tis.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.tis.model.Person;
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
}
