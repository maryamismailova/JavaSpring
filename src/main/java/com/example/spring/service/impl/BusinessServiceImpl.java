package com.example.spring.service.impl;

import com.example.spring.domain.Business;
import com.example.spring.exceptions.BadRequestException;
import com.example.spring.service.BusinessService;
import com.example.spring.service.dto.BusinessDTO;
import com.example.spring.repository.AdvertisementRepository;
import com.example.spring.repository.BusinessRepository;
import com.example.spring.service.mapper.BusinessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {

    private BusinessRepository businessRepository;
    private AdvertisementRepository advertisementRepository;
    private BusinessMapper businessMapper;
    private Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);

    public BusinessServiceImpl(BusinessRepository businessRepository, AdvertisementRepository advertisementRepository, BusinessMapper businessMapper) {
        this.businessRepository = businessRepository;
        this.advertisementRepository = advertisementRepository;
        this.businessMapper = businessMapper;
    }


    @Override
    public BusinessDTO save(BusinessDTO businessDTO) {

        logger.debug("Saving business");
        Business business = businessMapper.toEntity(businessDTO);
        businessRepository.save(business);
        return businessMapper.toDTO(business);
    }

    @Override
    public Optional<BusinessDTO> getById(Long id) {
        return businessRepository.findById(id)
                .map(businessMapper::toDTO);
    }

    @Override
    public BusinessDTO deleteById(Long id) {
        logger.debug("Deleting business");
        Optional<Business> businessToDelete=businessRepository.findById(id);
        if(businessToDelete.isPresent()){
            businessRepository.deleteById(id);
            return businessMapper.toDTO(businessToDelete.get());
        }else return null;//no such business found

    }

    //doesnt work correctly, changes the id
    @Override
    public BusinessDTO update(BusinessDTO businessDTO) {
        logger.debug("Updating business with id="+businessDTO.getId());

        Optional<Business> businessToUpdate=businessRepository.findById(businessDTO.getId());

        if(businessToUpdate.isPresent()){
            businessRepository.deleteById(businessDTO.getId());
            Business updated=businessRepository.save(businessMapper.toEntity(businessDTO));
            return businessMapper.toDTO(updated);
        }else{
            return null;
        }
    }

    @Override
    public List<BusinessDTO> getAllBusinesses() {
        logger.debug("Getting all businesses");

        List<BusinessDTO> all=businessRepository.findAll()
                .stream()
                .map((Business b)->{return businessMapper.toDTO(b);})
                .collect(toList());
        return all;
    }

}
