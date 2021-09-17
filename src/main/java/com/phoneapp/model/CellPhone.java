package com.phoneapp.model;

import com.opencsv.bean.CsvBindByName;

/**
 * Model class for mapping data from Cellphone csv.
 *
 * @author  Arshad Ali
 * @version 1.0
 * @since   2021-Sep-17
 */
public class CellPhone {

    @CsvBindByName(column = "employeeId")
    private String employeeId;

    @CsvBindByName(column = "employeeName")
    private String employeeName;

    @CsvBindByName(column = "purchaseDate")
    private String purchaseDate;

    @CsvBindByName(column = "model")
    private String model;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "CellPhone{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
