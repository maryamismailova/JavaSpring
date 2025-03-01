package com.example.spring.controller;


import com.example.spring.domain.Business;
import com.example.spring.exceptions.BadRequestException;
import com.example.spring.service.BusinessService;
import com.example.spring.service.dto.BusinessDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BusinessController {

    private BusinessService businessService;
    private Logger logger = LoggerFactory.getLogger(BusinessController.class);


    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @PostMapping("/business/save")
    public ResponseEntity<BusinessDTO> saveBusiness(@RequestBody BusinessDTO businessDTO) {
        logger.debug("Rest request to save business");
        if (businessDTO.getId() != null){
            throw new BadRequestException("Business already exists");
        }
        BusinessDTO result=businessService.save(businessDTO);
//        return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<BusinessDTO>(result, HttpStatus.CREATED);//to see also the created id
    }

    @GetMapping("/business/{id}")
    public ResponseEntity<Optional<BusinessDTO>> getBusinessById(@PathVariable Long id) {
        if (id == null){
            throw new BadRequestException("Enter valid id");
        }

        Optional<BusinessDTO> result = businessService.getById(id);
        return ResponseEntity.ok().body(result);//create and send result + status on success

    }

    @GetMapping("/business")
    public ResponseEntity<List<BusinessDTO>> getAllBusinesses(){
        logger.debug("Getting all businesses");

        return new ResponseEntity<>(businessService.getAllBusinesses(), HttpStatus.OK);

    }
/*
    @DeleteMapping("/business/delete")
    public ResponseEntity<BusinessDTO> deleteBusiness(@RequestBody BusinessDTO businessDTO){
        logger.debug("Rest request to delete business");

        BusinessDTO deletedBusiness=businessService.delete(businessDTO);
        if(deletedBusiness!=null){
            return new ResponseEntity<>(HttpStatus.OK);//NOT SURE about status
        }else{
            throw new BadRequestException("Business doesn't exist");
        }
    }
*/

    @DeleteMapping("business/delete/{id}")
    public ResponseEntity<BusinessDTO> deleteBusinessById(@PathVariable Long id){
        logger.debug("Rest request to delete business by id");
        if (id==null)
            throw new BadRequestException("Enter valid id");//if not provided an id, inform with an exception
        BusinessDTO result=businessService.deleteById(id);
        if(result!=null)
            return new ResponseEntity<>(result, HttpStatus.OK);
//                    new ResponseEntity<>(HttpStatus.OK);
        else
            throw new BadRequestException("Business doesn't exist");
    }

    @PutMapping("business/update")
    public ResponseEntity<BusinessDTO> updateBusiness(@RequestBody BusinessDTO businessDTO){
        logger.debug("Rest request to update a business");
        if(businessDTO!=null && businessDTO.getId()!=null){//must provide id of business to update
            BusinessDTO dto=businessService.update(businessDTO);
            if(dto!=null)
                return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        throw new BadRequestException("Business doesn't exist");

    }

}
