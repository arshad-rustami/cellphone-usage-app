package com.phoneapp;

import com.phoneapp.report.PhoneReportGenerator;
import com.phoneapp.util.DateUtil;
import java.util.Scanner;

/**
 * The PhoneUsageAppMain is main class to run application.
 * Based on user input it generate and print phone usage report.
 *
 * @author  Arshad Ali
 * @version 1.0
 * @since   2021-Sep-17
 */
public class PhoneUsageAppMain {

    /**
     *  Main Method of application for generating report.
     * @param arg
     */
    public static void main(String arg[]){
        generateDataUsageReport();
    }


    /**
     * This method input the year from user and display final result of generation.
     */
    private static void generateDataUsageReport(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter year for mobile usage report: ");
        String year = scanner.next();

        if (DateUtil.validateInputYear(year)){
            PhoneReportGenerator reportGenerator = new PhoneReportGenerator();
            boolean isReportGenerated = reportGenerator.generateReport(Integer.parseInt(year));

            if (isReportGenerated) {
                System.out.println("\nMobile usage report is successfully generated and printed.");
            } else {
                System.out.println("\nWe cannot print/generate report for the provided year. Please try again.");
            }
        } else {
            System.out.println("\nYear input is incorrect. Please try again.");
        }
    }


}
