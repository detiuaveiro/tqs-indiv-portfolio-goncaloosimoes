package integration;

import org.connection.ISimpleHttpClient;
import org.connection.TqsBasicHttpClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TqsBasicHttpClientIT {

    private ISimpleHttpClient client = new TqsBasicHttpClient();

    @Test
    void whenDoHttpGet_thenReturnRealResponse() throws Exception {
        // Example: hitting a public API (replace with your target)
        String url = "https://jsonplaceholder.typicode.com/posts/1";

        String response = client.doHttpGet(url);

        assertNotNull(response);
        assertTrue(response.contains("userId"));  // crude validation, depends on expected payload
    }
}
