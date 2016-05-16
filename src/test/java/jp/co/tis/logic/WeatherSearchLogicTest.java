package jp.co.tis.logic;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tis.App;
import jp.co.tis.model.WeatherDao;

/**
 * 天気検索Logicのテスト。
 *
 * @author Yoshiwara Masashi
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@TransactionConfiguration
@Transactional
public class WeatherSearchLogicTest extends AbstractTransactionalJUnit4SpringContextTests {

    /** テスト対象クラス */
    @Autowired
    private WeatherSearchLogic target;

    /** DB操作DAO */
    @Autowired
    private WeatherDao weatherDao;

    /**
     * 各テストメソッドの前に呼ばれるセットアップメソッド。<br/>
     * WEATHERテーブルの中身を空にする。<br/>
     * DB関連のテストを行う場合、各テストメソッドで事前データをDBに登録する必要がある。<br/>
     * テスト終了後にはロールバックが行われるため、テスト実施前後でDBの中身は変わらない。
     */
    @Before
    public void setUp() {
        super.deleteFromTables("WEATHER");
    }
}
