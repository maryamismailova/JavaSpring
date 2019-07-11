package com.example.spring.controller;

import com.example.spring.Application;
import com.example.spring.domain.Business;
import com.example.spring.service.dto.BusinessDTO;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class BusinessControllerIntegrationTest {

    @LocalServerPort
    private int localPort;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    //test to save mock Business object
    @Test
    public void save() {

        BusinessDTO businessDTO = new BusinessDTO("Facebook", "Social Media");
        ResponseEntity<BusinessDTO> dto = testRestTemplate.postForEntity(createURI("/api/business/save"), businessDTO, BusinessDTO.class);
        assertThat(dto.getStatusCode(), equalTo(HttpStatus.CREATED));
    }


    //test to get mock Business by id
    @Test
    public void getBusinessById() throws JSONException {

        String result = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Twitter\",\n" +
                "    \"info\": \"Social Media\"\n" +
                "}";

        ResponseEntity<String> dto = testRestTemplate.getForEntity(createURI("/api/business/1"), String.class);
        //JSONAssert converts your string into JSON object and the compares
        JSONAssert.assertEquals(result, dto.getBody(), false);
    }

    @Before
    public void init(){
        testRestTemplate.postForEntity(
                createURI("/api/business/save"), new BusinessDTO( "Facebook", "Social Media"),
                BusinessDTO.class);
        testRestTemplate.postForEntity(
                createURI("/api/business/save"), new BusinessDTO( "Twitter", "Social Media"),
                BusinessDTO.class);
    }

    @Test
    public void getAllBusinesses() throws JSONException {
        String expect="[{\"id\":1,\"name\":\"Facebook\",\"info\":\"Social Media\"},{\"id\":2,\"name\":\"Twitter\",\"info\":\"Social Media\"}]";
        ResponseEntity<String> result=testRestTemplate.getForEntity(createURI("/api/business/"), null, String.class);
        JSONAssert.assertEquals(expect, result.getBody(), false);

    }


    @Test
    public void deleteById(){
        BusinessDTO businessDTO = new BusinessDTO(1L, "Facebook", "Social Media");
        String uri=createURI("/api/business/delete/1");


        ResponseEntity<BusinessDTO> dto=testRestTemplate.exchange(uri, HttpMethod.DELETE, null, BusinessDTO.class );

        assertThat(dto.getStatusCode(), equalTo(HttpStatus.OK));
//        assertThat(dto.getBody(), equalTo(businessDTO));

    }

    private String createURI(String uri) {
        return "http://localhost:" + localPort + uri;
    }
}
