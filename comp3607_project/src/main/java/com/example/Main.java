package com.example;

import java.util.logging.*;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Hello Thank you for using our application");
        /*
         * // Specify the directory path where the zip files are stored
         * Path zipFilePath = Paths.get("C:", "Users", "andre", "OneDrive", "Documents",
         * "GitHub", "COMP3607_GroupProject",
         * "comp3607_project", "src", "main", "resources", "Submissions.zip");
         * File zipFile = zipFilePath.toFile();
         * 
         * // Create an instance of SubmissionFolderBuilder and Extractor
         * ZipExtractor zipExtractor = new ZipExtractor();
         * SubmissionFolderBuilder folderBuilder = new SubmissionFolderBuilder();
         * JavaFileProcessor processor = new JavaFileProcessor();
         * folderBuilder.addObserver(processor);
         * 
         * try {
         * // Extract the zip files into the specified directory
         * File extractedFolder = zipExtractor.extract(zipFile);
         * LOGGER.info("Extracted to: " + extractedFolder.getAbsolutePath());
         * 
         * // Build the submission folder
         * SubmissionFolder rootFolder = folderBuilder.buildFolder(extractedFolder);
         * 
         * // Run tests on the submission folder
         * rootFolder.runTests();
         * } catch (Exception e) {
         * LOGGER.log(Level.SEVERE, "Error extracting zip file: {0}", e.getMessage());
         * }
         */

    }
}