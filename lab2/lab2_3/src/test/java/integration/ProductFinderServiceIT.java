package integration;

import org.connection.TqsBasicHttpClient;
import org.junit.jupiter.api.Test;
import org.connection.ProductFinderService;

import static org.junit.jupiter.api.Assertions.*;

public class ProductFinderServiceIT {

    // Aqui usamos o cliente real, nada de mocks!
    private ProductFinderService service = new ProductFinderService(new TqsBasicHttpClient());

    @Test
    void whenFetchingRealProduct_thenResponseContainsExpectedData() throws Exception {
        // Exemplo com uma API pública de teste
        String productId = "1";
        String response = service.searchProduct("https://jsonplaceholder.typicode.com/posts/" + productId);

        assertNotNull(response, "A resposta não pode ser nula");
        assertTrue(response.contains("\"id\": 1"), "A resposta deve conter o id=1");
    }

    @Test
    void whenFetchingInvalidUrl_thenThrowsException() {
        assertThrows(Exception.class, () -> {
            service.searchProduct("http://invalid.url.abc/");
        });
    }
}
