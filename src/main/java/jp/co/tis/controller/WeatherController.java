package jp.co.tis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.tis.form.CsvRegisterForm;
import jp.co.tis.form.WeatherSearchForm;
import jp.co.tis.form.WeatherStatisticsForm;
import jp.co.tis.logic.CsvRegisterLogic;
import jp.co.tis.logic.WeatherSearchLogic;
import jp.co.tis.logic.WeatherStatisticsLogic;

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

    /** 天気統計ロジッククラス */
    @Autowired
    private WeatherStatisticsLogic weatherStatisticsLogic;

    /** CSV登録ロジッククラス */
    @Autowired
    private CsvRegisterLogic csvRegisterLogic;

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
     * @return ModelAndView
     */
    @RequestMapping("/weatherSearch/search")
    public ModelAndView search(WeatherSearchForm form) {

        return null;
    }

    /**
     * 天気の統計処理を行う。
     *
     * @param form フォーム
     * @return ModelAndView
     */
    @RequestMapping("/weatherStatistics/analysis")
    public ModelAndView statistics(WeatherStatisticsForm form) {

        return null;
    }

    /**
     * CSVファイルを読み込んでデータをテーブルに登録する。
     *
     * @param form フォーム
     * @return ModelAndView
     */
    @Transactional
    @RequestMapping("/csvRegister/insert")
    public ModelAndView csvRegister(CsvRegisterForm form) {

        return null;
    }
}
