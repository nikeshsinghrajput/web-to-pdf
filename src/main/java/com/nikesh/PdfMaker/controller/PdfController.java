package com.nikesh.PdfMaker.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.nikesh.PdfMaker.service.TextFormatingService;

@RestController
public class PdfController {

    @Autowired
    private WebClient webClient;
    @Autowired
    private TextFormatingService textFormatingService;

    @GetMapping("/generate-pdf")
	public ResponseEntity<byte[]> generatePdfFromUrl(@RequestParam String url)
			throws IOException, ExecutionException, InterruptedException, DocumentException {
		System.out.println("url"+url);
        String htmlContent = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(outputStream);
        renderer.finishPDF();
        byte[] pdfBytes = outputStream.toByteArray();

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf")
                .body(pdfBytes);
    }
//	 @GetMapping("/website-content")
//	    public ResponseEntity<byte[]> getWebsiteContent(@RequestParam String url) {
//	        String websiteContent = "";
//	        try {
//	            RestTemplate restTemplate = new RestTemplate();
//	            websiteContent = restTemplate.getForObject(url, String.class);
//	            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//	            ITextRenderer renderer = new ITextRenderer();
//	            renderer.setDocumentFromString(websiteContent);
//	            renderer.layout();
//	            renderer.createPDF(outputStream);
//	            renderer.finishPDF();
//	            byte[] pdfBytes = outputStream.toByteArray();
//
//	            return ResponseEntity
//	                    .ok()
//	                    .header("Content-Type", "application/pdf")
//	                    .body(pdfBytes);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return ResponseEntity.badRequest();
//	            
//	        }
//	       
//	    }
	
//	@GetMapping("/website-content")
//    public ResponseEntity<byte[]> getWebsiteContent(@RequestParam String url) {
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//            String websiteContent = restTemplate.getForObject(url, String.class);
//
//            // Extract content under <p> tags
//            String[] paragraphs = websiteContent.split("<p>*</p>");
//            System.out.println("paragraphs : "+paragraphs.toString());
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            ITextRenderer renderer = new ITextRenderer();
//            System.out.println("render instance created ");
//            // Construct HTML containing only <p> tag content
//            StringBuilder htmlBuilder = new StringBuilder();
//            htmlBuilder.append("<html><body>");
//            for (String paragraph : paragraphs) {
//                htmlBuilder.append(paragraph.trim());
//            }
//            htmlBuilder.append("</body></html>");
//            System.out.println("htmlbuilder append");
//            // Render HTML to PDF
//            renderer.setDocumentFromString(htmlBuilder.toString());
//            System.out.println("string is set in document ");
//            renderer.layout();
//            System.out.println("layout set in document ");
//            renderer.createPDF(outputStream);
//            System.out.println(" document is created");
//            renderer.finishPDF();
//            byte[] pdfBytes = outputStream.toByteArray();
//            System.out.println("pdfbytes : "+pdfBytes);
//            // Return PDF in the response
//            return ResponseEntity
//                    .ok()
//                    .header("Content-Type", "application/pdf")
//                    .body(pdfBytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().build();
//        }
//    }
	
	@GetMapping("/website-content")
	public ResponseEntity<byte[]> getWebsiteContent(@RequestParam String url) {
	    try {
	        RestTemplate restTemplate = new RestTemplate();
	        String websiteContent = restTemplate.getForObject(url, String.class);
	        System.out.println("website content : "+websiteContent);
	        // Parse HTML content using Jsoup
	       String extractedContent=   textFormatingService.formatingProcess(websiteContent);
	        

	        System.out.println("paragraphs set");
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        ITextRenderer renderer = new ITextRenderer();
	      
	        // Render extracted content to PDF
	        String xmlContent = "<root>" + extractedContent + "</root>";
	        System.out.println("render object created"+xmlContent);
	     // Render extracted content to PDF
	     renderer.setDocumentFromString(xmlContent);
            
	        System.out.println("render content save");
	        renderer.layout();
	        renderer.createPDF(outputStream);
	        renderer.finishPDF();
	        byte[] pdfBytes = outputStream.toByteArray();

	        // Return PDF in the response
	        return ResponseEntity
	                .ok()
	                .header("Content-Type", "application/pdf")
	                .body(pdfBytes);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.badRequest().build();
	    }
	}
	/*	@GetMapping("/website-content")
	public ResponseEntity<byte[]> getWebsiteContent(@RequestParam String url) {
	    try {
	        RestTemplate restTemplate = new RestTemplate();
	        String websiteContent = restTemplate.getForObject(url, String.class);
	        System.out.println("website content : "+websiteContent);
	        // Parse HTML content using Jsoup
	       String extractedContent=   textFormatingService.formatingProcess(websiteContent);
	        

	        System.out.println("paragraphs set");
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Create a content stream for writing to the PDF
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Set font and font size
            contentStream.setFont(PDType1Font.HELVETICA, 12);


            // Write text to the PDF
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 800 - 50); // Adjust position based on height
            contentStream.showText(extractedContent);
            contentStream.endText();
            contentStream.close();
	        byte[] pdfBytes = outputStream.toByteArray();

	        // Return PDF in the response
	        return ResponseEntity
	                .ok()
	                .header("Content-Type", "application/pdf")
	                .body(pdfBytes);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.badRequest().build();
	    }
	}*/
}