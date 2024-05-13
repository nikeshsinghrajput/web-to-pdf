package com.nikesh.PdfMaker.entity;

import org.jsoup.nodes.Document;

public class Content {
	private String websiteContent;
	private Document doc;
	private String formatingContent;
	public String getWebsiteContent() {
		return websiteContent;
	}
	public void setWebsiteContent(String websiteContent) {
		this.websiteContent = websiteContent;
	}
	public Document getDoc() {
		return doc;
	}
	public void setDoc(Document doc) {
		this.doc = doc;
	}
	public String getFormatingContent() {
		return formatingContent;
	}
	public void setFormatingContent(String formatingContent) {
		this.formatingContent = formatingContent;
	}
	@Override
	public String toString() {
		return "Content [websiteContent=" + websiteContent + ", doc=" + doc + ", formatingContent=" + formatingContent
				+ "]";
	}
	
	

}
