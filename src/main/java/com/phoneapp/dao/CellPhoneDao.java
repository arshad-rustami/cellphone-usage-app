package com.phoneapp.dao;

import com.phoneapp.model.CellPhone;
import com.phoneapp.util.CsvReader;

import java.util.List;

/**
 * The CellPhoneDao program implements functionality for loading data from CellPhone.csv.
 *
 * @author  Arshad Ali
 * @version 1.0
 * @since   2021-Sep-17
 */
public class CellPhoneDao {

    private CsvReader csvReader;

    public  CellPhoneDao (){
        csvReader = new CsvReader();
    }

    /**
     * This method load data from CellPhone.csv
     * @return List containing all cellphones data
     */
    public List<CellPhone> loadCellPhonesData(){
        List<CellPhone> phoneMonthlyUsageList = csvReader.getCellPhoneDataFromCsv();
        return phoneMonthlyUsageList;
    }

}
