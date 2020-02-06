# Usage

First, it is required to add the following to the current system `hosts`:

```
127.0.0.1 contoso.com
```

Then, this client can be tested like this against the forked `one-record-server-java` at https://github.com/hablutzel1/one-record-server-java:

```
mvn package
java -Djavax.net.ssl.keyStore=demo_client_certificate.jks -Djavax.net.ssl.keyStorePassword=secret -Djavax.net.ssl.trustStore=truststore.jks -jar target\resttemplate-client-with-mutual-ssl-authentication-demo-1.0-SNAPSHOT-jar-with-dependencies.jar
```
Where the `javax.net.ssl.trustStore` system property could be omitted if the server side SSL certificate comes from a publicly-trusted CA.
