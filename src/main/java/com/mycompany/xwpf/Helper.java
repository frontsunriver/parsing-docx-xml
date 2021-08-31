package com.mycompany.xwpf;

import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class Helper {
	public String getFullText(XWPFDocument xdoc) {
		List<XWPFParagraph> paragraphList = xdoc.getParagraphs();
		String str = "";
		for (XWPFParagraph paragraph : paragraphList) {
			if(paragraph.getRuns().size() > 0) {
				
				for (XWPFRun rn : paragraph.getRuns()) {
					String text = rn.text();
					text = fixTabString(text);
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
		boolean bold = false;
		String fontFamily = "Times New Roman";
		int align = 0;
		for (XWPFParagraph paragraph : paragraphList) {
			if(paragraph.getAlignment() == ParagraphAlignment.CENTER) {
				align = 1;
			}else if(paragraph.getAlignment() == ParagraphAlignment.LEFT) {
				align = 0;
			}else if(paragraph.getAlignment() == ParagraphAlignment.RIGHT){
				align = 2;
			}else {
				align = 0;
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
					String underLine = "";
					if(rn.getUnderline() != UnderlinePatterns.NONE) {
						underLine = " underline = \"true\" ";
					}
					String text = rn.text();
					text = fixTabString(text);
					bold = rn.isBold();
					fontFamily = rn.getFontFamily();
					xmlStr += "<content Alignment=\"" + align + "\" bold=\"" + bold + "\" family=\"" + fontFamily + "\" " + underLine + " size=\"" + rn.getFontSize() + "\" foreground=\"-16777216\" startOffset=\"" + startOffset + "\" length=\"" + text.length() + "\" /> \n";
					startOffset = startOffset + text.length();
				}
				xmlStr += "<content Alignment=\"" + align + "\" bold=\"" + bold + "\" family=\"" + fontFamily + "\" size=\"12\" startOffset=\"" + startOffset + "\" length=\"1\" />\n";
				startOffset += 1;
			}else {
				xmlStr += "<content Alignment=\"" + align + "\" bold=\"" + bold + "\" family=\""+ fontFamily +"\" size=\"12\" startOffset=\"" + startOffset + "\" length=\"1\" />\n";
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
	
	public int getTabCount(String str) {
		int result = 0;
		for(char c : str.toCharArray()) {
			if("\t".equals("" + c)) {
				result++;
			}
		}
		return result;
	}
	
	public String fixTabString(String str) {
		String finalString = "";
		if(getTabCount(str) > 2) {
			int tabCount = 0;
			for(char c : str.toCharArray()) {
				if("\t".equals("" + c)) {
					if(tabCount == 2) {
						finalString += "";
					}else {
						finalString += String.valueOf(c);
						tabCount++;
					}
				}else {
					finalString += String.valueOf(c);
				}
			}
		}else {
			finalString = str;
		}
		return finalString;
	}
	
	public String makeXml(XWPFDocument xdoc) {
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
		return xmlStr;
	}
}
