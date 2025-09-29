package org.example.Lab2_2;

import java.io.IOException;

public interface HttpClient {
    String get(String url) throws IOException;
}