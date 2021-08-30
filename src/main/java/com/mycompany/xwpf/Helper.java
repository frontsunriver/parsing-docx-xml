package com.mycompany.xwpf;

import java.util.List;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPageMargins;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

public class Helper {
	public String getFullText(XWPFDocument xdoc) {
		List<XWPFParagraph> paragraphList = xdoc.getParagraphs();
		String str = "";
		for (XWPFParagraph paragraph : paragraphList) {
			if(paragraph.getRuns().size() > 0) {
				
				for (XWPFRun rn : paragraph.getRuns()) {
					String text = rn.text();
					//text = text.replaceAll("\t", "");
					str += text;
				}
				str += "\n";
			}else {
				str += "\n";
			}
		}
		return str;
	}
	
	public String getParagrapData(XWPFDocument xdoc) {
		List<XWPFParagraph> paragraphList = xdoc.getParagraphs();
		String xmlStr = "<elements resolver=\"hvl-default\" >\n";
		int startOffset = 0;
		for (XWPFParagraph paragraph : paragraphList) {
			int align = 0;
			if(paragraph.getAlignment().toString().equals("CENTER")) {
				align = 1;
			}else if(paragraph.getAlignment().toString().equals("LEFT")) {
				align = 0;
			}else {
				align = 1;
			}
			xmlStr += "<paragraph Alignment=\"" + align + "\">\n";
//			System.out.println(paragraph.getText());
//			System.out.print(paragraph.getRuns().size());
//			System.out.println(paragraph.getStyle());
//
//			System.out.println(paragraph.getNumFmt());
//			System.out.println(paragraph.getAlignment());

//			System.out.println(paragraph.isWordWrapped());
			if(paragraph.getRuns().size() > 0) {
				
				for (XWPFRun rn : paragraph.getRuns()) {
//					System.out.println("bold------" + rn.isBold());
//					System.out.println("bold------" + rn.getFontSize());
//					System.out.println("bold------" + rn.getFontFamily());
//					System.out.println("position---------" + rn.getTextPosition());
					String text = rn.text();
					//text = text.replaceAll("\t", "        ");
					xmlStr += "<content Alignment=\"" + align + "\" bold=\"" + rn.isBold() + "\" family=\"" + rn.getFontFamily() + "\" size=\"" + rn.getFontSize() + "\" foreground=\"-16777216\" startOffset=\"" + startOffset + "\" length=\"" + text.length() + "\" /> \n";
					startOffset = startOffset + text.length();
					//System.out.println(rn.text());
				}
				xmlStr += "<content Alignment=\"" + align + "\" bold=\"true\" family=\"Times New Roman\" size=\"12\" startOffset=\"" + startOffset + "\" length=\"1\" />\n";
				startOffset += 1;
			}else {
				xmlStr += "<content Alignment=\"" + align + "\" bold=\"true\" family=\"Times New Roman\" size=\"12\" startOffset=\"" + startOffset + "\" length=\"1\" />\n";
				startOffset += 1;
			}
			xmlStr += "</paragraph>\n";
		}
		xmlStr += "</elements>\n";
		return xmlStr;
	}
	
	public int getPageCount(XWPFDocument xdoc) {
		int numPages = xdoc.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
		return numPages;
	}
	
	public void makeXml(XWPFDocument xdoc) {
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
		xmlStr += "<template format_id=\"1.7\" >\n";
		xmlStr += "<content><![CDATA[\n";
		String fullText = getFullText(xdoc);
		xmlStr += fullText;
		xmlStr += "\n";
		xmlStr += "]]></content> \n";
		xmlStr += "<properties>\n";
		xmlStr += "<pageFormat mediaSizeName=\"1\" leftMargin=\"53.85826740264892\" rightMargin=\"34.015747833251936\" topMargin=\"28.34645652770996\" bottomMargin=\"56.69291305541992\" paperOrientation=\"1\" headerFOffset=\"20.0\" footerFOffset=\"20.0\" />\n";
		xmlStr += "</properties>\n";
		xmlStr += getParagrapData(xdoc);
		xmlStr += "<styles>\n";
		xmlStr += "<style name=\"default\" description=\"Geçerli\" family=\"Dialog\" size=\"12\" bold=\"false\" italic=\"false\" foreground=\"-13421773\" FONT_ATTRIBUTE_KEY=\"javax.swing.plaf.FontUIResource[family=Dialog,name=Dialog,style=plain,size=12]\" />\n";
		xmlStr += "<style name=\"hvl-default\" family=\"Times New Roman\" size=\"12\" description=\"Gövde\" />\n";
		xmlStr += "</styles>\n";
		xmlStr += "</template>\n";
		System.out.println(xmlStr);
	}
}
