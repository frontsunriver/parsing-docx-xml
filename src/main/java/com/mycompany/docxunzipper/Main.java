package com.mycompany.docxunzipper;

import java.util.logging.Logger;

import com.mycompany.xwpf.Form;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
       new Main();
    }

    private Main(){
//        String userDirectory = System.getProperty("user.dir");
//        File f = new File(userDirectory, "/src/main/resources/example.docx");
//
//        DJMDocument djmDocument = DocxJM.map(f.toString());
//        
//        System.out.println(Helper.getTextFromDocument(djmDocument));
    	
//    	String userDirectory = System.getProperty("user.dir");
//        File f = new File(userDirectory, "/src/main/resources/example.docx");
//        
//        FileInputStream fis = new FileInputStream(f); 
//
//        XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
//	    Helper helper = new Helper();
//	    
//	    helper.makeXml(xdoc);
    	Form f = new Form();
    	f.show();
//        LOGGER.log(Level.INFO, "The text is: " + );
//        LOGGER.log(Level.INFO, "All bold words in the document: " + Helper.getBoldWords(djmDocument).toString());
    }
}