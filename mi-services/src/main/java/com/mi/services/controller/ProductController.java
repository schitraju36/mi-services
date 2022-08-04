package com.mi.services.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mi.services.dto.ProductDTO;
import com.mi.services.service.ProductService;

@RestController
@RequestMapping("products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("investor/{investrorId}")
	List<ProductDTO> getInvestorProducts(@PathVariable("investrorId") Long investrorId) {
		return productService.getProducts(investrorId);
	}
}
