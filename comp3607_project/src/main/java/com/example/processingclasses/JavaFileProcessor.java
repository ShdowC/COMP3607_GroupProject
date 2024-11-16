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
import com.github.javaparser.ast.body.VariableDeclarator;

import java.util.logging.Level;
import com.github.javaparser.ast.body.MethodDeclaration;

// JavaFileProcessor.java (modified)
public class JavaFileProcessor implements TestObserver {
    private static final Logger LOGGER = Logger.getLogger(JavaFileProcessor.class.getName());
    private JavaFileParser parser = new JavaFileParser();

    @Override
    public void update(List<SubmissionFolder> submissionFolders) {
        for (SubmissionFolder submissionFolder : submissionFolders) {
            // Process each child of the submission folder individually
            for (SubmissionComponent child : submissionFolder.getChildren()) {
                if (child instanceof JavaFileSubmission) {
                    JavaFileSubmission javaFileSubmission = (JavaFileSubmission) child;
                    File javaFile = javaFileSubmission.getFile();
                    // Process the Java file
                    List<String> classNames = processJavaFile(javaFile);
                    List<String> classMethodNames = processJavaMethods(javaFile);
                    List<String> classFieldNames = processJavaFields(javaFile);
                    System.out.println("Class Names: " + classNames);
                    System.out.println("Class Method Names: " + classMethodNames);
                    System.out.println("Class Field Names: " + classFieldNames);
                }
            }
        }
    }

    public List<File> getJavaFilesFromSubmissionFolder(SubmissionFolder submissionFolder) {
        List<SubmissionComponent> children = submissionFolder.getChildren();
        List<File> javaFiles = new ArrayList<>();
        for (SubmissionComponent child : children) {
            if (child instanceof JavaFileSubmission) {
                javaFiles.add(((JavaFileSubmission) child).getFile());
            }
        }
        return javaFiles;
    }

    public List<String> processJavaFile(File javaFile) {
        List<String> classNames = new ArrayList<>();
        try {
            List<ClassOrInterfaceDeclaration> classes = parser.parseClasses(javaFile);
            for (ClassOrInterfaceDeclaration clazz : classes) {
                classNames.add(clazz.getNameAsString());
            }
        } catch (Exception e) {
            String message = "Error parsing Java file: " + javaFile.getName();
            LOGGER.log(Level.SEVERE, message, e);
        }
        return classNames;
    }

    public List<String> processJavaMethods(File javaFile) {
        List<String> methodNames = new ArrayList<>();
        try {
            List<ClassOrInterfaceDeclaration> classes = parser.parseClasses(javaFile);
            for (ClassOrInterfaceDeclaration clazz : classes) {
                List<MethodDeclaration> methods = parser.parseMethods(clazz);
                for (MethodDeclaration method : methods) {
                    methodNames.add(method.getNameAsString());
                }
            }
        } catch (Exception e) {
            String message = "Error parsing Java file: " + javaFile.getName();
            LOGGER.log(Level.SEVERE, message, e);
        }
        return methodNames;
    }

    public List<String> processJavaFields(File javaFile) {
        List<String> fieldNames = new ArrayList<>();
        try {
            List<ClassOrInterfaceDeclaration> classes = parser.parseClasses(javaFile);
            for (ClassOrInterfaceDeclaration clazz : classes) {
                List<FieldDeclaration> fields = parser.parseFields(clazz);
                for (FieldDeclaration field : fields) {
                    for (VariableDeclarator variable : field.getVariables()) {
                        fieldNames.add(variable.getNameAsString());
                    }
                }
            }
        } catch (Exception e) {
            String message = "Error parsing Java file: " + javaFile.getName();
            LOGGER.log(Level.SEVERE, message, e);
        }
        return fieldNames;
    }

    public List<String> getClassNames(File javaFile) {
        List<String> classNames = new ArrayList<>();
        try {
            List<ClassOrInterfaceDeclaration> classes = parser.parseClasses(javaFile);
            for (ClassOrInterfaceDeclaration clazz : classes) {
                classNames.add(clazz.getNameAsString());
            }
        } catch (Exception e) {
            String message = "Error parsing Java file: " + javaFile.getName();
            LOGGER.log(Level.SEVERE, message, e);
        }
        return classNames;
    }

    public List<String> getMethodNames(File javaFile) {
        List<String> methodNames = new ArrayList<>();
        try {
            List<ClassOrInterfaceDeclaration> classes = parser.parseClasses(javaFile);
            for (ClassOrInterfaceDeclaration clazz : classes) {
                List<MethodDeclaration> methods = parser.parseMethods(clazz);
                for (MethodDeclaration method : methods) {
                    methodNames.add(method.getNameAsString());
                }
            }
        } catch (Exception e) {
            String message = "Error parsing Java file: " + javaFile.getName();
            LOGGER.log(Level.SEVERE, message, e);
        }
        return methodNames;
    }

    public List<String> getFieldNames(File javaFile) {
        List<String> fieldNames = new ArrayList<>();
        try {
            List<ClassOrInterfaceDeclaration> classes = parser.parseClasses(javaFile);
            for (ClassOrInterfaceDeclaration clazz : classes) {
                List<FieldDeclaration> fields = parser.parseFields(clazz);
                for (FieldDeclaration field : fields) {
                    for (VariableDeclarator variable : field.getVariables()) {
                        fieldNames.add(variable.getNameAsString());
                    }
                }
            }
        } catch (Exception e) {
            String message = "Error parsing Java file: " + javaFile.getName();
            LOGGER.log(Level.SEVERE, message, e);
        }
        return fieldNames;
    }
}