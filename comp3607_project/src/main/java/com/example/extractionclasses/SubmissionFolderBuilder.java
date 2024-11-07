package com.example.extractionclasses;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubmissionFolderBuilder {
    private static final Logger LOGGER = Logger.getLogger(SubmissionFolderBuilder.class.getName());

    public SubmissionFolder buildFolder(File directory) {

        SubmissionFolder folder = new SubmissionFolder(directory.getName());
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                // Unzip the folder if it's a zip file
                if (file.getName().endsWith(".zip")) {
                    try {
                        ZipExtractor zipExtractor = new ZipExtractor();
                        File extractedFolder = zipExtractor.extract(file);
                        folder.add(buildFolder(extractedFolder));
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "Error unzipping folder: {0}", e.getMessage());
                    }
                } else {
                    folder.add(buildFolder(file));
                }
            } else {
                folder.add(new JavaFileSubmission(file.getName(), file));
            }
        }

        logFolderStructure(folder, 0);
        return folder;
    }

    private void logFolderStructure(SubmissionFolder folder, int indentLevel) {
        LOGGER.info(getIndent(indentLevel) + folder.getName());
        for (SubmissionComponent component : folder.getChildren()) {
            if (component instanceof SubmissionFolder) {
                logFolderStructure((SubmissionFolder) component, indentLevel + 1);
            } else {
                LOGGER.info(getIndent(indentLevel + 1) + component.getName());
            }
        }
    }

    private String getIndent(int level) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level; i++) {
            indent.append("  ");
        }
        return indent.toString();
    }
}
