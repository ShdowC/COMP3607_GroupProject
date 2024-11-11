package com.example.testclasses;

import java.util.List;

import com.example.extractionclasses.SubmissionFolder;

public interface TestObserver {
    void update(List<SubmissionFolder> submissionFolders);
}