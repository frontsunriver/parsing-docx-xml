package com.mycompany.model;


import com.mycompany.model.interfaces.BodyElement;
import com.mycompany.model.interfaces.ParagraphElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * p (Paragraph)
 * <p>
 * This element specifies a paragraph of content in the document.
 * <p>
 * The contents of a paragraph in a WordprocessingML document shall consist of
 * any combination of the following four kinds of content:
 * <p>
 * Paragraph properties
 * <p>
 * Annotations (bookmarks, comments, revisions)
 * <p>
 * Custom markup
 * <p>
 * Run level content (fields, hyperlinks, runs)
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DJMParagraph implements BodyElement {

    List<ParagraphElement> paragraphElements;

    @XmlElements({
            @XmlElement(name = "r", type = DJMRun.class),
    })

    public List<ParagraphElement> getParagraphElements() {
        return paragraphElements;
    }

    private void setParagraphElements(List<ParagraphElement> paragraphElements) {
        this.paragraphElements = paragraphElements;
    }

}
