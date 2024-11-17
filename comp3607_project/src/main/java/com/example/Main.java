package com.example;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.processingclasses.JavaFileProcessor;
import com.itextpdf.text.pdf.PdfWriter;
import com.example.extractionclasses.SubmissionFolder;
import com.example.extractionclasses.SubmissionFolderBuilder;
import com.example.extractionclasses.ZipExtractor;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Hello Thank you for using our application");

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Zip Files", "zip");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Select a ZIP File");
        Scanner scanner = new Scanner(System.in);
        
        Path zipFilePath = null;
        File zipFile = null;
        boolean running = true;
        
        System.out.println("======================================================================================");
        System.out.println("Hello! Welcome to Object-Oriented Programming I Assignment Auto Grader!");
        System.out.println("Please Enter 'select' to choose a ZIP file or 'exit' to quit the application.");
        System.out.println("======================================================================================");

        while (running) {

            System.out.print(" > Enter command: ");
            String command = scanner.nextLine().replaceAll("\\s", "").toLowerCase();

            switch (command) {
                case "select":
                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        zipFile = fileChooser.getSelectedFile();
                        System.out.println("Selected file: " + zipFile.getAbsolutePath());
                        zipFilePath = Paths.get(fileChooser.getSelectedFile().getAbsolutePath());
                        running = false;
                    }
                    else {
                        System.out.println(" > File selection cancelled.\n");
                    }
                    break;
                case "exit":
                    System.out.println("======================================================================================");
                    System.out.println(" > Exiting the application... Goodbye!");
                    System.out.println("======================================================================================");
                    running = false;
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println(" > Unknown command. Please enter 'select' or 'exit'.\n");
            }   
        }
        scanner.close();

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
