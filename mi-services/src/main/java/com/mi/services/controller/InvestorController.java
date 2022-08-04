package com.mi.services.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mi.services.dto.InvestorDTO;
import com.mi.services.dto.ProductDTO;
import com.mi.services.dto.StatusInfo;
import com.mi.services.dto.WithdrawlDTO;
import com.mi.services.entity.ProductType;
import com.mi.services.service.InvestorService;
import com.mi.services.service.ProductService;

@RestController
@RequestMapping("investor")
public class InvestorController {

	private static final String FAIL = "FAIL";

	private static final String SUCCESS = "SUCCESS";

	@Autowired
	private InvestorService investorService;

	@Autowired
	private ProductService productService;

	@GetMapping
	public List<InvestorDTO> getAllInvestors() {
		return investorService.getAllInvestors();
	}

	@PostMapping("withdrawl")
	public ResponseEntity<StatusInfo> createWithDrawl(@RequestBody @Valid WithdrawlDTO withdrawlDTO) {

		ResponseEntity<StatusInfo> responseEntity = null;

		InvestorDTO investorDTO = investorService.getInvestor(withdrawlDTO.getInvestorId());
		if (investorDTO == null) {
			StatusInfo statusInfo = new StatusInfo();
			statusInfo.setStatus(FAIL);
			statusInfo.setMsg("Invalid investor id");
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(statusInfo);
			return responseEntity;
		}

		ProductDTO productDTO = productService.getProduct(withdrawlDTO.getProductId());

		if (productDTO == null) {
			StatusInfo statusInfo = new StatusInfo();
			statusInfo.setStatus(FAIL);
			statusInfo.setMsg("Invalid product id");
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(statusInfo);
			return responseEntity;
		}

		if (withdrawlDTO.getAmount() > productDTO.getCurrentBalance()) {
			StatusInfo statusInfo = new StatusInfo();
			statusInfo.setStatus(FAIL);
			statusInfo.setMsg("Amount should not be greater than current balance");
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(statusInfo);
			return responseEntity;
		}

		if (withdrawlDTO.getAmount() > (productDTO.getCurrentBalance() * 90) / 100) {
			StatusInfo statusInfo = new StatusInfo();
			statusInfo.setStatus(FAIL);
			statusInfo.setMsg("Amount should not be greater than 90% if its current balance");
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(statusInfo);
			return responseEntity;
		}

		if (productDTO.getType().equals(ProductType.RETIREMENT)) {
			Date dob = investorDTO.getDob();
			LocalDate dobLocalDate = new java.sql.Date(dob.getTime()).toLocalDate();
			int years = Period.between(dobLocalDate, LocalDate.now()).getYears();

			if (years > 65) {
				StatusInfo statusInfo = new StatusInfo();
				statusInfo.setStatus(FAIL);
				statusInfo.setMsg("For retirement product age should not be grater than 65");
				responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(statusInfo);
				return responseEntity;
			}
		}

		responseEntity = ResponseEntity.ok(new StatusInfo(SUCCESS));

		return responseEntity;
	}

}
