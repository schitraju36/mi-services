package com.mi.services.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mi.services.dto.InvestorDTO;
import com.mi.services.entity.Investor;
import com.mi.services.entity.Product;
import com.mi.services.repository.InvestorRepository;
import com.mi.services.repository.ProductRepository;

@Service
public class InvestorService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private InvestorRepository investorRepository;

	@Autowired
	private ProductRepository productRepository;

	public List<InvestorDTO> getAllInvestors() {

		List<Investor> investors = investorRepository.findAll();

		List<InvestorDTO> investorDTOList = investors.stream().map(inv -> modelMapper.map(inv, InvestorDTO.class))
				.collect(Collectors.toList());

		return investorDTOList;
	}

	public InvestorDTO getInvestor(Long investorId) {
		Investor investor = investorRepository.findById(investorId).orElse(null);
		InvestorDTO investorDTO = null;
		if (investor != null) {
			investorDTO = modelMapper.map(investor, InvestorDTO.class);
		}

		return investorDTO;
	}

}
