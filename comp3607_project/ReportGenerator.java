import java.io.*;
import java.util.zip.*;
import java.util.ArrayList;
import java.util.List;

public interface ReportGenerator {
    public void generateReport(List<ReportContent> content, String outputFile);
}



/*
 * Generates a PDF report based on the list of EvaluationResult objects. 
 * The report includes each test's pass/fail status and provides corrective 
 * feedback where necessary.
*/