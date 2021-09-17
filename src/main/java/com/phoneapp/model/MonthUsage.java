package com.phoneapp.model;


/**
 * DTO class for storing data minutes and data usage.
 *
 * @author  Arshad Ali
 * @version 1.0
 * @since   2021-Sep-17
 */
public class MonthUsage {
    private int minutesUsage;
    private double dataUsage;

    public MonthUsage() {
    }

    public MonthUsage(int minutesUsage, double dataUsage) {
        this.minutesUsage = minutesUsage;
        this.dataUsage = dataUsage;
    }

    public int getMinutesUsage() {
        return minutesUsage;
    }

    public void setMinutesUsage(int minutesUsage) {
        this.minutesUsage = minutesUsage;
    }

    public double getDataUsage() {
        return dataUsage;
    }

    public void setDataUsage(double dataUsage) {
        this.dataUsage = dataUsage;
    }

}
