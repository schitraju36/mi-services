package com.mi.services.dto;

import com.mi.services.entity.Investor;
import com.mi.services.entity.Product;
import com.mi.services.entity.ProductType;

import lombok.Data;
import lombok.NoArgsConstructor;


public class ProductDTO {

	private Long id;

	private ProductType type;

	private String name;

	private Double currentBalance;

	private Long investorId;
	
	public ProductDTO() {
		
	}

	public ProductDTO(Long id, ProductType type, String name, Double currentBalance, Long investorId) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.currentBalance = currentBalance;
		this.investorId = investorId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Long getInvestorId() {
		return investorId;
	}

	public void setInvestorId(Long investorId) {
		this.investorId = investorId;
	}

}
