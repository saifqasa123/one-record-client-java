package org.iata.resource;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping(value="/client")
public class ClientResource {
	private final RestTemplate restTemplate;
	private final Environment env;

	@Inject
	public ClientResource(RestTemplate restTemplate, Environment env) {
		this.restTemplate = restTemplate;
		this.env = env;
	}

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public String getData() {
		System.out.println("Returning data from nt-gateway own data method");
		return "Hello from client-data method";
	}
	
	@RequestMapping(value = "/server-data", method = RequestMethod.GET)
	public String getServerData() {
		System.out.println("Got inside server-data method");
		try {
			String serverEndpoint = env.getProperty("endpoint.one-record-server-service");
			System.out.println("One Record Server Endpoint name : [" + serverEndpoint + "]");

			return restTemplate.getForObject(new URI(Objects.requireNonNull(serverEndpoint)), String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "Exception occurred.. so, returning default data";
	}

}
