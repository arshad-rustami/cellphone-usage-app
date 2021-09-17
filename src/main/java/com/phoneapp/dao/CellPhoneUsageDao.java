package com.phoneapp.dao;

import com.phoneapp.model.CellPhoneMonthlyUsage;
import com.phoneapp.util.CsvReader;
import com.phoneapp.util.DateUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The CellPhoneUsageDao program implements functionality for loading data from CellPhoneUsageByMonth.csv.
 *
 * @author  Arshad Ali
 * @version 1.0
 * @since   2021-Sep-17
 */
public class CellPhoneUsageDao {

    /**
     * This method load data from CellPhoneUsageByMonth.csv
     * @return List containing all cellphones monthly data
     */
    public List<CellPhoneMonthlyUsage> loadCellPhoneUsageData(){
        List<CellPhoneMonthlyUsage> phoneMonthlyUsageList = CsvReader.getCellPhonesUsageDataFromCsv();
        //phoneMonthlyUsageList.forEach(System.out::println);
        return phoneMonthlyUsageList;
    }


    /**
     * This method find the data for a specific year input by user
     *
     * @param year
     * @return List of months usage data for year
     */
    public List<CellPhoneMonthlyUsage> searchYearlyData(int year){
        List<CellPhoneMonthlyUsage> phoneMonthlyUsageList = CsvReader.getCellPhonesUsageDataFromCsv();

        List<CellPhoneMonthlyUsage> filterListForInputYear = phoneMonthlyUsageList
                .stream()
                .filter(phoneMonthlyUsage -> DateUtil.getYearFromDate(phoneMonthlyUsage.getDate()) == year)
                .collect(Collectors.toList());

        return filterListForInputYear;
    }

}
