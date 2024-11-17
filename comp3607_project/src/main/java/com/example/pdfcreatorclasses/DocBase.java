package com.example.pdfcreatorclasses;

import java.io.File;

public class DocBase{
    private final PDFGenerator generator;

    public DocBase(){
        this.generator = new PDFGenerator();
    }

protected PDFGenerator getmanager(){
    return generator;
}

 public boolean update(String submissionPath, String studentID) {
        String savePath = submissionPath + File.separator + studentID + ".pdf";
        getmanager().saveDocument(savePath);
        return true;
    }
}