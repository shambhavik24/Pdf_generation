package come.example.pdf_generation.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import come.example.pdf_generation.model.InvoiceRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class PdfGenerationService {

    public String generatePdf(InvoiceRequest invoiceRequest) throws Exception {
        String pdfFileName = generateFileName(invoiceRequest);
        File directory = new File("invoces");


        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, pdfFileName);
        if (file.exists()) {
            return file.getAbsolutePath();
        }


        PdfWriter writer = new PdfWriter(new FileOutputStream(file));
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Invoices details"));
        document.add(new Paragraph("Seller: " + invoiceRequest.getSeller()));
        document.add(new Paragraph("Buyer: " + invoiceRequest.getBuyer()));

        Table table = new Table(new float[]{4, 2, 2, 2});
        table.addCell(new Cell().add(new Paragraph("Item Name")));
        table.addCell(new Cell().add(new Paragraph("Quantity")));
        table.addCell(new Cell().add(new Paragraph("Rate")));
        table.addCell(new Cell().add(new Paragraph("Amount")));

        for (InvoiceRequest.Item item : invoiceRequest.getItems()) {
            table.addCell(new Cell().add(new Paragraph(item.getName())));
            table.addCell(new Cell().add(new Paragraph(item.getQuantity())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getRate()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getAmount()))));
        }

        document.add(table);
        document.close();

        System.out.println("FIle generated at ======" + file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    private String generateFileName(InvoiceRequest invoiceRequest) {
        return invoiceRequest.getSeller().replaceAll("\\s+", "_") + "_"
                + invoiceRequest.getBuyer().replaceAll("\\s+", "_") + ".pdf";
    }
}
