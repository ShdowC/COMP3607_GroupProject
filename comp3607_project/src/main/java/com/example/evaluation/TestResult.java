package com.example.evaluation;

import java.io.File;
import java.util.logging.Logger;

public class TestResult {
    private File javaFile;
    private boolean methodsPresent;
    private boolean attributesPresent;
    private String resultSummary;
    private static final Logger LOGGER = Logger.getLogger(TestResult.class.getName());

    public TestResult() {
        // Default constructor
    }

    public File getJavaFile() {
        return javaFile;
    }

    public void setJavaFile(File javaFile) {
        this.javaFile = javaFile;
    }

    public boolean isMethodsPresent() {
        return methodsPresent;
    }

    public void setMethodsPresent(boolean methodsPresent) {
        this.methodsPresent = methodsPresent;
    }

    public boolean isAttributesPresent() {
        return attributesPresent;
    }

    public void setAttributesPresent(boolean attributesPresent) {
        this.attributesPresent = attributesPresent;
    }

    public void setResultSummary(String summary) {
        StringBuilder resultSummaryBuilder = new StringBuilder();
        resultSummaryBuilder.append("Java File: ").append(javaFile.getName()).append("\n");
        resultSummaryBuilder.append("Methods Present: ").append(methodsPresent).append("\n");
        resultSummaryBuilder.append("Attributes Present: ").append(attributesPresent).append("\n");
        this.resultSummary = resultSummaryBuilder.toString();
    }

    public String getResultSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Present Java File: ").append(javaFile.getName()).append("\n");
        summary.append("Methods Present: ").append(methodsPresent).append("\n");
        summary.append("Attributes Present: ").append(attributesPresent).append("\n");
        return summary.toString();
    }
}
