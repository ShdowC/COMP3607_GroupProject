package com.example.extractionclasses;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor {

    /**
     * Extracts the given zip file to a temporary directory.
     *
     * @param zipFile the zip file to be extracted
     * @return the root directory of the extracted files
     * @throws IOException if an I/O error occurs
     */
    public File extract(File zipFile) throws IOException {
        // Create a temporary directory to store the extracted files
        Path tempDir = Files.createTempDirectory("extracted_submissions");

        // Open the zip file as a ZipInputStream
        try (ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(zipFile.toPath()))) {
            ZipEntry entry;

            // Process each entry in the zip file
            while ((entry = zipIn.getNextEntry()) != null) {
                // Determine the file path of the extracted entry
                Path filePath = tempDir.resolve(entry.getName());

                if (entry.isDirectory()) {
                    // If it's a directory, create the directory
                    Files.createDirectories(filePath);
                } else {
                    // If it's a file, extract it
                    if (entry.getName().endsWith(".zip")) {
                        // If it's a zip file, recursively extract it
                        extract(filePath.toFile());
                    } else {
                        extractFile(zipIn, filePath);
                    }
                }

                // Close the current entry
                zipIn.closeEntry();
            }
        }

        // Return the root directory of the extracted files
        return tempDir.toFile();
    }

    /**
     * Extracts a single file from the ZipInputStream to the specified file path.
     *
     * @param zipIn    the ZipInputStream from which to read the file data
     * @param filePath the path to where the file should be extracted
     * @throws IOException if an I/O error occurs
     */
    private void extractFile(ZipInputStream zipIn, Path filePath) throws IOException {
        // Ensure the parent directory exists
        Files.createDirectories(filePath.getParent());

        // Write the file contents from the zip stream to the destination file
        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = zipIn.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }
}
