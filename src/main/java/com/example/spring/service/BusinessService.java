package com.example.spring.service;


import com.example.spring.service.dto.BusinessDTO;

import java.util.List;
import java.util.Optional;

public interface BusinessService {

    BusinessDTO save(BusinessDTO businessDTO);
    Optional<BusinessDTO> getById(Long id);
    BusinessDTO deleteById(Long id);
    BusinessDTO update(BusinessDTO businessDTO);
    List<BusinessDTO> getAllBusinesses();
}
