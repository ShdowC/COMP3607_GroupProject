
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.example.extractionclasses.SubmissionFolder;
import com.example.extractionclasses.SubmissionFolderBuilder;
import com.example.extractionclasses.ZipExtractor;
import com.example.processingclasses.JavaFileProcessor;

public class JavaFileTest {

    @Test
    public void testChatBotAttributes() {
        // Specify the directory path where the zip files are stored
        Path zipFilePath = Paths.get("C:", "Users", "andre", "OneDrive", "Documents", "GitHub", "COMP3607_GroupProject",
                "comp3607_project", "src", "main", "resources", "Submissions.zip");
        File zipFile = zipFilePath.toFile();

        // Create an instance of SubmissionFolderBuilder and Extractor
        ZipExtractor zipExtractor = new ZipExtractor();
        SubmissionFolderBuilder folderBuilder = new SubmissionFolderBuilder();
        JavaFileProcessor processor = new JavaFileProcessor();
        folderBuilder.addObserver(processor);
        SubmissionFolder rootFolder = null;

        try {
            // Extract the zip files into the specified directory
            File extractedFolder = zipExtractor.extract(zipFile);

            // Build the submission folder
            rootFolder = folderBuilder.buildFolder(extractedFolder);

        } catch (Exception e) {
            fail("Error extracting zip file: " + e.getMessage());
        }

        List<String> expectedClassNames = List.of("chatBotName", "numResponsesGenerated", "messageLimit",
                "messageNumber", "bots");
        Map<String, List<String>> actualClassNames = new HashMap<>();
        actualClassNames.put(rootFolder.getName(), processor.getClassNames());

        // Populate the actualClassNames map with the actual class names

        for (Map.Entry<String, List<String>> entry : actualClassNames.entrySet()) {
            String key = entry.getKey();
            List<String> actualClasses = entry.getValue();

            // Compare the actual class names with the expected class names
            boolean allClassesMatch = expectedClassNames.containsAll(actualClasses)
                    && actualClasses.containsAll(expectedClassNames);
            if (!allClassesMatch) {
                System.out.println("Mismatch for key: " + key);
                System.out.println("Actual class names: " + actualClasses);
                System.out.println("Expected class names: " + expectedClassNames);
            }
        }

    }
}