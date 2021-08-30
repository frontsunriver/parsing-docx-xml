package com.mycompany.docxunzipper;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class MainFunc {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
       new MainFunc();
    }

    private MainFunc() throws Exception {
        String userDirectory = System.getProperty("user.dir");
        File f = new File(userDirectory, "/src/main/resources/example.docx");
        
        FileInputStream fis = new FileInputStream(f); 

        XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
	    XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
	    System.out.println(extractor.getText());
//        LOGGER.log(Level.INFO, "The text is: " + );
//        LOGGER.log(Level.INFO, "All bold words in the document: " + Helper.getBoldWords(djmDocument).toString());
    }
}