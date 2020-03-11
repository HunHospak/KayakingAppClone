package com.flow.opal.controllers;

import com.flow.opal.FlowOpalApplication;
import com.flow.opal.models.entities.MyUser;
import com.flow.opal.models.entities.RiverSection;
import com.flow.opal.security.services.EmailSenderService;
import com.flow.opal.security.services.UserService;
import com.flow.opal.security.util.JwtUtil;
import com.flow.opal.services.RiverServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowOpalApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RiverControllerIntegrationTest {

  @Autowired
  private TestRestTemplate testRestTemplate;
  private HttpHeaders header = new HttpHeaders();
  private String validRiverNameInput = "Duna";
  private String invalidRiverNameInput = "Duma";
  private String resourceUrl = "http://localhost:8080/api/riversections/";
  private String jwtToken;

  @Autowired
  private Gson gson;
  private Type jsonObjectType;

  @Autowired
  RiverServiceImpl riverService;
  @Autowired
  JwtUtil jwtUtil;
  @Autowired
  UserService userService;
  @Autowired
  EmailSenderService emailSenderService;

  @Before
  public void setUp() throws Exception {
    //for validation
    userService.save(new MyUser("júzer", "e@mail.porn", "passzwörd",true));
    //token generation
    UserDetails user = new User("júzer", "passzwörd", new ArrayList<>());
    jwtToken = jwtUtil.generateToken(user);
    //add to token
    header.add("Authorization", "Bearer " + jwtToken);
  }

  @Test
  public void getRiverSectionsByRiverName() throws IOException {
    jsonObjectType = new TypeToken<ArrayList<RiverSection>>(){}.getType();
    HttpEntity<HttpHeaders> authorizationEntity = new HttpEntity<>(this.header);
    ResponseEntity<String> successfulResponse = this.testRestTemplate
        .exchange(resourceUrl + validRiverNameInput, HttpMethod.GET, authorizationEntity,
            String.class);
    assertEquals(successfulResponse.getStatusCode(), HttpStatus.OK);
    assertEquals(successfulResponse.getBody().isEmpty(), false);
    ArrayList<RiverSection> sections = gson.fromJson(successfulResponse.getBody(), jsonObjectType);
    assertFalse(sections.isEmpty());
    sections.stream().forEach(s -> assertEquals(s.getClass(), RiverSection.class));
    sections.stream().forEach(s -> assertNotNull(s.getId()));
    ResponseEntity<String> failedResponse = this.testRestTemplate
        .exchange(resourceUrl + invalidRiverNameInput, HttpMethod.GET, authorizationEntity,
            String.class);
    assertEquals(failedResponse.getStatusCode(), HttpStatus.NOT_FOUND);
  }
}
