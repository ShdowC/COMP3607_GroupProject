package com.example.pdf;

import com.example.evaluation.TestResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class GeneratePdfReport {
    private static final Logger LOGGER = Logger.getLogger(GeneratePdfReport.class.getName());

    public void generatePdfReport(List<TestResult> testResults, String studentId, File extractedFolder) {
        Document document = new Document();

        File pdfFile = new File(extractedFolder, "report.pdf");

        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            for (TestResult testResult : testResults) {
                // Add a section to the PDF document for each Java class
                document.add(new Paragraph("Student ID: " + studentId));
                document.add(new Paragraph("Java Class: " + testResult.getJavaFile().getName()));
                document.add(new Paragraph("Result Summary: " + testResult.getResultSummary()));

                // Add any additional test results or details for each Java class
                // ...

                document.add(new Paragraph("---------------------------------------------------"));
            }
        } catch (DocumentException e) {
            LOGGER.log(Level.SEVERE, "Error generating PDF report", e);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error generating PDF report", e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error generating PDF report", e);
        } finally {
            document.close();
        }
    }
}
