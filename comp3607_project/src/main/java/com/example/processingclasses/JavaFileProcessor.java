package com.example.processingclasses;

import com.example.extractionclasses.JavaFileSubmission;
import com.example.extractionclasses.SubmissionComponent;
import com.example.extractionclasses.SubmissionFolder;
import com.example.testclasses.TestObserver;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

// JavaFileProcessor.java (modified)
public class JavaFileProcessor implements TestObserver {
    private JavaCompiler compiler;
    private ClassLoader classLoader;
    private static final Logger LOGGER = Logger.getLogger(JavaFileProcessor.class.getName());

    public JavaFileProcessor() {
        compiler = ToolProvider.getSystemJavaCompiler();
        classLoader = new URLClassLoader(new URL[] {});
    }

    @Override
    public void update(SubmissionFolder submissionFolder) {
        // Process the submission folder
        LOGGER.info("Processing submission folder: " + submissionFolder.getName());

        // Get the Java files in the submission folder
        List<SubmissionComponent> children = submissionFolder.getChildren();
        List<File> javaFiles = new ArrayList<>();
        for (SubmissionComponent child : children) {
            if (child instanceof JavaFileSubmission) {
                javaFiles.add(((JavaFileSubmission) child).getFile());
            }
        }

        // Compile and execute each Java file
        for (File javaFile : javaFiles) {
            try {
                // Compile the Java file
                int compilationResult = compiler.run(null, null, null, javaFile.getAbsolutePath());
                if (compilationResult != 0) {
                    LOGGER.severe("Compilation failed for " + javaFile.getName());
                    continue;
                }

                // Load the compiled class
                Class<?> clazz = classLoader.loadClass(javaFile.getName().replace(".java", ""));

                // Get the JUnit test methods
                Method[] methods = clazz.getMethods();
                List<Method> testMethods = new ArrayList<>();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(org.junit.Test.class)) {
                        testMethods.add(method);
                    }
                }

                // Run the JUnit tests
                for (Method testMethod : testMethods) {
                    runTest(testMethod, javaFile);
                }

            } catch (Exception e) {
                LOGGER.severe("Error executing test for " + javaFile.getName() + ": " + e.getMessage());
            }
        }
    }

    private void runTest(Method testMethod, File javaFile) {
        try {
            testMethod.invoke(null, (Object) new Object[] {});
            LOGGER.info("Test passed for " + javaFile.getName() + ": " + testMethod.getName());
        } catch (Exception e) {
            LOGGER.severe("Test failed for " + javaFile.getName() + ": " + testMethod.getName() + ": "
                    + e.getMessage());
        }
    }
}