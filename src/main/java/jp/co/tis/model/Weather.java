package jp.co.tis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 天気エンティティクラス。
 * 
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
     * コンストラクタ。
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
     * @return weatherDate
     */
    public String getWeatherDate() {
        return weatherDate;
    }

    /**
     * @param weatherDate セットする weatherDate
     */
    public void setWeatherDate(String weatherDate) {
        this.weatherDate = weatherDate;
    }

    /**
     * @return place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @param place セットする place
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * @return weather
     */
    public String getWeather() {
        return weather;
    }

    /**
     * @param weather セットする weather
     */
    public void setWeather(String weather) {
        this.weather = weather;
    }

    /**
     * @return maxTemperature
     */
    public String getMaxTemperature() {
        return maxTemperature;
    }

    /**
     * @param maxTemperature セットする maxTemperature
     */
    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    /**
     * @return minTemperature
     */
    public String getMinTemperature() {
        return minTemperature;
    }

    /**
     * @param minTemperature セットする minTemperature
     */
    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

}