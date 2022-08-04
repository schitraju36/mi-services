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
import com.mi.services.dto.InvestorDTO;
import com.mi.services.dto.WithdrawlDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class InvestorControllerTest {

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
	public void testGetInvestors() throws Exception {

		HttpEntity<String> entity = new HttpEntity<>("", getHttpHeaders());

		ResponseEntity<InvestorDTO[]> allInvestorsEntity = restTemplate.exchange(baseUrl + "/investor", HttpMethod.GET,
				entity, InvestorDTO[].class);

		Assertions.assertTrue(allInvestorsEntity.getStatusCode().is2xxSuccessful());

		Assertions.assertTrue(allInvestorsEntity.getBody().length > 0);

	}

	// withdrawl
	@Test
	@Order(2)
	public void testWithdrawlWhenInvestorIdIsNull() throws Exception {
		WithdrawlDTO withdrawlDTO = new WithdrawlDTO();

		HttpEntity<WithdrawlDTO> entity = new HttpEntity<WithdrawlDTO>(withdrawlDTO, getHttpHeaders());

		ResponseEntity withdrawlStatus = restTemplate.exchange(baseUrl + "/investor/withdrawl", HttpMethod.POST, entity,
				String.class);

		Assertions.assertTrue(withdrawlStatus.getStatusCode().is4xxClientError());
	}

	@Test
	@Order(3)
	public void testWithdrawlWhenProductIdIsInvalid() throws Exception {
		WithdrawlDTO withdrawlDTO = new WithdrawlDTO();
		withdrawlDTO.setInvestorId(1001l);
		withdrawlDTO.setAmount(2563.0);
		withdrawlDTO.setProductId(5001l);

		HttpEntity<WithdrawlDTO> entity = new HttpEntity<WithdrawlDTO>(withdrawlDTO, getHttpHeaders());

		ResponseEntity withdrawlStatus = restTemplate.exchange(baseUrl + "/investor/withdrawl", HttpMethod.POST, entity,
				String.class);

		Assertions.assertTrue(withdrawlStatus.getStatusCode().is4xxClientError());
	}

	@Test
	@Order(4)
	public void testWithdrawlWhenWithdrawlAmountIsGreaterThanCurrentBalance() throws Exception {
		WithdrawlDTO withdrawlDTO = new WithdrawlDTO();
		withdrawlDTO.setInvestorId(1001l);
		withdrawlDTO.setAmount(612563.0);
		withdrawlDTO.setProductId(2001l);

		HttpEntity<WithdrawlDTO> entity = new HttpEntity<WithdrawlDTO>(withdrawlDTO, getHttpHeaders());

		ResponseEntity<String> withdrawlStatus = restTemplate.exchange(baseUrl + "/investor/withdrawl", HttpMethod.POST,
				entity, String.class);

		Assertions.assertTrue(withdrawlStatus.getStatusCode().is4xxClientError());

		Assertions.assertTrue(withdrawlStatus.getBody().contains("Amount should not be greater than current balance"));
	}

	@Test
	@Order(5)
	public void testWithdrawlWhenWithdrawlAmountIsGreaterThan90PercenOfCurrentBalance() throws Exception {
		WithdrawlDTO withdrawlDTO = new WithdrawlDTO();
		withdrawlDTO.setInvestorId(1001l);
		withdrawlDTO.setAmount(460000.2);
		withdrawlDTO.setProductId(2001l);

		HttpEntity<WithdrawlDTO> entity = new HttpEntity<WithdrawlDTO>(withdrawlDTO, getHttpHeaders());

		ResponseEntity<String> withdrawlStatus = restTemplate.exchange(baseUrl + "/investor/withdrawl", HttpMethod.POST,
				entity, String.class);

		Assertions.assertTrue(withdrawlStatus.getStatusCode().is4xxClientError());

		Assertions.assertTrue(
				withdrawlStatus.getBody().contains("Amount should not be greater than 90% if its current balance"));
	}

	@Test
	@Order(6)
	public void testWithdrawlWhenRetirementProduct_InvestorAgeGreaterThan65() throws Exception {
		WithdrawlDTO withdrawlDTO = new WithdrawlDTO();
		withdrawlDTO.setInvestorId(1003l);
		withdrawlDTO.setAmount(2563.0);
		withdrawlDTO.setProductId(2003l);

		HttpEntity<WithdrawlDTO> entity = new HttpEntity<WithdrawlDTO>(withdrawlDTO, getHttpHeaders());

		ResponseEntity<String> withdrawlStatus = restTemplate.exchange(baseUrl + "/investor/withdrawl", HttpMethod.POST,
				entity, String.class);

		Assertions.assertTrue(withdrawlStatus.getStatusCode().is4xxClientError());

		Assertions.assertTrue(
				withdrawlStatus.getBody().contains("For retirement product age should not be grater than 65"));
	}

	@Test
	@Order(7)
	public void testWithdrawl() throws Exception {
		WithdrawlDTO withdrawlDTO = new WithdrawlDTO();
		withdrawlDTO.setInvestorId(1001l);
		withdrawlDTO.setAmount(2563.0);
		withdrawlDTO.setProductId(2001l);

		HttpEntity<WithdrawlDTO> entity = new HttpEntity<WithdrawlDTO>(withdrawlDTO, getHttpHeaders());

		ResponseEntity<String> withdrawlStatus = restTemplate.exchange(baseUrl + "/investor/withdrawl", HttpMethod.POST,
				entity, String.class);

		Assertions.assertTrue(withdrawlStatus.getStatusCode().is2xxSuccessful());

	}
}
