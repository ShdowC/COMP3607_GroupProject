package com.example.extractionclasses;

//Leaf Class
import java.io.File;

public class JavaFileSubmission implements SubmissionComponent {

    private String name;
    private File file;

    public JavaFileSubmission(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void runTests() {
        // Logic to run tests on the Java file
        System.out.println("Running test on Java file: " + file.getName());
    }

}
