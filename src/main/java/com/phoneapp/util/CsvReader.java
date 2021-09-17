package com.phoneapp.util;

import com.opencsv.bean.CsvToBeanBuilder;
import com.phoneapp.constants.ApplicationConstants;
import com.phoneapp.model.CellPhone;
import com.phoneapp.model.CellPhoneMonthlyUsage;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * The CsvReader utility program implements functionality for csv files.
 *
 * @author  Arshad Ali
 * @version 1.0
 * @since   2021-Sep-17
 */
public class CsvReader {

    final static Logger logger = Logger.getLogger(CsvReader.class);

    /**
     * This method read data from Cellphone.csv
     * @return List containing all cellphones data
     */
    public static List<CellPhone> getCellPhoneDataFromCsv() {
        try {
            return new CsvToBeanBuilder(new FileReader(ApplicationConstants.CSV_FILES_DIRECTORY + ApplicationConstants.CELLPHONE_DATABASE_FILE))
                    .withType(CellPhone.class)
                    .build()
                    .parse();

        } catch (FileNotFoundException e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * This method read data from CellPhoneUsageByMonth.csv
     * @return List containing all cellphones monthly usage data
     */
    public static List<CellPhoneMonthlyUsage> getCellPhonesUsageDataFromCsv() {
        try {
            return new CsvToBeanBuilder(new FileReader(ApplicationConstants.CSV_FILES_DIRECTORY + ApplicationConstants.CELLPHONE_USAGE_DATABASE_FILE))
                    .withType(CellPhoneMonthlyUsage.class)
                    .build()
                    .parse();

        } catch (FileNotFoundException e) {
            logger.error(e);
        }

        return null;
    }

}
