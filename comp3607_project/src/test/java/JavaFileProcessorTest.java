
import org.junit.Test;

import java.io.File;

import com.example.extractionclasses.JavaFileSubmission;
import com.example.extractionclasses.SubmissionComponent;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.example.extractionclasses.SubmissionFolder;
import com.example.processingclasses.JavaFileProcessor;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.extractionclasses.JavaFileSubmission;
import com.example.extractionclasses.SubmissionComponent;
import com.example.extractionclasses.SubmissionFolder;
import com.example.processingclasses.JavaFileProcessor;

public class JavaFileProcessorTest {
    @Test
    public void testUpdate_EmptyList() {
        JavaFileProcessor processor = new JavaFileProcessor();
        List<SubmissionFolder> submissionFolders = new ArrayList<>();
        processor.update(submissionFolders);
        // Assuming getJavaFilesFromSubmissionFolder is a method to retrieve processed
        // files
        assertTrue(processor.getJavaFilesFromSubmissionFolder(new SubmissionFolder("")).isEmpty());
    }

    @Test
    public void testUpdate_SingleFolderWithJavaFiles() {
        JavaFileProcessor processor = new JavaFileProcessor();
        SubmissionFolder folder = new SubmissionFolder("test");
        File javaFile1 = new File("Test1.java");
        folder.add(new JavaFileSubmission("Test1", javaFile1.toPath()));
        File javaFile2 = new File("Test2.java");
        folder.add(new JavaFileSubmission("Test2", javaFile2.toPath()));
        List<SubmissionFolder> submissionFolders = new ArrayList<>();
        submissionFolders.add(folder);
        processor.update(submissionFolders);

        // Assuming getJavaFilesFromSubmissionFolder returns list of processed files
        List<File> files = processor.getJavaFilesFromSubmissionFolder(folder);
        assertEquals(2, files.size());
    }

    @Test
    public void testUpdate_MultipleFoldersWithJavaFiles() {
        JavaFileProcessor processor = new JavaFileProcessor();
        SubmissionFolder folder1 = new SubmissionFolder("test1");
        File javaFile1 = new File("Test1.java");
        folder1.add(new JavaFileSubmission("Test1", javaFile1.toPath()));
        SubmissionFolder folder2 = new SubmissionFolder("test2");
        File javaFile2 = new File("Test2.java");
        folder2.add(new JavaFileSubmission("Test2", javaFile2.toPath()));
        List<SubmissionFolder> submissionFolders = new ArrayList<>();
        submissionFolders.add(folder1);
        submissionFolders.add(folder2);
        processor.update(submissionFolders);

        // Assuming getJavaFilesFromSubmissionFolder returns list of processed files
        List<File> files1 = processor.getJavaFilesFromSubmissionFolder(folder1);
        List<File> files2 = processor.getJavaFilesFromSubmissionFolder(folder2);
        assertFalse(files1.isEmpty());
        assertFalse(files2.isEmpty());
    }

    @Test
    public void testUpdate_FolderWithNoJavaFiles() {
        JavaFileProcessor processor = new JavaFileProcessor();
        SubmissionFolder folder = new SubmissionFolder("test");
        File nonJavaFile = new File("Test.txt");
        folder.add(new JavaFileSubmission("Test", nonJavaFile.toPath()));
        List<SubmissionFolder> submissionFolders = new ArrayList<>();
        submissionFolders.add(folder);
        processor.update(submissionFolders);

        // Assuming getJavaFilesFromSubmissionFolder returns list of processed files
        List<File> files = processor.getJavaFilesFromSubmissionFolder(folder);
        assertTrue(files.isEmpty());
    }
}
