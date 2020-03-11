package com.flow.opal.controllers;

import com.flow.opal.models.entities.River;
import com.flow.opal.models.entities.RiverSection;
import com.flow.opal.services.RiverService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class RiverControllerUnitTests {

  @MockBean
  RiverService riverService;

  @Autowired
  MockMvc mockMvc;

  @Test
  public void listOfRivers_shouldReturn_OkStatusAndJsonContentAndServiceCalledOnce()
      throws Exception {
    List<River> riverList = new ArrayList<>();
    riverList.add(new River());

    when(riverService.findAllRivers()).thenReturn(riverList);

    mockMvc.perform(get("/api/rivers"))
        .andExpect(status().isOk())
        .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"));
    verify(riverService).findAllRivers();
  }

  @Test
  public void listOfRiversSections_withValidRiver_shouldReturn200StatusAndJsonContent()
      throws Exception {
    String validRiverName = "Duna";
    River mockRiver = Mockito.mock(River.class);
    List<RiverSection> mockSections = new ArrayList<>();

    when(riverService.findByName(validRiverName)).thenReturn(mockRiver);
    when(riverService.isRiverNameValid(validRiverName)).thenReturn(true);
    when(mockRiver.getSections()).thenReturn(mockSections);

    mockMvc.perform(get("/api/riversections/" + validRiverName))
        .andExpect(status().isOk())
        .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"));

    verify(riverService).isRiverNameValid(validRiverName);
    verify(riverService).findByName(validRiverName);
    verify(mockRiver).getSections();
  }

  @Test
  public void listOfRiversSections_withInvalidRiver_shouldReturn404Status()
      throws Exception {
    String invalidRiverName = "Duma";

    when(riverService.findByName(invalidRiverName)).thenReturn(null);
    //when(riverService.isRiverValid(null)).thenReturn(false);

    mockMvc.perform(get("/api/riversections/" + invalidRiverName))
        .andExpect(status().isNotFound());

    verify(riverService).isRiverNameValid(invalidRiverName);
  }

}
