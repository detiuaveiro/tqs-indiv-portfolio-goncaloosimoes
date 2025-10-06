/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.connection;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author ico
 */
public class TqsBasicHttpClient implements ISimpleHttpClient {

    @Override
    public String doHttpGet(String url) throws IOException {
        // create a default http client
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = client.execute(request);
            // execute the request
            try {
                HttpEntity entity = response.getEntity();
                // return it as a String if present
                return EntityUtils.toString(entity);
            } finally {
                if (response != null)
                    response.close();
            }
        }
    }

}
