package com.mycompany.docxunzipper;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Unzipper {

    private Unzipper() {
    }

    /**
     * Returns the stream to a document.xml from the DOCX-archive.
     *
     * @param docx the Docx file
     * @return the InputStream of the document.xml
     * @throws IOException
     */
    public static InputStream getStreamToDocumentXml(File docx) throws IOException {
        ZipFile zipFile = new ZipFile(docx);
        ZipEntry zipEntry = zipFile.getEntry("word/document.xml");
        return zipFile.getInputStream(zipEntry);
    }
}
