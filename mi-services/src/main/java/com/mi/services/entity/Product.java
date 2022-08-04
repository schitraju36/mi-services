package com.mi.services.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "type")
	private ProductType type;

	@Column(name = "name")
	private String name;

	@Column(name = "CURRENT_BALANCE")
	private Double currentBalance;

	@ManyToOne
	private Investor investor;
	
	public Product() {
		
	}

	public Product(Long id, ProductType type, String name, Double currentBalance, Investor investor) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.currentBalance = currentBalance;
		this.investor = investor;
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

	public Investor getInvestor() {
		return investor;
	}

	public void setInvestor(Investor investor) {
		this.investor = investor;
	}
	
	
}
