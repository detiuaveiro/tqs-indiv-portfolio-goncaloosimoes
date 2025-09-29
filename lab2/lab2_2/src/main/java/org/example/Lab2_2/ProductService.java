package org.example.Lab2_2;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;

public class ProductService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final String BASE_URL = "https://fakestoreapi.com/products/";

    public ProductService(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();
    }

    public Optional<Product> findProductDetails(Long id) {
        try {
            String response = httpClient.get(BASE_URL + id);
            Product product = objectMapper.readValue(response, Product.class);
            return Optional.of(product);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}