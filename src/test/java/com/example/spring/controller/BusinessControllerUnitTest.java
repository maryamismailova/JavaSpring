package com.example.spring.controller;

import com.example.spring.service.BusinessService;
import com.example.spring.service.dto.BusinessDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BusinessController.class)
public class BusinessControllerUnitTest {

    @MockBean
    BusinessService businessService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void saveBusiness() throws Exception {
        BusinessDTO businessDTO = new BusinessDTO("Facebook", "Social Media");
        when(businessService.save(isNull())).thenReturn(businessDTO);

        String content = "{\n" +
                "\t\"name\": \"Facebook\",\n" +
                "\t\"info\" : \"Social Media\"\n" +
                "\t\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/business/save")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    public void getBusinessById() throws Exception {

        BusinessDTO business = new BusinessDTO(1L, "Facebook", "Social Media");
        Optional<BusinessDTO> optional = Optional.of(business);
        when(businessService.getById(1L)).thenReturn(optional);

        MvcResult mockMvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/business/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String result = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Facebook\",\n" +
                "    \"info\": \"Social Media\"\n" +
                "}";

        JSONAssert.assertEquals(result, mockMvcResult.getResponse().getContentAsString(), false);

    }

    @Test
    public void getAllBusinesses() throws Exception {
        List<BusinessDTO> expected= Arrays.asList(new BusinessDTO(1L, "Facebook", "Social Media"),
                new BusinessDTO(2L, "Twitter", "Social Media"));
        String expectedString="[{\"id\":1,\"name\":\"Facebook\",\"info\":\"Social Media\"},{\"id\":2,\"name\":\"Twitter\",\"info\":\"Social Media\"}]";

        when(businessService.getAllBusinesses()).thenReturn(expected);

        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/api/business").
                accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONAssert.assertEquals(expectedString, result.getResponse().getContentAsString(), false);


    }
/*
    @Test
    public void deleteBusiness() throws Exception{
        BusinessDTO businessDTO=new BusinessDTO("Facebook", "Social Media");
        when(businessService.delete(businessDTO)).thenReturn(businessDTO);

        String content = "{\n" +
                "\t\"name\": \"Facebook\",\n" +
                "\t\"info\" : \"Social Media\"\n" +
                "\t\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/business/delete" )
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
*/
    @Test
    public void deleteBusinessById() throws Exception{
        BusinessDTO businessDTO=new BusinessDTO(1L, "Facebook", "Social Media");
        when(businessService.deleteById(1L)).thenReturn(businessDTO);

        String content = "{\n" +
                "\t\"name\": \"Facebook\",\n" +
                "\t\"info\" : \"Social Media\"\n" +
                "\t\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/business/delete/1" )
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception{
        BusinessDTO businessDTO=new BusinessDTO(1L, "Facebook", "Social Media");

        when(businessService.update(any())).thenReturn(businessDTO);

        String content = "{\n" +
                "\t\"id\": 1,\n" +
                "\t\"name\": \"Facebook\",\n" +
                "\t\"info\" : \"Social Media\"\n" +
                "\t\n" +
                "}";


        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.put("/api/business/update")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

}
