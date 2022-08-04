package com.mi.services.controller;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.mi.services.dto.AuthRequest;
import com.mi.services.dto.AuthResp;
import com.mi.services.dto.ProductDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String loginToken;

	private String baseUrl;

	@BeforeEach
	public void init() {

		baseUrl = "http://localhost:" + port;

		// api/public/login
		String loginUrl = baseUrl + "/api/public/login";

		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("demo");
		authRequest.setPassword("demo");

		HttpEntity<AuthRequest> reqEntity = new HttpEntity<AuthRequest>(authRequest);

		ResponseEntity<AuthResp> authRespEntity = restTemplate.postForEntity(loginUrl, reqEntity, AuthResp.class);

		loginToken = authRespEntity.getBody().getToken();

	}

	private HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(loginToken);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		return headers;
	}

	@Test
	@Order(1)
	public void testGetProductsForInvestor() throws Exception {

		HttpEntity<String> entity = new HttpEntity<>("", getHttpHeaders());

		ResponseEntity<ProductDTO[]> allProductsEntity = restTemplate.exchange(baseUrl + "/products/investor/1001",
				HttpMethod.GET, entity, ProductDTO[].class);

		Assertions.assertTrue(allProductsEntity.getStatusCode().is2xxSuccessful());

		Assertions.assertTrue(allProductsEntity.getBody().length > 0);

	}
}
