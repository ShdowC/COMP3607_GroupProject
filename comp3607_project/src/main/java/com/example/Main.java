package com.example;

import java.util.logging.*;

import com.example.extractionclasses.ZipExtractor;
import com.example.extractionclasses.SubmissionFolder;
import com.example.extractionclasses.SubmissionFolderBuilder;

import java.io.File;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Hello Thank you for using our application");

        // Specify the directory path where the zip files are stored

        File zipFile = new File("D:\\UWI Assignments\\OOP\\Project\\Submisisons.zip");

        // Create an instance of SubmissionFolderBuilder and Extractor
        ZipExtractor zipExtractor = new ZipExtractor();
        SubmissionFolderBuilder folderBuilder = new SubmissionFolderBuilder();

        try {
            // Extract the zip files into the specified directory
            File extractedFolder = zipExtractor.extract(zipFile);
            LOGGER.info("Extracted to: " + extractedFolder.getAbsolutePath());

            // Build the submission folder
            SubmissionFolder rootFolder = folderBuilder.buildFolder(extractedFolder);

            // Run tests on the submission folder
            rootFolder.runTests();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error extracting zip file: {0}", e.getMessage());
        }
    }
}