package io.github.olgamaciaszek.cardservice.user;

import io.github.olgamaciaszek.cardservice.application.CardApplicationDto;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Olga Maciaszek-Sharma
 */
@Component
@RefreshScope
public class UserServiceClient {

	private final RestTemplate restTemplate;
	private final DiscoveryClient discoveryClient;
	private final String gatewayServiceId;


	UserServiceClient(@Qualifier("restTemplate") RestTemplate restTemplate,
			DiscoveryClient discoveryClient, @Value("${gateway.serviceid}") String gatewayServiceId) {
		this.restTemplate = restTemplate;
		this.discoveryClient = discoveryClient;
		this.gatewayServiceId = gatewayServiceId;
	}

	public ResponseEntity<User> registerUser(CardApplicationDto.User userDto) {
		ServiceInstance instance = discoveryClient.getInstances(gatewayServiceId)
				.stream().findAny()
				.orElseThrow(() -> new IllegalStateException("Proxy unavailable"));
		return restTemplate.postForEntity(instance.getUri().toString()
						+ "/user-service/registration",
				userDto,
				User.class);
	}
}