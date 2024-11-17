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
import com.example.pdf.GeneratePdfReport;
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
        GeneratePdfReport pdfReport = new GeneratePdfReport();
        pdfReport.generatePdfReport(testResults, studentId, extractedFolder);
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
