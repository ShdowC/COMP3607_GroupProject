package com.example.extractionclasses;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.example.testclasses.TestObserver;
import com.example.testclasses.TestSubject;

public class SubmissionFolderBuilder {

    private TestSubject testSubject = new TestSubject();

    public void addObserver(TestObserver observer) {
        testSubject.addObserver(observer);
    }

    public SubmissionFolder buildFolder(Path directory) {
        SubmissionFolder folder = new SubmissionFolder(directory.getFileName().toString());
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    folder.add(buildFolder(entry));
                } else {
                    folder.add(new JavaFileSubmission(entry.getFileName().toString(), entry));
                }
            }
        } catch (IOException e) {
            // Handle the exception
        }

        // Notify observers with a list of submission folders
        List<SubmissionFolder> submissionFolders = new ArrayList<>();
        submissionFolders.add(folder);
        testSubject.notifyObservers(submissionFolders);

        return folder;
    }
}