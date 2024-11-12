package com.example.extractionclasses;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Queue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.BufferedOutputStream;

public class ZipExtractor {
    public File extract(File zipFile) throws IOException {
        // Create a temporary directory to store the extracted files
        Path tempDir = Files.createTempDirectory("extracted_submissions");

        // Create a queue to hold the zip files that need to be extracted
        Queue<File> zipFilesToExtract = new LinkedList<>();
        zipFilesToExtract.add(zipFile);

        while (!zipFilesToExtract.isEmpty()) {
            File currentZipFile = zipFilesToExtract.poll();

            // Open the zip file as a ZipInputStream
            try (ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(currentZipFile.toPath()))) {
                ZipEntry entry;

                // Process each entry in the zip file
                while ((entry = zipIn.getNextEntry()) != null) {
                    String entryName = entry.getName();
                    Path entryPath = tempDir.resolve(entryName);

                    if (entry.isDirectory()) {
                        // Create the directory if it doesn't exist
                        Files.createDirectories(entryPath);
                    } else if (entryName.endsWith(".zip")) {
                        // Recursively extract the zip file
                        extractZipFile(entryPath, zipIn);
                    } else {
                        // Extract the file
                        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(entryPath))) {
                            byte[] bytesIn = new byte[4096];
                            int read = 0;
                            while ((read = zipIn.read(bytesIn)) != -1) {
                                bos.write(bytesIn, 0, read);
                            }
                        }
                    }

                    zipIn.closeEntry();
                }
            }
        }

        // Return the root directory of the extracted files
        return tempDir.toFile();
    }

    private void extractZipFile(Path entryPath, ZipInputStream zipIn) throws IOException {
        // Create a temporary zip file
        Path tempZipFile = Files.createTempFile("temp", ".zip");
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(tempZipFile))) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }

        // Recursively extract the zip file
        try (ZipInputStream nestedZipIn = new ZipInputStream(Files.newInputStream(tempZipFile))) {
            ZipEntry nestedEntry;
            while ((nestedEntry = nestedZipIn.getNextEntry()) != null) {
                String nestedEntryName = nestedEntry.getName();
                Path nestedEntryPath = entryPath.resolve(nestedEntryName);

                if (nestedEntry.isDirectory()) {
                    // Create the directory if it doesn't exist
                    Files.createDirectories(nestedEntryPath);
                } else {
                    // Extract the file
                    try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(nestedEntryPath))) {
                        byte[] bytesIn = new byte[4096];
                        int read = 0;
                        while ((read = nestedZipIn.read(bytesIn)) != -1) {
                            bos.write(bytesIn, 0, read);
                        }
                    }
                }

                nestedZipIn.closeEntry();
            }
        } finally {
            // Delete the temporary zip file
            Files.delete(tempZipFile);
        }
    }
}