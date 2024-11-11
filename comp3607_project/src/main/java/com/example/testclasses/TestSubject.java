package com.example.testclasses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.extractionclasses.SubmissionFolder;

public class TestSubject {
    private List<TestObserver> observers = new ArrayList<>();
    private Set<String> processedFolders = new HashSet<>();

    public void addObserver(TestObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(SubmissionFolder folder) {
        if (!processedFolders.contains(folder.getName())) {
            // Notify observers
            for (TestObserver observer : observers) {
                observer.update(folder);
            }
            // Add folder to processed set
            processedFolders.add(folder.getName());
        }
    }
}