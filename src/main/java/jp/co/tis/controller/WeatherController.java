package jp.co.tis.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tis.exception.FileFormatException;
import jp.co.tis.exception.SystemException;
import jp.co.tis.form.WeatherSearchForm;
import jp.co.tis.logic.WeatherLogic;
import jp.co.tis.model.Weather;
import jp.co.tis.model.WeatherDao;
import jp.co.tis.model.WeatherDto;
import jp.co.tis.util.CsvReaderImpl;

import org.apache.commons.lang.StringUtils;
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

    /** 天気予報ロジッククラス */
    @Autowired
    private WeatherLogic weatherLogic;

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
    @RequestMapping(value = "weatherStatistics/statistics", method = RequestMethod.POST)
    public ModelAndView statistics(@Validated WeatherSearchForm form, BindingResult bindingResult) {
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
     * CSVファイルを読み込んで表示する。
     *
     * @param form フォーム
     * @param bindingResult バリデーション結果
     * @return ModelAndView
     */
    @RequestMapping(value = "csvRegister/csvRead", method = RequestMethod.POST)
    public ModelAndView csvRead(@Validated WeatherSearchForm form, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        // 項目精査
        List<String> errorList = weatherLogic.validateFormForCsvRead(form);
        if (!errorList.isEmpty()) {
            modelAndView.addObject("filePath", form.getFilePath());
            modelAndView.addObject("errorList", errorList);
            modelAndView.setViewName("csvRegister");
            return modelAndView;
        }

        // CSVファイル読み込み処理
        CsvReaderImpl csvReaderImpl = new CsvReaderImpl(form.getFilePath());
        try {
            csvReaderImpl.open();
        } catch (FileNotFoundException | FileFormatException e) {
            return weatherLogic.createErrorModelAndView(form, e.getMessage());
        }

        int rowCount = 0;
        String csvData = null;
        List<Map<String, String>> csvReadList = new ArrayList<Map<String, String>>();
        try {
            StringBuilder data = new StringBuilder();
            while (true) {
                Map<String, String> row = csvReaderImpl.readLine();
                // 読み込む行がなくなった場合
                if (row == null) {
                    break;
                }
                csvReadList.add(row);
                for (String key : row.keySet()) {
                    data.append(row.get(key)).append(",");
                }
                rowCount++;
            }
            if (csvReadList.isEmpty()) {
                return weatherLogic.createErrorModelAndView(form, "登録するデータが存在しません。");
            }
            csvData = StringUtils.removeEnd(data.toString(), ",");
        } catch (IOException e) {
            throw new SystemException("システム例外が発生しました。", e);
        } catch (FileFormatException e) {
            // ヘッダーの行数も考慮するため
            rowCount += 2;
            return weatherLogic.createErrorModelAndView(form, rowCount + "行目 ：" + e.getMessage());
        }
        csvReaderImpl.close();

        modelAndView.addObject("csvReadList", csvReadList);
        modelAndView.addObject("csvData", csvData);
        modelAndView.addObject("rowCount", rowCount);
        modelAndView.addObject("filePath", form.getFilePath());
        modelAndView.setViewName("csvRegister");

        return modelAndView;
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
        ModelAndView modelAndView = new ModelAndView();

        // CSV一行ずつのListを作成
        List<String> csvRowList = new ArrayList<String>();
        StringBuilder row = new StringBuilder("");
        int loopCount = 1;
        for (String csvData : form.getCsvDataList()) {
            if (loopCount == 5) {
                loopCount = 0;
                row.append("'").append(csvData).append("'");
                csvRowList.add(row.toString());
                row = new StringBuilder();
            } else {
                row.append("'").append(csvData).append("'").append(",");
            }
            loopCount++;
        }

        // 一行ずつDBに登録
        for (String csvRow : csvRowList) {
            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO WEATHER (WEATHER_DATE, PLACE, WEATHER, MAX_TEMPERATURE, MIN_TEMPERATURE) VALUES (");
            insertSql.append(csvRow).append(")");
            weatherDao.insert(insertSql.toString());
        }

        modelAndView.setViewName("complete");
        return modelAndView;
    }
}
