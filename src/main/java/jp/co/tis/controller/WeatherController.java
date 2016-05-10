package jp.co.tis.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.tis.form.CsvRegisterForm;
import jp.co.tis.form.WeatherSearchForm;
import jp.co.tis.form.WeatherStatisticsForm;
import jp.co.tis.logic.WeatherLogic;
import jp.co.tis.model.Weather;
import jp.co.tis.model.WeatherDao;
import jp.co.tis.model.WeatherDto;

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

    /** 天気予報ロジッククラス */
    @Autowired
    private WeatherLogic weatherLogic;

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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("myName", "TIS太郎");
        modelAndView.addObject("age", "25");
        modelAndView.addObject("hobby", "読書・映画・ショッピング");
        modelAndView.addObject("skill", "空手5段");
        modelAndView.setViewName("warmUp");

        return modelAndView;
    }

    /**
     * 天気検索TOP画面へ遷移する。
     *
     * @return ModelAndView
     */
    @RequestMapping("/weatherSearch/top")
    public ModelAndView weatherSearchTop() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("weatherSearch");
        return modelAndView;
    }

    /**
     * 天気検索発展TOP画面へ遷移する。
     *
     * @return ModelAndView
     */
    @RequestMapping("/weatherSearchHard/top")
    public ModelAndView weatherSearchHardTop() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("weatherSearchHard");
        return modelAndView;
    }

    /**
     * 天気統計TOP画面へ遷移する。
     *
     * @return ModelAndView
     */
    @RequestMapping("/weatherStatistics/top")
    public ModelAndView weatherStatisticsTop() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("weatherStatistics");
        return modelAndView;
    }

    /**
     * CSV登録TOP画面へ遷移する。
     *
     * @return ModelAndView
     */
    @RequestMapping("/csvRegister/top")
    public ModelAndView csvRegisterTop() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("csvRegister");
        return modelAndView;
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
        ModelAndView modelAndView = new ModelAndView();

        // 項目精査を行う
        List<String> errorList = weatherLogic.validateFormForSearch(form);
        if (!errorList.isEmpty()) {
            modelAndView.addObject("errorList", errorList);
            modelAndView.addObject("form", form);
            modelAndView.setViewName("weatherSearch");
            return modelAndView;
        }

        String selectSql = weatherLogic.createSqlForSearch(form);
        Map<String, String> condition = weatherLogic.createConditionForSearch(form);
        List<Weather> weatherList = weatherDao.findBySql(selectSql, condition);

        if (weatherList.isEmpty()) {
            errorList.add("検索結果がありません。");
            modelAndView.addObject("errorList", errorList);
            modelAndView.addObject("form", form);
            modelAndView.setViewName("weatherSearch");
        }

        modelAndView.addObject("form", form);
        modelAndView.addObject("weatherList", weatherList);
        modelAndView.setViewName("weatherSearch");
        return modelAndView;
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
        ModelAndView modelAndView = new ModelAndView();

        // 項目精査を行う
        List<String> errorList = weatherLogic.validateFormForSearchHard(form);
        if (!errorList.isEmpty()) {
            modelAndView.addObject("errorList", errorList);
            modelAndView.addObject("form", form);
            modelAndView.setViewName("weatherSearchHard");
            return modelAndView;
        }

        errorList = weatherLogic.validateBetweenItemForSearchHard(form);
        if (!errorList.isEmpty()) {
            modelAndView.addObject("errorList", errorList);
            modelAndView.addObject("form", form);
            modelAndView.setViewName("weatherSearchHard");
            return modelAndView;
        }

        String selectSql = weatherLogic.createSqlForSearchHard(form);
        Map<String, String> condition = weatherLogic.createConditionForSearchHard(form);
        List<Weather> weatherList = weatherDao.findBySql(selectSql, condition);

        modelAndView.addObject("form", form);
        modelAndView.addObject("weatherList", weatherList);
        modelAndView.addObject("searchCount", weatherList.size());
        if (weatherList.isEmpty()) {
            modelAndView.addObject("noResult", Boolean.TRUE);
        }
        modelAndView.setViewName("weatherSearchHard");
        return modelAndView;
    }

    /**
     * 天気の統計処理を行う。
     *
     * @param form フォーム
     * @param bindingResult バリデーション結果
     * @return ModelAndView
     */
    @RequestMapping(value = "weatherStatistics/analysis", method = RequestMethod.POST)
    public ModelAndView statistics(@Validated WeatherStatisticsForm form, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        // 項目精査を行う
        List<String> errorList = weatherLogic.validateFormForStatistics(form);
        if (!errorList.isEmpty()) {
            modelAndView.addObject("errorList", errorList);
            modelAndView.addObject("form", form);
            modelAndView.setViewName("weatherStatistics");
            return modelAndView;
        }

        // 過去の天気の統計を検索する
        List<Weather> pastWeatherList = weatherLogic.createPastWeatherList(form);
        if (pastWeatherList.isEmpty()) {
            errorList.add("データが存在しませんでした。");
            modelAndView.addObject("errorList", errorList);
            modelAndView.addObject("form", form);
            modelAndView.setViewName("weatherStatistics");
            return modelAndView;
        }

        WeatherDto statisticsWeather = weatherLogic.createWeatherDto(form, pastWeatherList);
        modelAndView.addObject("form", form);
        modelAndView.addObject("statisticsWeather", statisticsWeather);
        modelAndView.setViewName("weatherStatistics");
        return modelAndView;
    }

    /**
     * CSVファイルを読み込んでデータをテーブルに登録する。
     *
     * @param form フォーム
     * @param bindingResult バリデーション結果
     * @return ModelAndView
     */
    @Transactional
    @RequestMapping(value = "csvRegister/insert", method = RequestMethod.POST)
    public ModelAndView csvRegister(@Validated CsvRegisterForm form, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        // 項目精査
        List<String> errorList = weatherLogic.validateFormForCsvRead(form);
        if (!errorList.isEmpty()) {
            modelAndView.addObject("filePath", form.getFilePath());
            modelAndView.addObject("errorList", errorList);
            modelAndView.setViewName("csvRegister");
            return modelAndView;
        }

        List<String> csvDataList = new ArrayList<String>();
        try {
            csvDataList = weatherLogic.createCsvDataList(form);
        } catch (Exception e) {
            errorList.add(e.getMessage());
            modelAndView.addObject("filePath", form.getFilePath());
            modelAndView.addObject("errorList", errorList);
            modelAndView.setViewName("csvRegister");
            return modelAndView;
        }

        if (csvDataList.isEmpty()) {
            errorList.add("登録するデータが存在しません。");
            modelAndView.addObject("filePath", form.getFilePath());
            modelAndView.addObject("errorList", errorList);
            modelAndView.setViewName("csvRegister");
            return modelAndView;
        }
        weatherLogic.insert(csvDataList);

        modelAndView.setViewName("complete");
        return modelAndView;
    }
}
