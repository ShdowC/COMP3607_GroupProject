package com.example;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.processingclasses.JavaFileProcessor;

import com.example.extractionclasses.SubmissionFolder;
import com.example.extractionclasses.SubmissionFolderBuilder;
import com.example.extractionclasses.ZipExtractor;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Hello Thank you for using our application");

        // Specify the directory path where the zip files are stored
        Path zipFilePath = Paths.get("C:", "Users", "andre", "OneDrive", "Documents", "GitHub", "COMP3607_GroupProject",
                "comp3607_project", "src", "main", "resources", "SubmissionFolder.zip");

        // Create an instance of SubmissionFolderBuilder and Extractor
        ZipExtractor zipExtractor = new ZipExtractor();
        SubmissionFolderBuilder folderBuilder = new SubmissionFolderBuilder();
        JavaFileProcessor processor = new JavaFileProcessor();
        folderBuilder.addObserver(processor);

        try {
            // Extract the zip files into the specified directory
            Path extractedFolder = zipExtractor.extract(zipFilePath);
            LOGGER.log(Level.INFO, "Extracted to: {0}", extractedFolder.toAbsolutePath());

            // Build the submission folder
            SubmissionFolder rootFolder = folderBuilder.buildFolder(extractedFolder);

            // Notify observers with a list of submission folders
            List<SubmissionFolder> submissionFolders = new ArrayList<>();
            submissionFolders.add(rootFolder);
            processor.update(submissionFolders);

            // Evaluate the submission folder
            rootFolder.runTests();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error extracting zip file: {0}", e.getMessage());
        }
    }

}
