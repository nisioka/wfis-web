package jp.co.tis.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * データベースの検索結果をEntityなどのDTOにマッピングする。
 *
 * @author Takuma Saito
 * @since 1.0
 */
@Component
public class WeatherDao {

    /** DB登録・更新・削除のためのクラス */
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * SQLをもとに検索を行う。<br/>
     * 引数のSQLには、文字列としてSQLを渡す。<br/>
     * SQLの最後に「;」は不要。<br/>
     * 例：
     * <code><pre>
     * String sql = "SELECT A, B, C FROM TEST_TABLE";
     * </pre></code>
     *
     * @param selectSql 実行したいSQL
     * @return 検索結果
     */
    public List<Weather> findBySql(String selectSql) {
        RowMapper<Weather> mapper = new BeanPropertyRowMapper<Weather>(Weather.class);
        List<Weather> weatherList = jdbcTemplate.query(selectSql, mapper);

        return weatherList;
    }

    /**
     * SQLとパラメータをもとに検索を行う。{@link WeatherDao#findBySql}<br/>
     * 第一引数のSQLに、値を埋め込みたい場合「:変数名」として記述する。<br/>
     * 第二引数のMapの「key」に第一引数のSQLで設定した変数名を設定し
     * 「value」にSQLに埋め込みたい値を設定する。<br/>
     * 例：
     * <code><pre>
     * String sql = "SELECT A, B, C FROM TEST_TABLE WHERE A = :変数名";
     * contion.put(変数名, SQLに埋め込みたい値);
     * </pre></code>
     *
     * @param selectSql 実行したいSQL
     * @param condition SQLに埋め込むパラメータ
     * @return 検索結果
     */
    public List<Weather> findBySql(String selectSql, Map<String, String> condition) {
        RowMapper<Weather> mapper = new BeanPropertyRowMapper<Weather>(Weather.class);
        List<Weather> weatherList = jdbcTemplate.query(selectSql, condition, mapper);

        return weatherList;
    }

    /**
     * SQLをもとに登録を行う。{@link WeatherDao#findBySql}<br/>
     *
     * @param insertSql 実行したいSQL
     * @return 登録件数
     */
    public int insert(String insertSql) {
        return insert(insertSql, new HashMap<String, String>());
    }

    /**
     * SQLとパラメータをもとに登録を行う。{@link WeatherDao#findBySql}<br/>
     * 第一引数のSQLに、動的に値を埋め込みたい場合「:変数名」として記述する。<br/>
     * 第二引数のMapの「key」に第一引数のSQLで設定した変数名を設定し
     * 「value」にSQLに埋め込みたい値を設定する。<br/>
     *
     * @param insertSql 実行したいSQL
     * @param condition SQLに埋め込むパラメータ
     * @return 登録件数
     */
    public int insert(String insertSql, Map<String, String> condition) {
        int insertCount = jdbcTemplate.update(insertSql, condition);

        return insertCount;
    }

    /**
     * SQLをもとに更新を行う。{@link WeatherDao#findBySql}<br/>
     *
     * @param updateSql 実行したいSQL
     * @return 更新件数
     */
    public int update(String updateSql) {
        return update(updateSql, new HashMap<String, String>());
    }

    /**
     * SQLとパラメータをもとに更新を行う。{@link WeatherDao#findBySql}<br/>
     * 第一引数のSQLに、動的に値を埋め込みたい場合「:変数名」として記述する。<br/>
     * 第二引数のMapの「key」に第一引数のSQLで設定した変数名を設定し
     * 「value」にSQLに埋め込みたい値を設定する。<br/>
     *
     * @param updateSql 実行したいSQL
     * @param condition SQLに埋め込むパラメータ
     * @return 更新件数
     */
    public int update(String updateSql, Map<String, String> condition) {
        int updateCount = jdbcTemplate.update(updateSql, condition);

        return updateCount;
    }

    /**
     * SQLをもとに削除を行う。{@link WeatherDao#findBySql}<br/>
     *
     * @param deleteSql 実行したいSQL
     * @return 削除件数
     */
    public int delete(String deleteSql) {
        return delete(deleteSql, new HashMap<String, String>());
    }

    /**
     * SQLとパラメータをもとに削除を行う。{@link WeatherDao#findBySql}<br/>
     * 第一引数のSQLに、動的に値を埋め込みたい場合「:変数名」として記述する。<br/>
     * 第二引数のMapの「key」に第一引数のSQLで設定した変数名を設定し
     * 「value」にSQLに埋め込みたい値を設定する。<br/>
     *
     * @param deleteSql 実行したいSQL
     * @param condition SQLに埋め込むパラメータ
     * @return 削除件数
     */
    public int delete(String deleteSql, Map<String, String> condition) {
        int deleteCount = jdbcTemplate.update(deleteSql, condition);

        return deleteCount;
    }
}
