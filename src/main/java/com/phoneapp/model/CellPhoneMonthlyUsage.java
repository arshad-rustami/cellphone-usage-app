package com.phoneapp.model;

import com.opencsv.bean.CsvBindByName;

/**
 * Model class for mapping data from CellphoneUsageByMonth csv.
 *
 * @author  Arshad Ali
 * @version 1.0
 * @since   2021-Sep-17
 */
public class CellPhoneMonthlyUsage {

    @CsvBindByName(column = "emplyeeId")
    private String employeeId;

    @CsvBindByName(column = "date")
    private String date;

    @CsvBindByName(column = "totalMinutes")
    private String totalMinutes;

    @CsvBindByName(column = "totalData")
    private String totalData;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(String totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public String getTotalData() {
        return totalData;
    }

    public void setTotalData(String totalData) {
        this.totalData = totalData;
    }

    @Override
    public String toString() {
        return "CellPhoneMonthlyUsage{" +
                "employeeId='" + employeeId + '\'' +
                ", date='" + date + '\'' +
                ", totalMinutes='" + totalMinutes + '\'' +
                ", totalData='" + totalData + '\'' +
                '}';
    }
}
