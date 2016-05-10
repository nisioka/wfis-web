package jp.co.tis.logic;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.co.tis.App;

/**
 * CSV登録Logicのテスト。
 *
 * @author Saito Takuma
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class CsvRegisterLogicTest {

    /** テスト対象クラス */
    @Autowired
    private CsvRegisterLogic target;

}
