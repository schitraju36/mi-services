package com.mi.services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mi.services.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("select p from Product p where p.investor.id=:investorId")
	public List<Product> getInvestorProducts(@Param("investorId") Long investorId);
	
//	public List<Product> findByInvestor_id(Long investorId);
}
