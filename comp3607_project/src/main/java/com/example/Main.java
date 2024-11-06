package com.example;

import java.util.logging.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Hello Thank you for using our application");
        LOGGER.info("Please submit all zipped submissions to the Submissions Folder");

        // Implement the the input of zipped files here
        String dirPath = "comp3607_project\\src\\main\\resources\\Submissions";

        // create a Path object for the directory
        Path directory = Paths.get(dirPath);

        try {
            // use Files.list() to get a stream of files in the directory
            List<String> zipFileList = Files.list(directory)
                    .filter(file -> file.toString().endsWith(".zip"))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());

            // print the list of zip file names
            System.out.println(zipFileList);
        } catch (IOException e) {
            // handle exception
        }
    }
}