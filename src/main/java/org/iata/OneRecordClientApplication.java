package org.iata;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.logging.LogManager;

@SpringBootApplication(scanBasePackages = "org.iata.resource")
public class OneRecordClientApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    LogManager.getLogManager().reset();
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    new OneRecordClientApplication()
        .configure(new SpringApplicationBuilder(OneRecordClientApplication.class))
        .registerShutdownHook(true).application().run(args);
  }

  @Bean
  public RestTemplate getRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();

    KeyStore keyStore;
    HttpComponentsClientHttpRequestFactory requestFactory = null;

    try {
      keyStore = KeyStore.getInstance("jks");
      ClassPathResource classPathResource = new ClassPathResource("iata03_demo_certificate.jks");
      InputStream inputStream = classPathResource.getInputStream();
      keyStore.load(inputStream, "secret".toCharArray()); // TODO remove hardcoded password

      SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(new SSLContextBuilder()
          .loadTrustMaterial(null, new TrustSelfSignedStrategy())
          .loadKeyMaterial(keyStore, "secret".toCharArray()).build(), // TODO remove hardcoded password
          NoopHostnameVerifier.INSTANCE);

      HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
          .setMaxConnTotal(5)
          .setMaxConnPerRoute(5)
          .build();

      requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
      requestFactory.setReadTimeout(10000);
      requestFactory.setConnectTimeout(10000);

      restTemplate.setRequestFactory(requestFactory);
    } catch (Exception exception) {
      System.out.println("Exception occurred while creating restTemplate " + exception);
      exception.printStackTrace();
    }

    return restTemplate;
  }

}
