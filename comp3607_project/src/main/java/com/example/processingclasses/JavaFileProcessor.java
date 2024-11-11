package com.example.processingclasses;

import com.example.extractionclasses.JavaFileSubmission;
import com.example.extractionclasses.SubmissionComponent;
import com.example.extractionclasses.SubmissionFolder;
import com.example.testclasses.TestObserver;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.Parameter;
import java.util.logging.Level;
import com.github.javaparser.ast.body.MethodDeclaration;

// JavaFileProcessor.java (modified)
public class JavaFileProcessor implements TestObserver {
    private static final Logger LOGGER = Logger.getLogger(JavaFileProcessor.class.getName());
    private JavaFileParser parser = new JavaFileParser();
    private List<String> classNames = new ArrayList<>();

    @Override
    public void update(SubmissionFolder submissionFolder) {
        // Process the submission folder
        LOGGER.info("Processing submission folder: " + submissionFolder.getName());
        List<File> javaFiles = getJavaFilesFromSubmissionFolder(submissionFolder);
        classNames = processJavaFiles(javaFiles);
        processJavaMethods(javaFiles);
    }

    private List<File> getJavaFilesFromSubmissionFolder(SubmissionFolder submissionFolder) {
        List<SubmissionComponent> children = submissionFolder.getChildren();
        List<File> javaFiles = new ArrayList<>();
        for (SubmissionComponent child : children) {
            if (child instanceof JavaFileSubmission) {
                javaFiles.add(((JavaFileSubmission) child).getFile());
            }
        }
        return javaFiles;
    }

    public List<String> processJavaFiles(List<File> javaFiles) {
        List<String> attributes = new ArrayList<>();
        for (File javaFile : javaFiles) {
            try {
                List<ClassOrInterfaceDeclaration> classes = parser.parseClasses(javaFile);
                List<String> fileAttributes = new ArrayList<>(); // Create a new list for each file
                for (ClassOrInterfaceDeclaration clazz : classes) {
                    List<FieldDeclaration> fields = parser.parseFields(clazz);
                    for (FieldDeclaration field : fields) {
                        fileAttributes.add(field.getVariables().get(0).getNameAsString());
                    }
                }
                attributes.addAll(fileAttributes); // Add the file attributes to the main list
            } catch (Exception e) {
                String message = "Error parsing Java file: " + javaFile.getName();
                LOGGER.log(Level.SEVERE, message, e);
            }
        }
        return attributes;
    }

    public List<String> processJavaMethods(List<File> javaFiles) {
        List<String> attributes = new ArrayList<>();
        for (File javaFile : javaFiles) {
            try {
                List<ClassOrInterfaceDeclaration> classes = parser.parseClasses(javaFile);
                for (ClassOrInterfaceDeclaration clazz : classes) {
                    List<MethodDeclaration> methods = parser.parseMethods(clazz);
                    for (MethodDeclaration method : methods) {
                        for (Parameter parameter : method.getParameters()) {
                            attributes.add(parameter.getNameAsString());
                        }
                    }
                }
            } catch (Exception e) {
                String message = "Error parsing Java file: " + javaFile.getName();
                LOGGER.log(Level.SEVERE, message, e);
            }
        }
        return attributes;
    }

    public List<String> getClassNames() {
        return classNames;
    }

}