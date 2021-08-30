package com.mycompany.docxunzipper;

import com.mycompany.model.DJMDocument;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocxJM {

    private DocxJM() {
    }

    private static final Logger LOGGER = Logger.getLogger(DocxJM.class.getName());
    private static JAXBContext jaxbContext;

    /**
     * Maps a given Docx to a Pojo and returns it.
     *
     * @param str path to the Docx file
     * @return the mapped Pojo
     * @throws java.io.IOException if file doesn't exist
     */
    public static DJMDocument map(String str) throws IOException {
        File file = new File(str);
        if (!file.exists()) {
            throw new IOException("File doesn't exist: " + file);
        }
        DJMDocument document = null;

        try (InputStream is = Unzipper.getStreamToDocumentXml(new File(str))) {
            try {
                jaxbContext = JAXBContext.newInstance(DJMDocument.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                document = (DJMDocument) jaxbUnmarshaller.unmarshal(is);
            } catch (JAXBException ex) {
                LOGGER.log(Level.SEVERE, ex.toString());
            }
        }
        return document;
    }
}