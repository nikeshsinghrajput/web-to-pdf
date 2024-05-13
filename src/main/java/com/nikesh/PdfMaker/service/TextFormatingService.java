package com.nikesh.PdfMaker.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class TextFormatingService {

//	public String formatingProcess(String websiteContent) {
//		 
//		Document doc = Jsoup.parse(websiteContent, "UTF-8");
//        StringBuilder extractedContent = new StringBuilder();
//        System.out.println("String builder object created");
//     // Extract text content under <p> tags
//     // Extract text content under <p> tags
//        org.jsoup.select.Elements paragraphs = doc.getElementsByTag("p");
//        for (Element paragraph : paragraphs) {
//        	System.out.println("paragraph => "+paragraph.text());
//            extractedContent.append("<h2>").append(paragraph.text()).append("</h2>");
//        }
//        return extractedContent.toString();
//	}
	
	public String formatingProcess(String websiteContent) {
	    Document doc = Jsoup.parse(websiteContent, "UTF-8");
	    System.out.println("StringBuilder object created");

	    // StringBuilder to store extracted content
	    StringBuilder extractedContent = new StringBuilder();

	    // Extract text content under <p>, <h1>, <h2>, and <strong> tags
	    Elements elements = doc.select("p, h1, h2, strong");
	    for (Element element : elements) {
	        // Append the text content of the element
	        if (element.tagName().equals("p"))
	            extractedContent.append("\n<h3>").append(element.text()).append("</h3>");
	        else if (element.tagName().equals("h1"))
	            extractedContent.append("\n<h1>").append(element.text()).append("</h1>");
	        else if (element.tagName().equals("h2"))
	            extractedContent.append("\n<h2>").append(element.text()).append("</h2>");
	        else if (element.tagName().equals("strong"))
	            extractedContent.append("\n<b>").append(element.text()).append("</b>");
	        else
	            extractedContent.append(" ");
	    }

	    // Return the extracted content
	    return extractedContent.toString();
	}


}
