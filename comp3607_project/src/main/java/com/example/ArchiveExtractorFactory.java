package com.example;

public class ArchiveExtractorFactory {

    private ArchiveExtractorFactory() {
    }

    public static ArchiveExtractor createExtractor(String archiveType) {
        if (archiveType.equals("zip")) {
            return new ZipExtractor();
        } else {
            throw new UnsupportedOperationException("Unsupported archive type");
        }
    }
}
