package com.example.processingclasses;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class JavaFileParser {
    private static final Logger LOGGER = Logger.getLogger(JavaFileParser.class.getName());

    public List<ClassOrInterfaceDeclaration> parseClasses(File javaFile) {
        JavaParser parser = new JavaParser();
        try {
            CompilationUnit cu = parser.parse(javaFile).getResult().get();
            return cu.findAll(ClassOrInterfaceDeclaration.class);
        } catch (FileNotFoundException e) {
            // Handle the exception, for example, by logging an error message
            LOGGER.severe("Error: File not found - " + javaFile.getAbsolutePath());
            return Collections.emptyList();
        }
    }

    public List<FieldDeclaration> parseFields(ClassOrInterfaceDeclaration clazz) {
        return clazz.findAll(FieldDeclaration.class);
    }

    public List<MethodDeclaration> parseMethods(ClassOrInterfaceDeclaration clazz) {
        return clazz.findAll(MethodDeclaration.class);
    }
}