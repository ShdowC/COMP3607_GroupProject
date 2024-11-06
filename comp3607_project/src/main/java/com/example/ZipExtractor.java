package com.example;

import java.io.*;
import java.util.zip.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ZipExtractor implements ArchiveExtractor {

    private static final Logger LOGGER = Logger.getLogger(ZipExtractor.class.getName());

    @Override
    public void extract(String archivePath, String destinationPath) {
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(archivePath))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                String filePath = destinationPath + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    extractFile(zipIn, filePath);
                } else {
                    File dir = new File(filePath);
                    dir.mkdirs();
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error extracting zip file: {0}", e.getMessage());
        }
    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
}