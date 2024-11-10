package com.example.testclasses;

import java.util.ArrayList;
import java.util.List;
import com.example.extractionclasses.SubmissionFolder;

public class TestSubject {
    private List<TestObserver> observers = new ArrayList<>();

    public void addObserver(TestObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(SubmissionFolder submissionFolder) {
        for (TestObserver observer : observers) {
            observer.update(submissionFolder);
        }
    }
}