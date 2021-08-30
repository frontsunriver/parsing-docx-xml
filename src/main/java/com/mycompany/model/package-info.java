@XmlSchema(
        namespace = "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
        elementFormDefault = XmlNsForm.QUALIFIED,
        xmlns = {
                @XmlNs(prefix = "w", namespaceURI = "http://schemas.openxmlformats.org/wordprocessingml/2006/main"),

        }
)

package com.mycompany.model;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
