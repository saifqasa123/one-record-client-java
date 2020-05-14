# Usage

First, it is required to add the following to the current system `hosts`:

```
127.0.0.1 contoso.com
```

Then, this client can be tested like this against the forked `one-record-server-java` at https://github.com/hablutzel1/one-record-server-java:

```
mvn package
java -Djavax.net.ssl.keyStore=iata03_demo_certificate.jks -Djavax.net.ssl.keyStorePassword=secret -Djavax.net.ssl.trustStore=truststore_bgca1_changeit.jks -Djavax.net.ssl.trustStorePassword=changeit -jar target\resttemplate-client-with-mutual-ssl-authentication-demo-1.0-SNAPSHOT-jar-with-dependencies.jar
```
Where the `javax.net.ssl.trustStore` system property could be omitted if the server side SSL certificate comes from a publicly-trusted CA.

##Useful commands

`keytool -v -list -keystore iata03_demo_certificate.jks
`
Following command is used to import a cert to the browser. As the browser can’t understand a .jks file. Instead, it understands PKCS12 format. So, how we need to convert .jks file to PKCS12 format.
`keytool -importkeystore -srckeystore iata03_demo_certificate.jks -destkeystore iata03.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass secret -deststorepass secret -srcalias "iataclient03 (wisekey certifyi
d personal gb ca 1)" -destalias "iataclient03 (wisekey certifyid personal gb ca 1)" -srckeypass secret -destkeypass secret -noprompt
`
# TODO some useful commands
keytool -import -trustcacerts -alias onerecordclient -file 2D9E53408E3D8CAE5862FCA6CDEE6CB645EC23C8.crt -keystore iata03_demo_certificate.jks
keytool -importkeystore -srckeystore 65D37B7663E8169D38C75F4EDA06049D09F7839E.pfx -srcstoretype pkcs12 -destkeystore clientcert.jks -deststoretype JKS
keytool -importkeystore -srckeystore one-record-server-tls.herokuapp.com.p12 -srcstoretype pkcs12 -destkeystore client.jks -deststoretype jks

# Remove passphrase from pem file (needed for Heroku)
openssl rsa -in key.pem -out newkey.pem

openssl s_client -connect one-record-server-tls.herokuapp.com:443 -servername https://one-record-server-tls.herokuapp.com/

curl -vI https://one-record-server-tls.herokuapp.com:443

See https://www.trustico.com/install/import/iis7/iis7-pfx-installation.php on how to import the PKCS12 (PFX) file for Windows.

TODO
mvn package -DskipTests=true -Dpath.to.nginx.ssl_certificate=src/main/resources/ssl_certificate.crt -Dpath.to.nginx.ssl_certificate_key=src/main/resources/ssl_certificate_key.key -Dpath.to.nginx.ssl_client_certificate=src/main/resources/ssl_client_certificate.crt