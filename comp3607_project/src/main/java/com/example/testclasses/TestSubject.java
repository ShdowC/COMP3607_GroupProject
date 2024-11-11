package com.example.testclasses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.extractionclasses.SubmissionFolder;

public class TestSubject {
    private List<TestObserver> observers = new ArrayList<>();
    private Set<String> processedFolders = new HashSet<>();

    public void addObserver(TestObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(List<SubmissionFolder> submissionFolders) {
        if (!processedFolders
                .containsAll(submissionFolders.stream().map(SubmissionFolder::getName).collect(Collectors.toList()))) {
            // Notify observers
            for (TestObserver observer : observers) {
                observer.update(submissionFolders);
            }
            // Add folders to processed set
            processedFolders
                    .addAll(submissionFolders.stream().map(SubmissionFolder::getName).collect(Collectors.toList()));
        }
    }
}