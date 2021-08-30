package com.mycompany.model;


import com.mycompany.model.properties.DJMColor;
import com.mycompany.model.properties.DJMFont;
import com.mycompany.model.properties.FontSize;
import com.mycompany.model.properties.adapter.BoldAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * rPr (Run Properties)
 * <p>
 * This element specifies a set of run properties which shall be applied to the
 * contents of the parent run after all style formatting has been applied to the
 * text. These properties are defined as direct formatting, since they are
 * directly applied to the run and supersede any formatting from styles.
 * <p>
 * This formatting is applied at the following location in the style hierarchy:
 * <p>
 * Document defaults
 * <p>
 * Table styles
 * <p>
 * Numbering styles
 * <p>
 * Paragraph styles
 * <p>
 * Character styles
 * <p>
 * Direct formatting (this element)
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@XmlAccessorType(XmlAccessType.FIELD)
public class DJMRunProperties {

    @XmlElement
    DJMColor color;
    @XmlElement(name = "rFonts")
    DJMFont font;
    @XmlElement(name = "sz")
    FontSize fontSize;
    @XmlElement(name = "b")
    @XmlJavaTypeAdapter(BoldAdapter.class)
    @Getter(AccessLevel.NONE)
    Boolean isBold = false;

    public Boolean isBold() {
        return isBold;
    }
}
