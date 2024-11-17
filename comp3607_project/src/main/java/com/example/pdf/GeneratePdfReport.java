package com.example.pdf;

import com.example.evaluation.TestResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class GeneratePdfReport {
    private static final Logger LOGGER = Logger.getLogger(GeneratePdfReport.class.getName());

    public void generatePdfReport(List<TestResult> testResults, String studentId, File extractedFolder) {
        Document document = new Document();

        // Create the PDF file in the temporary folder
        String tempDir = System.getProperty("java.io.tmpdir");
        File pdfFile = new File(tempDir, "report.pdf");
        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
            document.add(new Paragraph("Test Results for Student " + studentId));
            document.add(new Paragraph("---------------------------------------------------"));
            for (TestResult testResult : testResults) {
                document.add(new Paragraph("File: " + testResult.getJavaFile().getName()));
                document.add(new Paragraph("Methods Present: " + testResult.isMethodsPresent()));
                document.add(new Paragraph("Attributes Present: " + testResult.isAttributesPresent()));
                if (!testResult.isMethodsPresent() || !testResult.isAttributesPresent()) {
                    document.add(new Paragraph(
                            "Corrective Feedback: Please review your code to ensure all required methods and attributes are present."));
                }
                document.add(new Paragraph("---------------------------------------------------"));
            }
        } catch (DocumentException e) {
            LOGGER.log(Level.SEVERE, "Error generating PDF report", e);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error generating PDF report", e);
        } finally {
            document.close();
        }
    }
}
