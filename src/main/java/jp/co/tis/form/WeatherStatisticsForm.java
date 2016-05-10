package jp.co.tis.form;

import java.io.Serializable;

/**
 * 天気統計用Form。<br/>
 * 画面の入力項目や画面から送られてくる項目をプロパティとして持つクラス。
 *
 * @author Saito Takuma
 * @since 1.0
 */
public class WeatherStatisticsForm implements Serializable {

    /** 日付 */
    private String weatherDate;

    /** 場所 */
    private String place;

    /**
     * デフォルトコンストラクタ。
     */
    public WeatherStatisticsForm() {
    }

    /**
     * コンストラクタ。
     *
     * @param weatherDate 日付
     * @param place 場所
     */
    public WeatherStatisticsForm(String weatherDate, String place) {
        this.weatherDate = weatherDate;
        this.place = place;
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

}
