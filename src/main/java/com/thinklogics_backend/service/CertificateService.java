package com.thinklogics_backend.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class CertificateService {

    public byte[] generateCertificate(String userName) throws IOException {
        // Load your PDF certificate template
        PDDocument document = PDDocument.load(getClass().getResourceAsStream("/path/to/certificate_template.pdf"));

        // Access the first page of the PDF (assuming certificate has only one page)
        PDPage page = document.getPage(0);

        // Create a content stream to write the user's name
        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.newLineAtOffset(100, 100); // Set coordinates where the name should be placed
        contentStream.showText(userName); // Replace this with your placeholder logic
        contentStream.endText();
        contentStream.close();

        // Save the updated PDF as a byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.save(byteArrayOutputStream);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
