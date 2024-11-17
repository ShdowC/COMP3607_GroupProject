package com.example.extractionclasses;

import java.io.File;
//Composite Class
import java.util.ArrayList;
import java.util.List;
import com.example.evaluation.TestResult;
import com.example.pdf.GeneratePdfReport;

public class SubmissionFolder implements SubmissionComponent {
    private String name;
    private List<SubmissionComponent> children = new ArrayList<>();
    private List<TestResult> testResults = new ArrayList<>();

    public SubmissionFolder(String name) {
        this.name = name;
    }

    public void add(SubmissionComponent component) {
        children.add(component);
    }

    public void remove(SubmissionComponent component) {
        children.remove(component);
    }

    public List<SubmissionComponent> getChildren() {
        List<SubmissionComponent> childComponents = new ArrayList<>();
        for (SubmissionComponent child : this.children) {
            if (child instanceof SubmissionFolder) {
                childComponents.addAll(((SubmissionFolder) child).getChildren());
            } else {
                childComponents.add(child);
            }
        }
        return childComponents;
    }

    public void addTestResult(TestResult testResult) {
        testResults.add(testResult);
    }

    public List<TestResult> getTestResults() {
        return testResults;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void runTests() {
        for (SubmissionComponent component : children) {
            component.runTests();
        }
    }
}
