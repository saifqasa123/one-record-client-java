package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

public class ClientDemo {
  public static void main(String[] args) {
    final String token = "Bearer eyJhbGciOiJSUzI1NiIsImprdSI6Imh0dHBzOlwvXC9jaWRvaWRjLmNlcnRpZnlpZGRlbW8uY29tXC9vcFwvb3AuandrIiwia2lkIjoiU0lHIn0.eyJpc3MiOiJodHRwczpcL1wvY2lkb2lkYy5jZXJ0aWZ5aWRkZW1vLmNvbSIsInN1YiI6Imh1bmd0cUB3aXNla2V5LmNvbSIsImF1ZCI6WyJxYW1QcmtWRGZ0a2JYYkQ4Uzc5SmFXRHBwWnhkMktwbiJdLCJleHAiOjE1OTAwMzUzODQsImlhdCI6MTU5MDAzNTA4NCwibm9uY2UiOiI1emZqbWVTemplSmh4dUUyV1k5RERYNGhWT1VzTF9YTGlIWmduejkyZkcwIiwiYXRfaGFzaCI6ImtUREJiMm8zdWtERVlDZ2xhOXc2Z0EifQ.CXDIyqsMxV4dmNE0nsPj1ps_8EVlzTz44iiHFxsbnrk7j3GSxwaTe_Shfsmz2bjIDnZnOeV5e9ViiXv0AmjMBBQvlCdCegccnI_3Sf_s-Y78yHzXXRCLuNWfdVucJ0BAyrYK61kPtFgU75M-IjlBZlS7XED8QWfkmAufgaU2QpV6XIzpukxJjJw01wnjX1uwUfAcWKXcPvbrdVbcvSxRpLJOsq6KPSxEb_SdyIIKK7csYFiVPMv_bWoNnufa_ISUwttEQbi5GxCJMOnDYQDI8B-m411KBkt8i8_cclEUYMKOzIxPDE7WuaOpiHbje-GIFrhC60XuRMmACXL3vWf99w";
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", token);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);
    try {
      ResponseEntity<String> response = restTemplate
        .exchange("https://contoso.com:8443/sslclientauthenticationtest", HttpMethod.GET, entity, String.class);
        // .getForEntity("https://contoso.com:8443/sslclientauthenticationtest/", String.class);
      System.out.println("Response coming from server -> " + response.getBody());
    } catch (HttpClientErrorException ex) {
      System.out.println("Error coming from server -> " + ex.getResponseBodyAsString());
    } catch (Exception e){
      System.out.println("Error -> " + e.getMessage());
      e.printStackTrace();
    }
  }
}
