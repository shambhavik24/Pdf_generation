package come.example.pdf_generation;

import come.example.pdf_generation.model.InvoiceRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PdfGenerationApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGeneratePdf(){
		InvoiceRequest invoiceRequest = new InvoiceRequest();

		ResponseEntity<String> response = restTemplate.postForEntity("/api/pdf/generate",invoiceRequest,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}
	@Test
	public void testDownloadPdf(){
		ResponseEntity<String> response = restTemplate.getForEntity("/api/pdf/download", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}





}
