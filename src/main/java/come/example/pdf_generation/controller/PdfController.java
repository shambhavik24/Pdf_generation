package come.example.pdf_generation.controller;

import come.example.pdf_generation.model.InvoiceRequest;
import come.example.pdf_generation.service.PdfGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfGenerationService pdfGenerationService;

    @PostMapping("/generate")
    public ResponseEntity<String> generatePdf(@RequestBody InvoiceRequest invoiceRequest) {
        try {
            String filePath = pdfGenerationService.generatePdf(invoiceRequest);
            return ResponseEntity.ok("PDF generated at: " + filePath);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
        }
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadPdf(@RequestParam String fileName) throws FileNotFoundException {
        File file = new File("invoces/" + fileName);

        if (!file.exists()) {
            return ResponseEntity.status(404).body(null);
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
