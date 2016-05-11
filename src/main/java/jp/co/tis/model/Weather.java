package jp.co.tis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 天気エンティティクラス。<br/>
 * Weatherテーブルとのデータのやり取りに使用するクラス。<br/>
 * 検索した結果を保持したりデータ登録の際に使用する。
 *
 * @author Saito Takuma
 * @since 1.0
 *
 */
@Entity
@Table(name = "Weather")
public class Weather {

    @Id
    @Column
    private String weatherDate;

    @Column
    private String place;

    @Column
    private String weather;

    @Column
    private String maxTemperature;

    @Column
    private String minTemperature;

    /**
     * デフォルトコンストラクタ。
     */
    public Weather() {
        super();
    }

    /**
     * コンストラクタ。
     *
     * @param weatherDate 日付
     * @param place 場所
     * @param weather 天気
     * @param maxTemperature 最高気温
     * @param minTemperature 最低気温
     */
    public Weather(String weatherDate, String place, String weather, String maxTemperature, String minTemperature) {
        super();
        this.weatherDate = weatherDate;
        this.place = place;
        this.weather = weather;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    /**
     * 日付を取得する。
     *
     * @return 日付
     */
    public String getWeatherDate() {
        return weatherDate;
    }

    /**
     * 日付を設定する。
     *
     * @param weatherDate 日付
     */
    public void setWeatherDate(String weatherDate) {
        this.weatherDate = weatherDate;
    }

    /**
     * 場所を取得する。
     *
     * @return 場所
     */
    public String getPlace() {
        return place;
    }

    /**
     * 場所を設定する。
     *
     * @param place 場所
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * 天気を取得する。
     *
     * @return 天気
     */
    public String getWeather() {
        return weather;
    }

    /**
     * 天気を設定する。
     *
     * @param weather 天気
     */
    public void setWeather(String weather) {
        this.weather = weather;
    }

    /**
     * 最高気温を取得する。
     *
     * @return 最高気温
     */
    public String getMaxTemperature() {
        return maxTemperature;
    }

    /**
     * 最高気温を設定する。
     *
     * @param maxTemperature 最高気温
     */
    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    /**
     * 最低気温を取得する。
     *
     * @return 最低気温
     */
    public String getMinTemperature() {
        return minTemperature;
    }

    /**
     * 最低気温を設定する。
     *
     * @param minTemperature 最低気温
     */
    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

}