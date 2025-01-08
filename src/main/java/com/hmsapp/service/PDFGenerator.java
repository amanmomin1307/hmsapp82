package com.hmsapp.service;

import com.hmsapp.entity.Booking;
import com.hmsapp.entity.Property;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class PDFGenerator {
    public void generatePdf(String path, Booking booking, LocalDate fromDate, LocalDate toDate, Property property){
        try {
            // Create a PdfWriter instance
            PdfWriter writer = new PdfWriter(path);

            // Create a PdfDocument instance
            PdfDocument pdfDoc = new PdfDocument(writer);

            // Create a Document instance
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Name of Property "+String.valueOf(property.getName())));
            document.add(new Paragraph("Name of Country "+String.valueOf(property.getCountry())));
            document.add(new Paragraph("Name of City "+String.valueOf(property.getCity())));


            // Define the number of columns in the table
            float[] columnWidths = {5,10,10,10};
            Table table = new Table(columnWidths);

            // Add table headers
            table.addHeaderCell("Name");
            table.addHeaderCell("No of days");
            table.addHeaderCell("Date of arrival");
            table.addHeaderCell("Date till");

            // Add sample rows to the table
            table.addCell(booking.getGuestName());
            table.addCell(booking.getNoOfGuest());
            table.addCell(String.valueOf(fromDate));
            table.addCell(String.valueOf(toDate));

            // Add the table to the document
            document.add(table);

            // Close the document
            document.close();

            System.out.println("PDF created successfully: " + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
