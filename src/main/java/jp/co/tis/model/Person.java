package jp.co.tis.model;

/**
 * 自己紹介クラス。<br/>
 * ウォーミングアップのみに使用する。
 *
 * @author Saito Takuma
 * @since 1.0
 *
 */
public class Person {

    /** 名前 */
    private String myName;

    /** 年齢 */
    private String age;

    /** 趣味 */
    private String hobby;

    /** 特技 */
    private String skill;

    /**
     * デフォルトコンストラクタ。
     */
    public Person() {
        super();
    }

    /**
     * コンストラクタ。
     *
     * @param myName 名前
     * @param age 年齢
     * @param hobby 趣味
     * @param skill 特技
     */
    public Person(String myName, String age, String hobby, String skill) {
        super();
        this.myName = myName;
        this.age = age;
        this.hobby = hobby;
        this.skill = skill;
    }

    /**
     * 名前を取得する。
     *
     * @return 名前
     */
    public String getMyName() {
        return myName;
    }

    /**
     * 名前を設定する。
     *
     * @param myName 名前
     */
    public void setMyName(String myName) {
        this.myName = myName;
    }

    /**
     * 年齢を取得する。
     *
     * @return 年齢
     */
    public String getAge() {
        return age;
    }

    /**
     * 年齢を設定する。
     *
     * @param age 年齢
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * 趣味を取得する。
     *
     * @return 趣味
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * 趣味を設定する。
     *
     * @param hobby 趣味
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    /**
     * 特技を取得する。
     *
     * @return 特技
     */
    public String getSkill() {
        return skill;
    }

    /**
     * 特技を設定する。
     *
     * @param skill 特技
     */
    public void setSkill(String skill) {
        this.skill = skill;
    }

}