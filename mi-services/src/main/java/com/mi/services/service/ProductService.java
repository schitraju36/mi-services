package com.mi.services.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mi.services.dto.ProductDTO;
import com.mi.services.entity.Product;
import com.mi.services.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private ProductRepository productRepository;

	public List<ProductDTO> getProducts(Long investrorId) {
		List<Product> products = productRepository.getInvestorProducts(investrorId);
//		List<Product> products = productRepository.findByInvestor_id(investrorId);
		List<ProductDTO> productList = products.stream().map(prod -> modelMapper.map(prod, ProductDTO.class))
				.collect(Collectors.toList());
		return productList;
	}

	public ProductDTO getProduct(Long productId) {
		Product product = productRepository.findById(productId).orElse(null);
		ProductDTO productDto = null;
		if (product != null) {
			productDto = modelMapper.map(product, ProductDTO.class);
		}
		return productDto;
	}

}
