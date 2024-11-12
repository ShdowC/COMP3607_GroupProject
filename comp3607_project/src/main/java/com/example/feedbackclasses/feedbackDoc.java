package com.example.feedbackclasses;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import com.example.extractionclasses.SubmissionFolder;
import com.example.testclasses.TestObserver;

import junit.framework.TestResult;


public class feedbackDoc implements TestObserver{
 /**
 * The Feedback class provides feedback based on test results.
 * The feedback is retrieved from a file and associated with specific test cases.
 * Feedback is then added to the test results of a submission.
 */
  
 private List<feedbackEntry> feedbackList;
 private static String mainDirectory = "src/main/java/comp3607project";

 public feedbackDoc() {
    feedbackList = new ArrayList<>();
    populate();
}
   

public void populate() {
    Path feedbackFilePath = Paths.get(mainDirectory, "utilityFiles", "testFeedback.txt");

    try (Scanner reader = new Scanner(feedbackFilePath)) {
        while (reader.hasNextLine()) {
            String line = reader.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] entry = line.split(",", 2);
            if (entry.length == 2) {
                // Create a new FeedbackEntry and add it to the feedbackList
                feedbackEntry feedbackEntries = new feedbackEntry(); // error
                feedbackList.add(feedbackEntries);
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading from testFeedback.txt: " + e.getMessage());
        }
    }

    public void update(List<SubmissionFolder> submissionFolders) {
     
}

}

/*
 *  
    private List <FeedbackEntry> feedbackForm;
    private static String mainDirectory = "src/main/java/comp3607project";

public feedbackDoc(){
    feedbackForm = new ArrayList<>();
    populateFeedback();


    public boolean update
}
 * 
*/