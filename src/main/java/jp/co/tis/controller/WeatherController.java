package jp.co.tis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.tis.form.WeatherSearchForm;
import jp.co.tis.logic.WeatherSearchLogic;

/**
 * 天気予報のコントローラークラス。
 *
 * @author Saito Takuma
 * @since 1.0
 */
@Controller
public class WeatherController {

    /** 天気検索ロジッククラス */
    @Autowired
    private WeatherSearchLogic weatherSearchLogic;

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
    @RequestMapping("/weatherSimpleSearch/search")
    public ModelAndView simpleSearch(WeatherSearchForm form) {

        return null;
    }

    /**
     * 天気の検索を行う（天気検索）。
     *
     * @param form フォーム
     * @return ModelAndView
     */
    @RequestMapping("/weatherSearch/search")
    public ModelAndView search(WeatherSearchForm form) {

        return null;
    }
}
