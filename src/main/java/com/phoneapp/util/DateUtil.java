package com.phoneapp.util;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

/**
 * The DocumentPrinter utility program implements functionality related to date formatting.
 *
 * @author  Arshad Ali
 * @version 1.0
 * @since   2021-Sep-17
 */
public class DateUtil {

    final static Logger logger = Logger.getLogger(DateUtil.class);

    /**
     * This method retrieve year from provided date
     * @param inputDate
     * @return int year
     */
    public static int getYearFromDate (String inputDate) {

        try {
            Date date = DateUtils.parseDate(inputDate,
                    new String[] { "yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy" });

            LocalDate localDate = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            return  localDate.getYear();
        } catch (Exception e){
            logger.error(e);
        }

        return 0;
    }

    /**
     * This method retrieve month name from date
     * @param inputDate
     * @return String name of the month
     */
    public static String getMonthFromDate (String inputDate) {

        try {
            Date date = DateUtils.parseDate(inputDate,
                    new String[] { "yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy" });

            LocalDate localDate = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            return  localDate.getMonth().name();

        } catch (Exception e){
            logger.error(e);
        }

        return null;
    }

    public static boolean validateInputYear(String year) {
        if(year.matches("[0-9]{4}")) {
            return true;
        } else {
            return false;
        }
    }
}
