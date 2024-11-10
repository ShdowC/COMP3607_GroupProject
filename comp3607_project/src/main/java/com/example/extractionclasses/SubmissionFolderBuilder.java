package com.example.extractionclasses;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.testclasses.TestObserver;
import com.example.testclasses.TestSubject;

public class SubmissionFolderBuilder {
    private static final Logger LOGGER = Logger.getLogger(SubmissionFolderBuilder.class.getName());

    private TestSubject testSubject = new TestSubject();

    public void addObserver(TestObserver observer) {
        testSubject.addObserver(observer);
    }

    public SubmissionFolder buildFolder(File directory) {

        SubmissionFolder folder = new SubmissionFolder(directory.getName());
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                // Unzip the folder if it's a zip file
                if (file.getName().endsWith(".zip")) {
                    try {
                        ZipExtractor zipExtractor = new ZipExtractor();
                        File extractedFolder = zipExtractor.extract(file);
                        folder.add(buildFolder(extractedFolder));
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "Error unzipping folder: {0}", e.getMessage());
                    }
                } else {
                    folder.add(buildFolder(file));
                }
            } else {
                folder.add(new JavaFileSubmission(file.getName(), file));
            }
        }

        testSubject.notifyObservers(folder);
        return folder;
    }

}
