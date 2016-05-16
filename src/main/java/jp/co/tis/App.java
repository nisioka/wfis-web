package jp.co.tis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 起動クラス。<br/>
 * mainを実行することでサーバー起動する。<br/>
 * サーバー起動中に再度起動を行うとエラーとなる。
 *
 * @author Saito Takuma
 * @since 1.0
 *
 */
@SpringBootApplication
public class App {

    /**
     * 起動のためのmainクラス。
     *
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}