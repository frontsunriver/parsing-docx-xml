package com.mycompany.docxunzipper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import com.mycompany.xwpf.Helper;
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
       new Main();
    }

    private Main() throws Exception {
//        String userDirectory = System.getProperty("user.dir");
//        File f = new File(userDirectory, "/src/main/resources/example.docx");
//
//        DJMDocument djmDocument = DocxJM.map(f.toString());
//        
//        System.out.println(Helper.getTextFromDocument(djmDocument));
    	
    	String userDirectory = System.getProperty("user.dir");
        File f = new File(userDirectory, "/src/main/resources/example.docx");
        
        FileInputStream fis = new FileInputStream(f); 

        XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
	    Helper helper = new Helper();
//	    System.out.println(helper.getFullText(xdoc));
//	    helper.getParagrapData(xdoc);
	    
	    helper.makeXml(xdoc);
//        LOGGER.log(Level.INFO, "The text is: " + );
//        LOGGER.log(Level.INFO, "All bold words in the document: " + Helper.getBoldWords(djmDocument).toString());
    }
}