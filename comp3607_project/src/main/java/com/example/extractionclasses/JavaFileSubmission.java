package com.example.extractionclasses;

//Leaf Class
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.logging.Logger;
import com.example.evaluation.Evaluator;
import com.example.evaluation.RequiredClassInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.example.evaluation.TestResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.logging.Level;
import java.nio.file.Paths;

public class JavaFileSubmission implements SubmissionComponent {

    private String name;
    private File file;
    private Map<String, RequiredClassInfo> requiredClassInfo = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(JavaFileSubmission.class.getName());

    public JavaFileSubmission(String name, Path path) {
        this.name = name;
        this.file = path.toFile();
    }

    public File getFile() {
        return file;
    }

    @Override
    public String getName() {
        return name;
    }

    public void addRequiredClassInfo(String className, List<String> requiredMethods, List<String> requiredAttributes) {
        RequiredClassInfo info = new RequiredClassInfo(requiredMethods, requiredAttributes);
        requiredClassInfo.put(className, info);
    }

    private String getStudentIdFromFolderName() {
        // Assuming the folder name is in the format "studentId-assignmentName"
        String folderName = file.getParentFile().getName();
        return folderName.split("-")[0];
    }

    public void generatePdfReport(List<TestResult> testResults, String studentId, File extractedFolder) {
        Document document = new Document();

        // Create the PDF file in the temporary folder
        String tempDir = System.getProperty("java.io.tmpdir");
        Path pdfPath = Paths.get(tempDir, "report.pdf");
        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath.toFile()));
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
        } catch (DocumentException | FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error generating PDF report", e);
        } finally {
            document.close();
        }
    }

    @Override
    public void runTests() {
        // Create an instance of the Evaluator class
        Evaluator evaluator = new Evaluator();

        // Add the required class info
        addRequiredClassInfo("ChatBot",
                Arrays.asList("getChatBotName", "getNumResponsesGenerated", "getTotalNumResponsesGenerated",
                        "getTotalNumMessagesRemaining", "limitReached", "generateResponse", "toString", "interact"),
                Arrays.asList("chatBotName", "numResponsesGenerated", "messageLimit", "messageNumber"));
        addRequiredClassInfo("ChatBotPlatform",
                Arrays.asList("addChatBot", "getChatBotList", "interactWithBot", "prompt"),
                Arrays.asList("bots"));
        addRequiredClassInfo("ChatBotGenerator",
                Arrays.asList("generateChatBot"),
                Arrays.asList(""));
        addRequiredClassInfo("ChatBotSimulation",
                Arrays.asList(""),
                Arrays.asList(""));

        // Evaluate the Java file
        TestResult testResult = evaluator.evaluateJavaFile(this.file, requiredClassInfo);

        // Generate PDF report
        List<TestResult> testResults = new ArrayList<>();
        testResults.add(testResult);
        String studentId = getStudentIdFromFolderName();
        generatePdfReport(testResults, studentId, file.getParentFile());

        // Do something with the testResult
        LOGGER.info(testResult.getResultSummary());
    }

}
