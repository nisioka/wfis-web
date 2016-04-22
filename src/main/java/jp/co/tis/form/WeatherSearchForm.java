package jp.co.tis.form;

import java.io.Serializable;
import java.util.List;

/**
 * 天気予報用Form。
 * 画面の入力項目や画面から送られてくる項目をプロパティとして持つクラス。
 *
 * @author Saito Takuma
 * @since 1.0
 */
public class WeatherSearchForm implements Serializable {

    /** 日付 */
    private String weatherDate;

    /** 日付From */
    private String weatherDateFrom;

    /** 日付To */
    private String weatherDateTo;

    /** 場所 */
    private String place;

    /** 天気 */
    private String weather;

    /** 最高気温 */
    private String maxTemperature;

    /** 最高気温From */
    private String maxTemperatureFrom;

    /** 最高気温To */
    private String maxTemperatureTo;

    /** 最低気温 */
    private String minTemperature;

    /** 最低気温From */
    private String minTemperatureFrom;

    /** 最低気温To */
    private String minTemperatureTo;

    /** ファイルパス */
    private String filePath;

    /** CSVデータリスト */
    private List<String> csvDataList;

    /**
     * デフォルトコンストラクタ。
     */
    public WeatherSearchForm() {
    }

    /**
     * コンストラクタ。
     *
     * @param weatherDate 日付
     * @param weatherDateFrom 日付From
     * @param weatherDateTo 日付To
     * @param place 場所
     * @param weather 天気
     * @param maxTemperature 最高気温
     * @param maxTemperatureFrom 最高気温From
     * @param maxTemperatureTo 最高気温To
     * @param minTemperature 最低気温
     * @param minTemperatureFrom 最低気温From
     * @param minTemperatureTo 最低気温To
     * @param filePath ファイルパス
     * @param csvDataList CSVデータリスト
     */
    public WeatherSearchForm(String weatherDate, String weatherDateFrom, String weatherDateTo, String place, String weather, String maxTemperature,
            String maxTemperatureFrom, String maxTemperatureTo, String minTemperature, String minTemperatureFrom, String minTemperatureTo,
            String filePath, List<String> csvDataList) {
        this.weatherDate = weatherDate;
        this.weatherDateFrom = weatherDateFrom;
        this.weatherDateTo = weatherDateTo;
        this.place = place;
        this.weather = weather;
        this.maxTemperature = maxTemperature;
        this.maxTemperatureFrom = maxTemperatureFrom;
        this.maxTemperatureTo = maxTemperatureTo;
        this.minTemperature = minTemperature;
        this.minTemperatureFrom = minTemperatureFrom;
        this.minTemperatureTo = minTemperatureTo;
        this.filePath = filePath;
        this.csvDataList = csvDataList;
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
     * 日付Fromを取得する。
     *
     * @return 日付From
     */
    public String getWeatherDateFrom() {
        return weatherDateFrom;
    }

    /**
     * 日付Fromを設定する。
     *
     * @param weatherDateFrom 日付From
     */
    public void setWeatherDateFrom(String weatherDateFrom) {
        this.weatherDateFrom = weatherDateFrom;
    }

    /**
     * 日付Toを取得する。
     *
     * @return 日付To
     */
    public String getWeatherDateTo() {
        return weatherDateTo;
    }

    /**
     * 日付Toを設定する。
     *
     * @param weatherDateTo 日付To
     */
    public void setWeatherDateTo(String weatherDateTo) {
        this.weatherDateTo = weatherDateTo;
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
     * 最高気温Fromを取得する。
     *
     * @return 最高気温From
     */
    public String getMaxTemperatureFrom() {
        return maxTemperatureFrom;
    }

    /**
     * 最高気温Fromを設定する。
     *
     * @param maxTemperatureFrom 最高気温From
     */
    public void setMaxTemperatureFrom(String maxTemperatureFrom) {
        this.maxTemperatureFrom = maxTemperatureFrom;
    }

    /**
     * 最高気温Toを取得する。
     *
     * @return 最高気温To
     */
    public String getMaxTemperatureTo() {
        return maxTemperatureTo;
    }

    /**
     * 最高気温Toを設定する。
     *
     * @param maxTemperatureTo 最高気温To
     */
    public void setMaxTemperatureTo(String maxTemperatureTo) {
        this.maxTemperatureTo = maxTemperatureTo;
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

    /**
     * 最低気温Fromを取得する。
     *
     * @return 最低気温From
     */
    public String getMinTemperatureFrom() {
        return minTemperatureFrom;
    }

    /**
     * 最低気温Fromを設定する。
     *
     * @param minTemperatureFrom 最低気温From
     */
    public void setMinTemperatureFrom(String minTemperatureFrom) {
        this.minTemperatureFrom = minTemperatureFrom;
    }

    /**
     * 最低気温Toを取得する。
     *
     * @return 最低気温To
     */
    public String getMinTemperatureTo() {
        return minTemperatureTo;
    }

    /**
     * 最低気温Toを設定する。
     *
     * @param minTemperatureTo 最低気温To
     */
    public void setMinTemperatureTo(String minTemperatureTo) {
        this.minTemperatureTo = minTemperatureTo;
    }

    /**
     * ファイルパスを取得する。
     *
     * @return ファイルパス
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * ファイルパスを設定する。
     *
     * @param filePath ファイルパス
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * CSVデータリストを取得する。
     *
     * @return CSVデータリスト
     */
    public List<String> getCsvDataList() {
        return csvDataList;
    }

    /**
     * CSVデータリストを設定する。
     *
     * @param csvDataList CSVデータリスト
     */
    public void setCsvDataList(List<String> csvDataList) {
        this.csvDataList = csvDataList;
    }
}
