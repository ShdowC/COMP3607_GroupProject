import java.io.*;
import java.util.zip.*;
import java.util.ArrayList;
import java.util.List;

public class OpenZipfiles {
    private final List<File> studentFiles = new ArrayList<>();
    private FileInputStream studentAssignment = null;
    private ZipInputStream zippedAssignment = null; 
    private ZipEntry entry;

    public List<File> getStudentFiles() {
        return studentFiles;
    }

    public List<File> extracFiles(File zippedFile, String outputD){

        
       try {
            studentAssignment = new FileInputStream(zippedFile);
            zippedAssignment = new ZipInputStream(studentAssignment);
            String fileName = entry.getName();

            while((entry = zippedAssignment.getNextEntry())!= null){
                if(fileName.endsWith(".java")){
                    File  javaFile = new File(outputD, fileName);
                    studentFiles.add(javaFile);

                    studentFiles(zippedAssignment, javaFile);
                }
            }
    
       } catch (IOException e) {
        
       } finally {
            try {
                if(studentAssignment != null)
                    studentAssignment.close();

                if(zippedAssignment != null)
                    zippedAssignment.close();
            } catch (IOException e) {
            }
     
        }
        return studentFiles;
    }
   
}
