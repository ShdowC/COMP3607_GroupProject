package com.example.extractionclasses;

import java.io.File;
import com.example.testclasses.TestObserver;
import com.example.testclasses.TestSubject;

public class SubmissionFolderBuilder {

    private TestSubject testSubject = new TestSubject();

    public void addObserver(TestObserver observer) {
        testSubject.addObserver(observer);
    }

    public SubmissionFolder buildFolder(File directory) {

        SubmissionFolder folder = new SubmissionFolder(directory.getName());
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                folder.add(buildFolder(file));
            } else {
                folder.add(new JavaFileSubmission(file.getName(), file));
            }
        }

        testSubject.notifyObservers(folder);
        return folder;
    }

}
