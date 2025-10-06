package org.connection;

import org.connection.ISimpleHttpClient;

public class ProductFinderService {

    private final ISimpleHttpClient httpClient;

    // Injeção de dependência via construtor
    public ProductFinderService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Busca informações de um produto através de uma URL.
     * Pode ser uma API real ou um endpoint fake de teste.
     */
    public String searchProduct(String url) throws Exception {
        return httpClient.doHttpGet(url);
    }
}
