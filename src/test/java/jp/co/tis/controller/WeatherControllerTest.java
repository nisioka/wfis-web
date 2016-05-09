package jp.co.tis.controller;

import static org.junit.Assert.fail;
import jp.co.tis.App;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 天気予報コントローラのテスト。
 *
 * @author Saito Takuma
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class WeatherControllerTest {

    /** テスト対象クラス */
    @Autowired
    private WeatherController target;

    /**
     * テスト。
     */
    @Test
    public void test() {
        fail("まだ実装されていません。");
    }

}
