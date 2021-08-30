package com.mycompany.model;

import com.mycompany.model.interfaces.BodyElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * The container for the block level structures such as paragraphs, tables,
 * annotations, and others specified in the ISO/IEC 29500 specification.
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DJMBody {

    List<BodyElement> bodyElements;

    @XmlElements({
            @XmlElement(name = "p", type = DJMParagraph.class)
    })

    public List<BodyElement> getBodyElements() {
        return bodyElements;
    }

    private void setBodyElements(List<BodyElement> bodyElements) {
        this.bodyElements = bodyElements;
    }
}
