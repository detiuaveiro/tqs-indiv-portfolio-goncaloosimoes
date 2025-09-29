package Lab2_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Optional;

import org.example.Lab2_2.HttpClient;
import org.example.Lab2_2.Product;
import org.example.Lab2_2.ProductService;

public class ProductServiceTest {

    @Mock
    private HttpClient httpClient;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(httpClient);
    }

    @Test
    void whenGetProduct_thenReturnProduct() throws IOException {
        // prepare mock response
        String jsonResponse = """
                {
                    "id": 1,
                    "title": "Fjallraven - Foldsack No. 1 Backpack",
                    "price": 109.95,
                    "category": "men's clothing",
                    "description": "Your perfect pack for everyday use and walks in the forest.",
                    "image": "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
                }
                """;

        when(httpClient.get("https://fakestoreapi.com/products/1")).thenReturn(jsonResponse);

        // execute
        Optional<Product> productOpt = productService.findProductDetails(1L);

        // verify
        assertThat(productOpt).isPresent();
        Product product = productOpt.get();
        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getTitle()).isEqualTo("Fjallraven - Foldsack No. 1 Backpack");
        assertThat(product.getPrice()).isEqualTo(109.95);
        assertThat(product.getCategory()).isEqualTo("men's clothing");
        assertThat(product.getDescription()).contains("Your perfect pack");
    }

    @Test
    void whenGetProduct_withValidId_thenReturnProduct() throws IOException {
        // prepare mock response
        String jsonResponse = """
                {
                    "id": 3,
                    "title": "Mens Cotton Jacket",
                    "price": 55.99,
                    "category": "men's clothing",
                    "description": "great outerwear jackets for Spring/Autumn/Winter",
                    "image": "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg"
                }
                """;

        when(httpClient.get("https://fakestoreapi.com/products/3")).thenReturn(jsonResponse);

        // execute
        Optional<Product> productOpt = productService.findProductDetails(3L);

        // verify
        assertThat(productOpt).isPresent();
        Product product = productOpt.get();
        assertThat(product.getId()).isEqualTo(3L);
        assertThat(product.getTitle()).isEqualTo("Mens Cotton Jacket");
    }

    @Test
    void whenGetProduct_withInvalidId_thenReturnEmpty() throws IOException {
        // prepare mock to simulate 404 or error response
        when(httpClient.get("https://fakestoreapi.com/products/300"))
            .thenThrow(new IOException("Product not found"));

        // execute
        Optional<Product> productOpt = productService.findProductDetails(300L);

        // verify
        assertThat(productOpt).isEmpty();
    }
}