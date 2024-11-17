package com.example.evaluation;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.example.processingclasses.JavaFileProcessor;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class Evaluator extends JavaFileProcessor {
    public TestResult evaluateJavaFile(File javaFile, Map<String, RequiredClassInfo> requiredClassInfo) {
        // Process the Java file using the JavaFileProcessor class
        List<String> classNames = processJavaFile(javaFile);
        List<String> methodNames = processJavaMethods(javaFile);
        List<String> fieldNames = processJavaFields(javaFile);

        // Create a TestResult object to store the evaluation results
        TestResult testResult = new TestResult();
        testResult.setJavaFile(javaFile);

        for (String className : classNames) {
            if (requiredClassInfo.containsKey(className)) {
                RequiredClassInfo info = requiredClassInfo.get(className);

                // Check if all methods in the Java file are present in the required methods
                boolean methodsValid = true;
                for (String methodName : methodNames) {
                    if (!info.getRequiredMethods().contains(methodName)) {
                        methodsValid = false;
                        break;
                    }
                }
                testResult.setMethodsPresent(methodsValid);

                // Check if all attributes in the Java file are present in the required
                // attributes
                boolean attributesValid = true;
                for (String fieldName : fieldNames) {
                    if (!info.getRequiredAttributes().contains(fieldName)) {
                        attributesValid = false;
                        break;
                    }
                }
                testResult.setAttributesPresent(attributesValid);

                if (methodsValid && attributesValid) {
                    testResult.setResultSummary("Java file is valid");
                    return testResult;
                } else {
                    testResult.setResultSummary("Java file is invalid");
                    return testResult;
                }
            }
        }
        return testResult;
    }
}