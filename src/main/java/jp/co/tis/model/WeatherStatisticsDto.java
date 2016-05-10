package jp.co.tis.model;

/**
 * 天気Dtoクラス。<br/>
 * Dtoはコントローラー側から画面に表示する値として渡したいプロパティを集めたクラス。<br/>
 * 入力項目や画面から送られてくる値ではなく、ただ画面に渡す目的のみに使うクラス。
 *
 * @author Saito Takuma
 * @since 1.0
 *
 */
public class WeatherStatisticsDto {

    /** 日付 */
    private String weatherDate;

    /** 場所 */
    private String place;

    /** 天気 */
    private String weather;

    /** 平均最高気温 */
    private int maxTemperatureAve;

    /** 平均最低気温 */
    private int minTemperatureAve;

    /** 晴れの確率 */
    private int sunnyPercent;

    /** 曇りの確率 */
    private int cloudyPercent;

    /** 雨の確率 */
    private int rainyPercent;

    /** 雪の確率 */
    private int snowPercent;

    /**
     * デフォルトコンストラクタ。
     */
    public WeatherStatisticsDto() {
        super();
    }

    /**
     * コンストラクタ。
     *
     * @param weatherDate 日付
     * @param place 場所
     * @param weather 天気
     * @param maxTemperatureAve 平均最高気温
     * @param minTemperatureAve 平均最低気温
     * @param sunnyPercent 晴れの確率
     * @param cloudyPercent 曇りの確率
     * @param rainyPercent 雨の確率
     * @param snowPercent 雪の確率
     */
    public WeatherStatisticsDto(String weatherDate, String place, String weather, int maxTemperatureAve, int minTemperatureAve, int sunnyPercent,
            int cloudyPercent, int rainyPercent, int snowPercent) {
        super();
        this.weatherDate = weatherDate;
        this.place = place;
        this.weather = weather;
        this.maxTemperatureAve = maxTemperatureAve;
        this.minTemperatureAve = minTemperatureAve;
        this.sunnyPercent = sunnyPercent;
        this.cloudyPercent = cloudyPercent;
        this.rainyPercent = rainyPercent;
        this.snowPercent = snowPercent;
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
     * 平均最高気温を取得する。
     *
     * @return 平均最高気温
     */
    public int getMaxTemperatureAve() {
        return maxTemperatureAve;
    }

    /**
     * 平均最高気温を設定する。
     *
     * @param maxTemperatureAve 平均最高気温
     */
    public void setMaxTemperatureAve(int maxTemperatureAve) {
        this.maxTemperatureAve = maxTemperatureAve;
    }

    /**
     * 平均最低気温を取得する。
     *
     * @return 平均最低気温
     */
    public int getMinTemperatureAve() {
        return minTemperatureAve;
    }

    /**
     * 平均最低気温を設定する。
     *
     * @param minTemperatureAve 平均最低気温
     */
    public void setMinTemperatureAve(int minTemperatureAve) {
        this.minTemperatureAve = minTemperatureAve;
    }

    /**
     * 晴れの確率を取得する。
     *
     * @return 晴れの確率
     */
    public int getSunnyPercent() {
        return sunnyPercent;
    }

    /**
     * 晴れの確率を設定する。
     *
     * @param sunnyPercent 晴れの確率
     */
    public void setSunnyPercent(int sunnyPercent) {
        this.sunnyPercent = sunnyPercent;
    }

    /**
     * 曇りの確率を取得する。
     *
     * @return 曇りの確率
     */
    public int getCloudyPercent() {
        return cloudyPercent;
    }

    /**
     * 曇りの確率を設定する。
     *
     * @param cloudyPercent 曇りの確率
     */
    public void setCloudyPercent(int cloudyPercent) {
        this.cloudyPercent = cloudyPercent;
    }

    /**
     * 雨の確率を取得する。
     *
     * @return 雨の確率
     */
    public int getRainyPercent() {
        return rainyPercent;
    }

    /**
     * 雨の確率を設定する。
     *
     * @param rainyPercent 雨の確率
     */
    public void setRainyPercent(int rainyPercent) {
        this.rainyPercent = rainyPercent;
    }

    /**
     * 雪の確率を取得する。
     *
     * @return 雪の確率
     */
    public int getSnowPercent() {
        return snowPercent;
    }

    /**
     * 雪の確率を設定する。
     *
     * @param snowPercent 雪の確率
     */
    public void setSnowPercent(int snowPercent) {
        this.snowPercent = snowPercent;
    }
}