package com.phoneapp.util;

import org.apache.log4j.Logger;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Sides;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * The DocumentPrinter utility program implements functionality for printing pdf documents.
 *
 * @author  Arshad Ali
 * @version 1.0
 * @since   2021-Sep-17
 */
public class DocumentPrinter {

    final static Logger logger = Logger.getLogger(DocumentPrinter.class);

    /**
     * This method search the local printer configured in system and print document
     * @param fileName
     * @return String represents success or error
     */
    public static boolean printDocument(String fileName) {

        FileInputStream textStream = null;
        try {
            textStream = new FileInputStream(fileName);
        } catch (FileNotFoundException exception) {
            logger.error(exception);
        }
        if (textStream == null) {
            logger.info("No content in PDF file for printing.");
            return false;
        }

        // Set the document type
        DocFlavor myFormat = DocFlavor.INPUT_STREAM.PDF;

        // Create a Doc
        Doc myDoc = new SimpleDoc(textStream, myFormat, null);

        // Build a set of attributes
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(new Copies(1));
        printRequestAttributeSet.add(Sides.ONE_SIDED);

        // discover the printers that can print the format according to the
        PrintService[] services =
                PrintServiceLookup.lookupPrintServices(myFormat, printRequestAttributeSet);

        // Create a print job from one of the print services
        if (services.length > 0) {
            DocPrintJob job = services[0].createPrintJob();
            try {
                job.print(myDoc, printRequestAttributeSet);
                logger.info("Report successfully printed");
                return true;
            } catch (PrintException printException) {
                logger.error(printException);
            }
        } else {
            logger.info("No printer attached to system");
        }

        return false;
    }

}
