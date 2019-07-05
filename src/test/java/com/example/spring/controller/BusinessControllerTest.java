package com.example.spring.controller;

import com.example.spring.service.BusinessService;
import com.example.spring.service.dto.BusinessDTO;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BusinessController.class)
public class BusinessControllerTest {

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
}
