package com.example.pdfcreatorclasses;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Utility class for creating and managing PDF documents.
 * Provides methods to create, modify, and extract content from PDFs.
 */


public class PDFGenerator {

      private PDDocument doc;
      private PDPage page;
      private PDPageContentStream currentStream;
      private static int fileNo = 1;


  /*
   * Creates a new PDF document.
   */    

   public void createDoc(){
        doc = new PDDocument();
   }


  /*
   * Creates a new PDF page.
   */  


   public void createNewpage(){
    page = new PDPage();
    doc.addPage(page);

    try {
        currentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true);
    } catch (IOException e) {
        e.printStackTrace();
    }
   }

    /**
     * Gets the current page of the PDF document.
     * @return The current page.
     */

     public PDPage getpage()
     {
         return page;
     }
 
    public PDPageContentStream getcurrentStream()
    {
        return currentStream;
    }
    /**
     * Add this content to the PDF document.
     * @param lines 
     */

    public void addContent(String[] lines){
        try {
            float margin = 75;
            float posit = page.getMediaBox().getHeight() - margin;

 // Begin a text block in the content stream
            currentStream.beginText();
            currentStream.setFont(PDType1Font.COURIER_BOLD, 11);
            currentStream.newLineAtOffset(margin, posit);
            float lheight = 12;

// Loop through each line of content and add it to the PDF
            for(String line : lines){
                currentStream.newLineAtOffset(0, -lheight);
                currentStream.showText(line);
            }

            currentStream.endText(); // End the text block
          } catch(IOException){
            e.printStackTrace();
        
         }
    }


   /**
     * Adds additional content to the PDF document.
     * @param additionalContent The additional content to add.
     */


    
     public void additionalContent(String additionalContent){
        try {
            float margin = 75
            float posit = page.getMediaBox().getHeight() - margin;
    // Begin a text block in the content stream
            currentStream.beginText();
            currentStream.setFont(PDType1Font.COURIER_BOLD, 11);
            currentStream.newLineAtOffset(margin, posit);
           // float lheight = 12;
    // Loop through each line of additional content and add it to the PDF
            for(String line : additionalContent.split("\n")){
              //  currentStream.newLineAtOffset(tx:0, -lHeight);
                currentStream.showText(line);
                currentStream.newLineAtOffset(0, -11);
            }

            currentStream.endText();
          }
   
       catch(IOException){
        e.printStackTrace();
        
         }
     }

/**
 * Saves the PDF document to the specified file path.
 * @param filePath The path where the document should be saved.
 */
public void saveDocument(String filePath) {
    try {
        doc.save(filePath);  // Save the document to the provided file path
    } catch (IOException e) {
        e.printStackTrace();  // Print the stack trace if an IOException occurs
    } finally {
        closeDocument();  // Ensure the document is closed, even if an exception occurs
    }
}

/**
 * Closes the PDF document.
 */
private void closeDocument() {
    try {
        if (doc != null) {
            doc.close();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


public String getContent(){
    if(doc == NULL){
        throw new IllegalStateException("ERROR: Creating document error.");
    }

    try{
        PDFTextStripper pdfStripper = new PDFTextStripper();
        StringWriter stringW = new StringWriter();
        pdfStripper.writeText(doc,stringW);
        return stringW.toString();
    } catch(IOException e){
        e.printStackTrace();
        return NULL;
    }
  }
}  