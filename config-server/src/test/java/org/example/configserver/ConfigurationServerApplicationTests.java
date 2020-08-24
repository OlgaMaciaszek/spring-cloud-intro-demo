package org.example.configserver;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.config.server.environment.EnvironmentController;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Olga Maciaszek-Sharma
 */
@SpringBootTest
class ConfigurationServerApplicationTests {

	@Autowired
	EnvironmentController environmentController;

	@Test
	void contextLoads() {
		assertThat(environmentController).isNotNull();
	}

}