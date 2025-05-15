package com.abc.product.client;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author amlandeep.nandi
 *
 */
@SpringBootApplication
public class ProductClientApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(ProductClientApplication.class, args);
	}

	@LoadBalanced
	@Bean
	RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder)
	{
		return restTemplateBuilder
				.connectTimeout(Duration.ofSeconds(500))
				.readTimeout(Duration.ofSeconds(500))
				.build();
	}
}
