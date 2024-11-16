package com.example.extractionclasses;

//Leaf Class
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

public class JavaFileSubmission implements SubmissionComponent {

    private String name;
    private File file;
    private static final Logger LOGGER = Logger.getLogger(JavaFileSubmission.class.getName());

    public JavaFileSubmission(String name, Path path) {
        this.name = name;
        this.file = path.toFile();
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
        LOGGER.info("Running tests on " + name);
    }

}
