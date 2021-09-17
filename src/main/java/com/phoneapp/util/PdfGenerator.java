package com.phoneapp.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.phoneapp.constants.ApplicationConstants;
import com.phoneapp.model.CellPhoneUsageDetail;
import com.phoneapp.model.MonthUsage;
import com.phoneapp.model.PhoneUsageReport;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The PdfGenerator utility program implements functionality for generating PDF documents.
 *
 * @author  Arshad Ali
 * @version 1.0
 * @since   2021-Sep-17
 */
public class PdfGenerator {

    final static Logger logger = Logger.getLogger(PdfGenerator.class);

    private final Font DOCUMENT_TITLE_FONT_STYLE =  FontFactory.getFont(FontFactory.TIMES, 16f, Font.BOLD, BaseColor.BLUE);
    private final Font LABEL_FONT_STYLE =  FontFactory.getFont(FontFactory.TIMES, 12f, Font.BOLD, BaseColor.DARK_GRAY);
    private final Font NOTE_FONT_STYLE =  FontFactory.getFont(FontFactory.TIMES, 10f, Font.NORMAL, BaseColor.RED);
    private static DecimalFormat df = new DecimalFormat("0.00");

    /**
     * This method generate PDF file for phone usage data.
     *
     * @param fileName
     * @param usageReport
     * @return String representing success or failure
     */
    public String createPdfDocumentReport(String fileName, PhoneUsageReport usageReport) {

        try {

            logger.info("Creating PDF document : " + fileName);

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(ApplicationConstants.PDF_REPORTS_DIRECTORY + fileName));

            document.open();

            Paragraph title = new Paragraph("Phone Usage Report", DOCUMENT_TITLE_FONT_STYLE);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            Paragraph runDate = new Paragraph("Report Run Date:    " +  new Date().toString(), LABEL_FONT_STYLE);
            document.add(runDate);

            Paragraph noOfPhones = new Paragraph("Number of Phones:   " +  usageReport.getNumberOfPhones(), LABEL_FONT_STYLE);
            document.add(noOfPhones);

            Paragraph totalMinutes = new Paragraph("Total Minutes:      " +  usageReport.getTotalMinutes(), LABEL_FONT_STYLE);
            document.add(totalMinutes);

            Paragraph totalData = new Paragraph("Total Data:         " +  df.format(usageReport.getTotalData()), LABEL_FONT_STYLE);
            document.add(totalData);

            Paragraph averageMinutes = new Paragraph("Average Minutes:    " +  df.format(usageReport.getAverageMinutes()), LABEL_FONT_STYLE);
            document.add(averageMinutes);

            Paragraph averageData = new Paragraph("Average Data:       " +  df.format(usageReport.getAverageData()), LABEL_FONT_STYLE);
            averageData.setSpacingAfter(30);
            document.add(averageData);

            Paragraph note = new Paragraph("Note: Each column contains Minutes and data for a specified  month (Minutes - DataUsage) ", NOTE_FONT_STYLE);
            note.setSpacingAfter(20);
            document.add(note);

            usageReport.getCellPhonesDetail().forEach( (cellphoneDetail) -> {
                try {
                    Paragraph employeeDetail = setDetailSection(cellphoneDetail);
                    document.add(employeeDetail);
                } catch (Exception e){
                    logger.error(e);
                }
            });

            document.close();

            logger.info("PDF report successfully created.");

        } catch (Exception e) {
            logger.error(e);
        }

        return ApplicationConstants.PDF_REPORTS_DIRECTORY + fileName;
    }

    /**
     * This method formulate data for a phone with its monthly usage.
     *
     * @param cellPhoneUsageDetails
     * @return Paragraph having cell phone usage monthly detail
     */
    private Paragraph setDetailSection(CellPhoneUsageDetail cellPhoneUsageDetails) {
        Paragraph employeeDetail = new Paragraph();
        employeeDetail.setSpacingAfter(30);
        employeeDetail.add(new Paragraph("Employee Id: " + cellPhoneUsageDetails.getEmployeeId()));
        employeeDetail.add(new Paragraph("Employee Name: " + cellPhoneUsageDetails.getEmployeeName()));
        employeeDetail.add(new Paragraph("Model: " + cellPhoneUsageDetails.getModel()));
        employeeDetail.add(new Paragraph("Purchase Date: " + cellPhoneUsageDetails.getPurchaseDate()));

        PdfPTable table = new PdfPTable(cellPhoneUsageDetails.getPhoneUsage().keySet().size());
        table.setSpacingBefore(10);
        table.setWidthPercentage(100);

        Map<String, MonthUsage> phoneUsage = cellPhoneUsageDetails.getPhoneUsage();

        // Table Header
        for (Map.Entry<String, MonthUsage> pair : phoneUsage.entrySet()) {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(pair.getKey()));
            table.addCell(header);
        }

        for (Map.Entry<String, MonthUsage> pair : phoneUsage.entrySet()) {
            table.addCell(pair.getValue().getMinutesUsage() + " - " + df.format(pair.getValue().getDataUsage()));
        }

        employeeDetail.add(table);

        return employeeDetail;
    }


}
