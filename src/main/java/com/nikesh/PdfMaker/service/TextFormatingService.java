package com.nikesh.PdfMaker.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class TextFormatingService {

	public String formatingProcess(String websiteContent) {
		 
		Document doc = Jsoup.parse(websiteContent, "UTF-8");
        StringBuilder extractedContent = new StringBuilder();
        System.out.println("String builder object created");
     // Extract text content under <p> tags
     // Extract text content under <p> tags
        org.jsoup.select.Elements paragraphs = doc.getElementsByTag("p");
        for (Element paragraph : paragraphs) {
        	System.out.println("paragraph => "+paragraph.text());
            extractedContent.append(paragraph.text()).append("\n");
        }
        return extractedContent.toString();
	}

}
