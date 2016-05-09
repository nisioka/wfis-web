package jp.co.tis.controller;

import jp.co.tis.form.WeatherSearchForm;
import jp.co.tis.model.WeatherDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 天気予報のコントローラークラス。
 *
 * @author Saito Takuma
 * @since 1.0
 */
@Controller
public class WeatherController {

    /** DB操作を行うDAO */
    @Autowired
    private WeatherDao weatherDao;


    /**
     * Formのセットアップを行う。
     *
     * @return Form
     */
    @ModelAttribute
    WeatherSearchForm setupForm() {
        return new WeatherSearchForm();
    }

    /**
     * TOP画面へ遷移する。
     *
     * @return ModelAndView
     */
    @RequestMapping("/")
    public ModelAndView top() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("top");

        return modelAndView;
    }

    /**
     * ウォーミングアップ画面へ遷移する。
     *
     * @return ModelAndView
     */
    @RequestMapping("/warmUp")
    public ModelAndView warmUp() {

        return null;
    }

    /**
     * 天気検索TOP画面へ遷移する。
     *
     * @return ModelAndView
     */
    @RequestMapping("/weatherSearch/top")
    public ModelAndView weatherSearchTop() {

        return null;
    }

    /**
     * 天気検索発展TOP画面へ遷移する。
     *
     * @return ModelAndView
     */
    @RequestMapping("/weatherSearchHard/top")
    public ModelAndView weatherSearchHardTop() {

        return null;
    }

    /**
     * 天気統計TOP画面へ遷移する。
     *
     * @return ModelAndView
     */
    @RequestMapping("/weatherStatistics/top")
    public ModelAndView weatherStatisticsTop() {

        return null;
    }

    /**
     * CSV登録TOP画面へ遷移する。
     *
     * @return ModelAndView
     */
    @RequestMapping("/csvRegister/top")
    public ModelAndView csvRegisterTop() {

        return null;
    }

    /**
     * 天気の検索を行う。
     *
     * @param form フォーム
     * @param bindingResult バリデーション結果
     * @return ModelAndView
     */
    @RequestMapping(value = "weatherSearch/search", method = RequestMethod.POST)
    public ModelAndView search(@Validated WeatherSearchForm form, BindingResult bindingResult) {

        return null;
    }

    /**
     * 天気の検索を行う（天気検索発展）。
     *
     * @param form フォーム
     * @param bindingResult バリデーション結果
     * @return ModelAndView
     */
    @RequestMapping(value = "weatherSearchHard/search", method = RequestMethod.POST)
    public ModelAndView searchHard(@Validated WeatherSearchForm form, BindingResult bindingResult) {

        return null;
    }

    /**
     * 天気の統計処理を行う。
     *
     * @param form フォーム
     * @param bindingResult バリデーション結果
     * @return ModelAndView
     */
    @RequestMapping(value = "weatherStatistics/analysis", method = RequestMethod.POST)
    public ModelAndView statistics(@Validated WeatherSearchForm form, BindingResult bindingResult) {

        return null;
    }

    /**
     * CSVファイルを読み込んで表示する。
     *
     * @param form フォーム
     * @param bindingResult バリデーション結果
     * @return ModelAndView
     */
    @RequestMapping(value = "csvRegister/csvRead", method = RequestMethod.POST)
    public ModelAndView csvRead(@Validated WeatherSearchForm form, BindingResult bindingResult) {

        return null;
    }

    /**
     * CSVファイルのデータを登録する。
     *
     * @param form フォーム
     * @param bindingResult バリデーション結果
     * @return ModelAndView
     */
    @Transactional
    @RequestMapping(value = "csvRegister/register", method = RequestMethod.POST)
    public ModelAndView register(@Validated WeatherSearchForm form, BindingResult bindingResult) {

        return null;
    }
}
