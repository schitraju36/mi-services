package com.mi.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mi.services.entity.Investor;

public interface InvestorRepository  extends JpaRepository<Investor, Long>{

 
}
