
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import com.example.extractionclasses.SubmissionFolder;
import com.example.extractionclasses.SubmissionFolderBuilder;
import com.example.extractionclasses.ZipExtractor;
import com.example.processingclasses.JavaFileProcessor;

public class JavaFileTest {

    @Test
    public void testJavaFile() {
        // Specify the directory path where the zip files are stored
        Path zipFilePath = Paths.get("C:", "Users", "andre", "OneDrive", "Documents", "GitHub", "COMP3607_GroupProject",
                "comp3607_project", "src", "main", "resources", "Submissions.zip");
        File zipFile = zipFilePath.toFile();

        // Create an instance of SubmissionFolderBuilder and Extractor
        ZipExtractor zipExtractor = new ZipExtractor();
        SubmissionFolderBuilder folderBuilder = new SubmissionFolderBuilder();
        JavaFileProcessor processor = new JavaFileProcessor();
        folderBuilder.addObserver(processor);

        try {
            // Extract the zip files into the specified directory
            File extractedFolder = zipExtractor.extract(zipFile);

            // Build the submission folder
            SubmissionFolder rootFolder = folderBuilder.buildFolder(extractedFolder);

        } catch (Exception e) {
            fail("Error extracting zip file: " + e.getMessage());
        }

        List<String> expectedClassNames = List.of("chatBotName", "numResponsesGenerated", "messageLimit",
                "messageNumber", "bots");

        List<String> actualClassNames = processor.getClassNames();
        assertEquals(expectedClassNames, actualClassNames);
    }
}