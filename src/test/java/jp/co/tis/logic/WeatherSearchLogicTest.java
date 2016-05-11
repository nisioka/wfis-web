package jp.co.tis.logic;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.co.tis.App;

/**
 * 天気検索ロジックのテスト。
 *
 * @author Saito Takuma
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class WeatherSearchLogicTest {

    /** テスト対象クラス */
    @Autowired
    private WeatherSearchLogic target;

    /**
     * テスト。
     */
    @Test
    public void test() {
        fail("まだ実装されていません。");
    }

}