package com.example.extractionclasses;

//Leaf Class
import java.io.File;
import java.util.logging.Logger;

public class JavaFileSubmission implements SubmissionComponent {

    private String name;
    private File file;
    private static final Logger LOGGER = Logger.getLogger(JavaFileSubmission.class.getName());

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

    // Method to run tests on the Java file
    @Override
    public void runTests() {
        // Logic to run tests on the Java file
        LOGGER.info("Running test on Java file: " + file.getName());
    }

}
