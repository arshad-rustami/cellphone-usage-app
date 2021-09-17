package com.phoneapp.model;

import java.util.Map;
/**
 * DTO class for storing data for Cellphone.
 *
 * @author  Arshad Ali
 * @version 1.0
 * @since   2021-Sep-17
 */
public class CellPhoneUsageDetail {

    private int employeeId;
    private String employeeName;
    private String model;
    private String purchaseDate;
    private Map<String, MonthUsage> phoneUsage;
    private Map<String, Integer> dataUsage;

    public CellPhoneUsageDetail() {
    }

    public CellPhoneUsageDetail(int employeeId, String employeeName, String model, String purchaseDate) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.model = model;
        this.purchaseDate = purchaseDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Map<String, MonthUsage> getPhoneUsage() {
        return phoneUsage;
    }

    public void setPhoneUsage(Map<String, MonthUsage> phoneUsage) {
        this.phoneUsage = phoneUsage;
    }

    public Map<String, Integer> getDataUsage() {
        return dataUsage;
    }

    public void setDataUsage(Map<String, Integer> dataUsage) {
        this.dataUsage = dataUsage;
    }
}
