package com.mycompany.xwpf;

import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;

public class Helper {
	public String getFullText(XWPFDocument xdoc) {
		List<XWPFParagraph> paragraphList = xdoc.getParagraphs();
		String str = "";
		String beforeString = "";
		for (XWPFParagraph paragraph : paragraphList) {
			if(paragraph.getRuns().size() > 0) {
				
				for (XWPFRun rn : paragraph.getRuns()) {
					String text = rn.text();
					text = fixTabString(checkCount(beforeString), text);
					if(rn.getUnderline() != UnderlinePatterns.NONE) {
						str += text + " ";
						beforeString = text + " ";
					}else {
						str += text;
						beforeString = text;
					}
				}
				str += " ";
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
		String beforeString = "";
		int fontSize = 12;
		for (XWPFParagraph paragraph : paragraphList) {
			String firstLineIndent = "";
			if(paragraph.getAlignment() == ParagraphAlignment.CENTER) {
				align = 1;
			}else if(paragraph.getAlignment() == ParagraphAlignment.LEFT) {
				align = 0;
			}else if(paragraph.getAlignment() == ParagraphAlignment.RIGHT){
				align = 2;
			}else {
				align = 0;
			}
			int lineIndent = paragraph.getFirstLineIndent();
			if(lineIndent != -1) {
				firstLineIndent = " FirstLineIndent=\"" + changeIndent(paragraph.getFirstLineIndent()) + "\"";
			}
			
			xmlStr += "<paragraph Alignment=\"" + align + "\"" + firstLineIndent + ">\n";
			if(paragraph.getRuns().size() > 0) {
				for (XWPFRun rn : paragraph.getRuns()) {
					String text = rn.text();
					text = fixTabString(checkCount(beforeString), text);
					bold = rn.isBold();
					if(text.equals(" ")) {
						bold = false;
					}
					fontFamily = rn.getFontFamily();
					if(fontFamily == null) {
						fontFamily = "Times New Roman";
					}
					fontSize = rn.getFontSize();
					if(fontSize == -1) {
						fontSize = 11;
					}
					String underLine = "";
					if(rn.getUnderline() != UnderlinePatterns.NONE) {
						underLine = " underline = \"true\" ";
						xmlStr += "<content Alignment=\"" + align + "\" bold=\"" + bold + "\" family=\"" + fontFamily + "\" " + underLine + " size=\"" + fontSize + "\" foreground=\"-16777216\" startOffset=\"" + startOffset + "\" length=\"" + (text.length() + 1) + "\" /> \n";
						startOffset = startOffset + text.length() + 1;
						beforeString = text + " ";
					}else {
						xmlStr += "<content Alignment=\"" + align + "\" bold=\"" + bold + "\" family=\"" + fontFamily + "\" " + underLine + " size=\"" + fontSize + "\" foreground=\"-16777216\" startOffset=\"" + startOffset + "\" length=\"" + (text.length()) + "\" /> \n";
						startOffset = startOffset + text.length();
						beforeString = text;
					}
				}
				xmlStr += "<content Alignment=\"" + align + "\" bold=\"" + bold + "\" family=\"" + fontFamily + "\" size=\"" + fontSize + "\" startOffset=\"" + startOffset + "\" length=\"1\" />\n";
				startOffset += 1;
				xmlStr += "<content Alignment=\"" + align + "\" bold=\"" + bold + "\" family=\"" + fontFamily + "\" size=\"" + fontSize + "\" startOffset=\"" + startOffset + "\" length=\"1\" />\n";
				startOffset += 1;
			}else {
				xmlStr += "<content Alignment=\"" + align + "\" bold=\"" + bold + "\" family=\""+ fontFamily +"\" size=\"" + fontSize + "\" startOffset=\"" + startOffset + "\" length=\"1\" />\n";
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
		int length = str.toCharArray().length;
		char [] arr = str.toCharArray();
		boolean flag = false;
		for(int i = 0; i < length  ; i++) {
			if("\t".equals("" + arr[i])) {
				flag = true;
				result ++;
			}else {
				flag = false;
			}
		}
//		for(char c : str.toCharArray()) {
//			if("\t".equals("" + c)) {
//				result++;
//			}
//		}
		return result;
	}
	
	public String fixTabString(int before, String str) {
		String finalString = "";
		if(before == 1) {
			str = "\t" + str;
		}else if(before == 2) {
			str = "\t\t" + str;
		}
		char [] strArray = str.toCharArray();
		boolean flag = false;
		int tabCount = 0;
		for(int i = 0; i < str.toCharArray().length; i++) {
			if("\t".equals("" + strArray[i])) {
				tabCount ++;
				if(tabCount == 2) {
					finalString += "";
				}else if(tabCount == 3) {
					finalString += "\t";
				}else if(tabCount > 3) {
					finalString += "";
				}else {
					finalString += "" + strArray[i];
				}
			}else {
				tabCount = 0;
				finalString += "" + strArray[i];
			}
		}
		String makeString = "";
		if(before == 1) {
			for (int i = 1; i< finalString.toCharArray().length; i++) {
				makeString += "" + finalString.toCharArray()[i];
			}
		}else if(before == 2){
			for (int i = 2; i< finalString.toCharArray().length; i++) {
				makeString += "" + finalString.toCharArray()[i];
			}
		}else {
			makeString = finalString;
		}
		
//		if(getTabCount(str) > 2) {
//			int tabCount = 0;
//			for(char c : str.toCharArray()) {
//				if("\t".equals("" + c)) {
//					if(tabCount == 2) {
//						finalString += "";
//					}else {
//						finalString += String.valueOf(c);
//						tabCount++;
//					}
//				}else {
//					finalString += String.valueOf(c);
//				}
//			}
//		}else if(getTabCount(str) == 2){
//			int tabCount = 0;
//			for(char c : str.toCharArray()) {
//				if("\t".equals("" + c)) {
//					if(tabCount == 1) {
//						finalString += "";
//					}else {
//						finalString += String.valueOf(c);
//						tabCount++;
//					}
//				}else {
//					finalString += String.valueOf(c);
//				}
//			}
//		}else {
//			finalString = str;
//		}
		return makeString;
	}
	
	public float changeIndent(int value) {
		float result = 0.0f;
		result = Float.valueOf(value) / 28;
		return result;
	}
	
	public int checkCount(String beforeString) {
		int result = 0;
		if(beforeString.length() == 0) {
			result = 0;
		}else {
			if(beforeString.length() > 1) {
				char last = beforeString.charAt(beforeString.length() - 1);
				char last1 = beforeString.charAt(beforeString.length() - 2);
				if("\t".equals("" + last)) {
					result ++;
				}
				if("\t".equals("" + last1)) {
					result ++;
				}
			}else if(beforeString.length() == 1) {
				if("\t".equals("" + beforeString.charAt(beforeString.length() - 1))) {
					result ++;
				}
			}
		}
		return result;
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
