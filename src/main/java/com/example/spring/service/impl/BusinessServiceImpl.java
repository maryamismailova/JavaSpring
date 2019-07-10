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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


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
    public BusinessDTO delete(BusinessDTO businessDTO) {
        logger.debug("Deleting business");
        Business business=businessMapper.toEntity(businessDTO);
        businessRepository.delete(business);
        return businessMapper.toDTO(business);
    }

    @Override
    public BusinessDTO deleteById(Long id) {
        logger.debug("Deleting business");
        if(businessRepository.findById(id).isPresent()) {
            Business businessToDelete = businessRepository.findById(id).get();
            businessRepository.delete(businessToDelete);
            return businessMapper.toDTO(businessToDelete);
        }else return null;
    }

    @Override
    public BusinessDTO update(BusinessDTO businessDTO) {
        logger.debug("Updating business with id="+businessDTO.getId());

        Optional<Business> businessToUpdate=businessRepository.findById(businessDTO.getId());

        if(businessToUpdate.isPresent()){
            businessRepository.delete(businessToUpdate.get());
            businessRepository.save(businessMapper.toEntity(businessDTO));
            return businessDTO;
        }else{
            return null;
        }
    }
}
