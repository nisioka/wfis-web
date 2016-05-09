package jp.co.tis.controller;

import jp.co.tis.form.WeatherSearchForm;
import jp.co.tis.model.WeatherDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

        return null;
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
     * 天気一覧画面へ遷移する。
     *
     * @return ModelAndView
     */
    @RequestMapping("/weatherList")
    public ModelAndView weatherList() {

        return null;
    }

    /**
     * 天気簡易検索TOP画面へ遷移する。
     *
     * @return ModelAndView
     */
    @RequestMapping("/weatherSimpleSearch/top")
    public ModelAndView weatherSimpleSearchTop() {

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
     * 天気の検索を行う（天気簡易検索）。
     *
     * @param form フォーム
     * @return ModelAndView
     */
    @RequestMapping(value = "weatherSimpleSearch/search", method = RequestMethod.POST)
    public ModelAndView simpleSearch(WeatherSearchForm form) {

        return null;
    }

    /**
     * 天気の検索を行う（天気検索）。
     *
     * @param form フォーム
     * @param bindingResult バリデーション結果
     * @return ModelAndView
     */
    @RequestMapping(value = "weatherSearch/search", method = RequestMethod.POST)
    public ModelAndView search(@Validated WeatherSearchForm form, BindingResult bindingResult) {

        return null;
    }

}
