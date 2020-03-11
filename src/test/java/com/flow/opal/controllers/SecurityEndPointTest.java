package com.flow.opal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.flow.opal.models.entities.MyUser;
import com.flow.opal.security.services.EmailSenderService;
import com.flow.opal.security.util.JwtUtil;
import java.nio.charset.StandardCharsets;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityEndPointTest {

  public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
      MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      StandardCharsets.UTF_8);

  @MockBean
  JwtUtil jwtUtil;

  @MockBean
  EmailSenderService emailSenderService;

  @Autowired
  MockMvc mockMvc;

  @Test
  public void createAuthenticationToken_LoginNotEnabledUser_shouldReturnUnauthorizedException()
      throws Exception {
    MyUser mockUser = new MyUser("helo", "helo", "helo",true);
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(mockUser);
    mockMvc.perform(post("/login")
        .contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
        .andDo(print())
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.message", is("Not validated")));
  }

  @Test
  public void registerNewUser_registerWithAlreadyExistingName_ShouldReturnBadRequest()
      throws Exception {
    MyUser mockUser = new MyUser("admin", "admin", "admin",true);
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(mockUser);
    mockMvc.perform(post("/register")
        .contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("This username is already taken."
            + " Please select another valid username")));
  }

  @Test
  public void registerNewUser_registerWithValidDetails_ShouldReturnOk() throws Exception {
    MyUser mockUser = new MyUser("testUnit", "test@unit.com", "testUnit",true);
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(mockUser);
    mockMvc.perform(post("/register")
        .contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getEndPointShouldReturnUnauthorizedRequest() throws Exception {
    mockMvc.perform(get("/api/hello")).andDo(print()).andExpect(status().isForbidden());
  }

  @Test
  public void getEndPointShouldReturnOkForLogin() throws Exception {
    MyUser mockUser = new MyUser("admin", "admin@admin.com", "admin",true);
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(mockUser);
    mockMvc.perform(post("/login")
        .contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
