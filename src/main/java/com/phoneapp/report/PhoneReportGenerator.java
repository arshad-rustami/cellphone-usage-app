package com.phoneapp.report;

import com.phoneapp.dao.CellPhoneDao;
import com.phoneapp.dao.CellPhoneUsageDao;
import com.phoneapp.model.*;
import com.phoneapp.util.DateUtil;
import com.phoneapp.util.DocumentPrinter;
import com.phoneapp.util.PdfGenerator;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * The PhoneReportGenerator program implements functionality for generating phone usage report.
 *
 * @author  Arshad Ali
 * @version 1.0
 * @since   2021-Sep-17
 */
public class PhoneReportGenerator {

    private CellPhoneDao cellPhoneDao;
    private CellPhoneUsageDao cellPhoneUsageDao;
    private PdfGenerator pdfGenerator;

    final static Logger logger = Logger.getLogger(PhoneReportGenerator.class);

    public PhoneReportGenerator(){
        cellPhoneDao = new CellPhoneDao();
        cellPhoneUsageDao = new CellPhoneUsageDao();
        pdfGenerator = new PdfGenerator();
    }

    /**
     *  This method is used for generating phone usage report and then call
     *  utility for printing of report.
     *
     * @param inputYear
     * @return String This will be result of the report generation
     */
    public boolean generateReport (int inputYear){

        logger.info("Generating report for year: " + inputYear);

        PhoneUsageReport usageReport = new PhoneUsageReport();

        List<CellPhoneMonthlyUsage> phoneMonthlyUsageList = cellPhoneUsageDao.searchYearlyData(inputYear);

        if (phoneMonthlyUsageList != null && !phoneMonthlyUsageList.isEmpty()){
            usageReport.setRunDate(new Date().toString());
            List<CellPhoneMonthlyUsage> phonesFiltered = setTotalNumberOfPhone(phoneMonthlyUsageList, usageReport);

            // Set Total and Average Minutes.
            setTotalMinutes(phoneMonthlyUsageList, usageReport);
            setAverageMinutes(phoneMonthlyUsageList, usageReport);

            // Set Total and Average Data.
            setTotalData(phoneMonthlyUsageList, usageReport);
            setAverageData(phoneMonthlyUsageList, usageReport);

            setCellPhonesData(phoneMonthlyUsageList, usageReport, phonesFiltered);

            // Generate PDF Report
            String fileName = inputYear + "_" + System.currentTimeMillis() + ".pdf";
            String generatedFile = pdfGenerator.createPdfDocumentReport(fileName, usageReport);

            logger.info("Mobile usage report successfully Generated.");
            // Print Report
            return DocumentPrinter.printDocument(generatedFile);
        }

        return false;
    }

    /**
     * This method calculate the total number of phones
     *
     * @param phoneMonthlyUsageList
     * @param usageReport
     * @return List The list of phone who usage is available in csv.
     */
    private List<CellPhoneMonthlyUsage> setTotalNumberOfPhone(List<CellPhoneMonthlyUsage> phoneMonthlyUsageList, PhoneUsageReport usageReport){
        List<CellPhoneMonthlyUsage> phonesFiltered = phoneMonthlyUsageList.stream()
                .filter(distinctByKey(phone -> phone.getEmployeeId()))
                .collect(Collectors.toList());
        usageReport.setNumberOfPhones(phonesFiltered.size());
        return phonesFiltered;
    }

    /**
     * This method calculate the total minutes of all phones.
     * The total minutes are then set in usageReport
     *
     * @param phoneMonthlyUsageList
     * @param usageReport
     */
    private void setTotalMinutes(List<CellPhoneMonthlyUsage> phoneMonthlyUsageList, PhoneUsageReport usageReport){
        Integer totalMinutes = phoneMonthlyUsageList.stream()
                .map((phoneUsage->Integer.parseInt(phoneUsage.getTotalMinutes())))
                .reduce(0, Integer::sum);
        usageReport.setTotalMinutes(totalMinutes);
    }

    /**
     * This method calculate the total data usage of all phones.
     * The total data usage are then set in usageReport
     *
     * @param phoneMonthlyUsageList
     * @param usageReport
     */
    private void setTotalData(List<CellPhoneMonthlyUsage> phoneMonthlyUsageList, PhoneUsageReport usageReport){
        Double totalData = phoneMonthlyUsageList.stream()
                .map((phoneUsage->Double.parseDouble(phoneUsage.getTotalData())))
                .reduce(0.0, Double::sum);
        usageReport.setTotalData(totalData);
    }

    /**
     * This method calculate the average minutes of all phones.
     * The total average minutes are then set in usageReport
     *
     * @param phoneMonthlyUsageList
     * @param usageReport
     */
    private void setAverageMinutes(List<CellPhoneMonthlyUsage> phoneMonthlyUsageList, PhoneUsageReport usageReport){
        OptionalDouble averageMinutes = phoneMonthlyUsageList.stream()
                .map((phoneUsage->Integer.parseInt(phoneUsage.getTotalMinutes())))
                .mapToInt((x) -> x)
                .average();
        usageReport.setAverageMinutes(averageMinutes.getAsDouble());
    }

    /**
     * This method calculate the average data usage of all phones.
     * The average data usage are then set in usageReport
     *
     * @param phoneMonthlyUsageList
     * @param usageReport
     */
    private void setAverageData(List<CellPhoneMonthlyUsage> phoneMonthlyUsageList, PhoneUsageReport usageReport){
        OptionalDouble averageData = phoneMonthlyUsageList.stream()
                .map((phoneUsage->Double.parseDouble(phoneUsage.getTotalData())))
                .mapToDouble((x) -> x)
                .average();
        usageReport.setAverageData(averageData.getAsDouble());
    }


    /**
     * This method find the phones list whom data is available.
     * For each phone the monthly usage is then calculated
     *
     * @param phoneMonthlyUsageList
     * @param usageReport
     * @param phonesFiltered
     */
    private void setCellPhonesData (List<CellPhoneMonthlyUsage> phoneMonthlyUsageList, PhoneUsageReport usageReport, List<CellPhoneMonthlyUsage> phonesFiltered){
        List<CellPhone> cellPhoneList = cellPhoneDao.loadCellPhonesData();

        List<CellPhoneUsageDetail> usageDetailList = new ArrayList<>();

        phonesFiltered.forEach( (phoneUsage) -> {
            CellPhone cellPhone = cellPhoneList.stream()
                    .filter(phone -> phone.getEmployeeId().equals(phoneUsage.getEmployeeId()))
                    .findAny()
                    .orElse(null);

            CellPhoneUsageDetail cellPhoneUsageDetail = new CellPhoneUsageDetail(Integer.parseInt(cellPhone.getEmployeeId()), cellPhone.getEmployeeName(), cellPhone.getModel(), cellPhone.getPurchaseDate());
            Map<String, MonthUsage> monthlyData = getMonthlyDataForUser(phoneMonthlyUsageList, cellPhone.getEmployeeId());
            cellPhoneUsageDetail.setPhoneUsage(monthlyData);
            usageDetailList.add(cellPhoneUsageDetail);
        });

        usageReport.setCellPhonesDetail(usageDetailList);
    }

    /**
     * This method calculate the minutes and data usage for each month.
     * The usage is then set in usageReport
     *
     * @param phoneMonthlyUsageList
     * @param employeeId
     * @return
     */
    private Map<String, MonthUsage> getMonthlyDataForUser(List<CellPhoneMonthlyUsage> phoneMonthlyUsageList, String employeeId){
        Map<String, MonthUsage> monthlyData = new HashMap<>();

        phoneMonthlyUsageList.forEach( (phoneUsage) -> {
            if (phoneUsage.getEmployeeId().equals(employeeId)){
                MonthUsage monthUsage = new MonthUsage(Integer.parseInt(phoneUsage.getTotalMinutes()), Double.parseDouble(phoneUsage.getTotalData()));
                if (monthlyData.get(DateUtil.getMonthFromDate(phoneUsage.getDate())) == null){
                    monthlyData.put(DateUtil.getMonthFromDate(phoneUsage.getDate()), monthUsage);
                } else {
                    int monthlyUserMinutes = monthUsage.getMinutesUsage() + monthlyData.get(DateUtil.getMonthFromDate(phoneUsage.getDate())).getMinutesUsage();
                    double monthlyUserData = monthUsage.getDataUsage() + monthlyData.get(DateUtil.getMonthFromDate(phoneUsage.getDate())).getDataUsage();

                    monthlyData.get(DateUtil.getMonthFromDate(phoneUsage.getDate())).setDataUsage(monthlyUserData);
                    monthlyData.get(DateUtil.getMonthFromDate(phoneUsage.getDate())).setMinutesUsage(monthlyUserMinutes);
                }
            }
        });

        return monthlyData;
    }


    /**
     * This method finds the unique entries in List
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
