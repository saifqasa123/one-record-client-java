package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

public class ClientDemo {
  public static void main(String[] args) {
    RestTemplate restTemplate = new RestTemplate();
    try {
      ResponseEntity<String> response = restTemplate
          .getForEntity("https://contoso.com:8443/sslclientauthenticationtest/", String.class);
      System.out.println("Response coming from server -> " + response.getBody());
    } catch (HttpClientErrorException ex) {
      System.out.println("Error coming from server -> " + ex.getResponseBodyAsString());
    }
  }
}
