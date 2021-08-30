package com.mycompany.docxunzipper;

import com.mycompany.model.DJMDocument;
import com.mycompany.model.DJMParagraph;
import com.mycompany.model.DJMRun;
import com.mycompany.model.interfaces.BodyElement;
import com.mycompany.model.interfaces.ParagraphElement;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    private Helper() {
    }

    /**
     * Returns the actual text of a Docx document.
     *
     * @param djmDocument the Document to process
     * @return String of actual text of the document
     */
    public static String getTextFromDocument(DJMDocument djmDocument) {
        StringBuilder stringBuilder = new StringBuilder();
        // Different elements can be of type BodyElement
        for (BodyElement bodyElement : djmDocument.getBody().getBodyElements()) {
            // Check, if current BodyElement is of type DJMParagraph
        	try {
        		if (bodyElement instanceof DJMParagraph) {
                    DJMParagraph dJMParagraph = (DJMParagraph) bodyElement;
                    // Different elements can be of type ParagraphElement
                    for (ParagraphElement paragraphElement : dJMParagraph.getParagraphElements()) {
                        // Check, if current ParagraphElement is of type DJMRun
                        if (paragraphElement instanceof DJMRun) {
                            DJMRun dJMRun = (DJMRun) paragraphElement;
                            stringBuilder.append(dJMRun.getText());
                        }
                    }
                    stringBuilder.append("\n");
                }
        	}catch(Exception e) {
        		
        	}
            
        }
        return stringBuilder.toString();
    }

    /**
     * Returns a list of all bold words in a document.
     *
     * @param djmDocument the Document to process
     * @return list of all bold words
     */
    public static List<String> getBoldWords(DJMDocument djmDocument) {
        List<String> boldWords = new ArrayList<>();
        // Different elements can be of type BodyElement
        for (BodyElement bodyElement : djmDocument.getBody().getBodyElements()) {
            // Check, if current BodyElement is of type DJMParagraph
            if (bodyElement instanceof DJMParagraph) {
                DJMParagraph dJMParagraph = (DJMParagraph) bodyElement;
                // Different elements can be of type ParagraphElement
                for (ParagraphElement paragraphElement : dJMParagraph.getParagraphElements()) {
                    // Check, if current ParagraphElement is of type DJMRun
                    if (paragraphElement instanceof DJMRun) {
                        DJMRun dJMRun = (DJMRun) paragraphElement;
                        boolean isBold = dJMRun.getRunProperties().isBold();
                        if (isBold) {
                            String text = dJMRun.getText();
                            boldWords.add(text);
                        }
                    }
                }
            }
        }
        return boldWords;
    }
}