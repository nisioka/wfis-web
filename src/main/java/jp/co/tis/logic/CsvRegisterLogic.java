package jp.co.tis.logic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.tis.exception.FileFormatException;
import jp.co.tis.exception.SystemException;
import jp.co.tis.form.CsvRegisterForm;
import jp.co.tis.model.WeatherDao;
import jp.co.tis.util.CsvReaderImpl;

/**
 * CSV登録Logicクラス。<br/>
 * コントローラーに直接メソッド切り出しを行うと行数が膨れるため、<br/>
 * 業務ロジック部分はロジッククラスに切り出す。<br/>
 * JUnitテストをしやすくするための目的もある。
 *
 * @author Saito Takuma
 * @since 1.0
 */
@Component
public class CsvRegisterLogic {

    /** DB操作DAO */
    @Autowired
    private WeatherDao weatherDao;

    /**
     * 入力項目をバリデーションする。
     *
     * @param form フォーム
     * @return エラーリスト
     */
    public List<String> validateForm(CsvRegisterForm form) {
        List<String> errorList = new ArrayList<String>();

        String filePath = form.getFilePath();
        if (StringUtils.isEmpty(filePath)) {
            errorList.add("ファイルパスは必ず入力してください。");
        } else if (!StringUtils.endsWith(filePath, ".csv")) {
            errorList.add("ファイルの拡張子はcsv形式にしてください。");
        }

        return errorList;
    }

    /**
     * CSVファイルの読み込み処理を行う。
     *
     * @param form フォーム
     * @throws Exception CSV読込中に何らかの例外が発生した場合にスローされる
     * @return 読み込んだCSVファイルのデータ部。
     */
    public List<String> createCsvDataList(CsvRegisterForm form) throws Exception {
        // CSVファイル読み込み処理
        CsvReaderImpl csvReaderImpl = new CsvReaderImpl(form.getFilePath());
        try {
            csvReaderImpl.open();
        } catch (FileNotFoundException | FileFormatException e) {
            throw new Exception(e.getMessage());
        }

        int rowCount = 0;
        List<String> csvDataList = new ArrayList<String>();
        try {
            while (true) {
                Map<String, String> row = csvReaderImpl.readLine();
                // 読み込む行がなくなった場合
                if (row == null) {
                    break;
                }

                StringBuilder csvData = new StringBuilder("");
                for (String key : row.keySet()) {
                    csvData.append("'").append(row.get(key)).append("',");
                }
                csvDataList.add(StringUtils.removeEnd(csvData.toString(), ","));
                rowCount++;
            }
        } catch (IOException e) {
            csvReaderImpl.close();
            throw new SystemException("システム例外が発生しました。", e);
        } catch (FileFormatException e) {
            // ヘッダーの行数も考慮するため
            rowCount += 2;
            csvReaderImpl.close();
            throw new Exception(rowCount + "行目 ：" + e.getMessage());
        }
        csvReaderImpl.close();
        return csvDataList;
    }

    /**
     * CSVファイルを読み込んでデータをテーブルに登録する。
     *
     * @param csvDataList 登録するCSVファイルのデータ部
     * @return 登録件数
     */
    public int insert(List<String> csvDataList) {
        // 一行ずつDBに登録
        for (String csvData : csvDataList) {
            StringBuilder insertSql = new StringBuilder("");
            insertSql.append("INSERT INTO WEATHER (WEATHER_DATE, PLACE, WEATHER, MAX_TEMPERATURE, MIN_TEMPERATURE) VALUES (");
            insertSql.append(csvData).append(")");
            weatherDao.insert(insertSql.toString());
        }
        return csvDataList.size();
    }

}
