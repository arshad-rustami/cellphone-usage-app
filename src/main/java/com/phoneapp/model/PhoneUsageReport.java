package com.phoneapp.model;

import java.util.List;

/**
 * DTO class for storing usage report of all phones.
 *
 * @author  Arshad Ali
 * @version 1.0
 * @since   2021-Sep-17
 */
public class PhoneUsageReport {
    private String runDate;
    private int numberOfPhones;
    private int totalMinutes;
    private double averageMinutes;
    private double totalData;
    private double averageData;

    List<CellPhoneUsageDetail> cellPhonesDetail ;

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public int getNumberOfPhones() {
        return numberOfPhones;
    }

    public void setNumberOfPhones(int numberOfPhones) {
        this.numberOfPhones = numberOfPhones;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public double getAverageMinutes() {
        return averageMinutes;
    }

    public void setAverageMinutes(double averageMinutes) {
        this.averageMinutes = averageMinutes;
    }

    public double getTotalData() {
        return totalData;
    }

    public void setTotalData(double totalData) {
        this.totalData = totalData;
    }

    public double getAverageData() {
        return averageData;
    }

    public void setAverageData(double averageData) {
        this.averageData = averageData;
    }

    public List<CellPhoneUsageDetail> getCellPhonesDetail() {
        return cellPhonesDetail;
    }

    public void setCellPhonesDetail(List<CellPhoneUsageDetail> cellPhonesDetail) {
        this.cellPhonesDetail = cellPhonesDetail;
    }


}
