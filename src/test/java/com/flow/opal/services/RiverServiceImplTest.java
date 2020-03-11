package com.flow.opal.services;

import com.flow.opal.models.entities.River;
import com.flow.opal.repositories.RiverRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RiverServiceImplTest {

  @InjectMocks
  RiverService mockService = new RiverServiceImpl();
  @Mock
  RiverRepository mockRepository;

  @Before
  public void initialize() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void isRiverValid_GivenRiverName_ExpectedTrue() {
    // Arrange
    River validRiver = new River("Duna");

    // Act
    boolean isValid = mockService.isRiverNameNotNull(validRiver);

    // Assert
    Assert.assertTrue(isValid);

  }

  @Test
  public void isRiverValid_NoRiverNameGiven_ExpectedFalse() {
    River invalidRiver = new River();
    boolean isInvalid = mockService.isRiverNameNotNull(invalidRiver);
    Assert.assertFalse(isInvalid);
  }

  @Test
  public void isRiverValid_EmptyStringGiven_ExpectedFalse() {
    River invalidRiver = new River("");
    boolean isInvalid = mockService.isRiverNameNotNull(invalidRiver);
    Assert.assertFalse(isInvalid);
  }


  @Test
  public void findAllRiver_empty() {
    List<River> rivers = new ArrayList<>();
    Mockito.when(mockRepository.findAll()).thenReturn(rivers);
    Assert.assertEquals(new ArrayList<>(), mockService.findAllRivers());
  }

  @Test
  public void findAllRiver_oneRiver() {
    River mockRiver = new River();
    List<River> rivers = new ArrayList<>();
    rivers.add(mockRiver);
    Mockito.when(mockRepository.findAll()).thenReturn(rivers);
    Assert.assertEquals(mockRiver, mockService.findAllRivers().get(0));
  }

  @Test
  public void findAllRiver_threeRiver() {
    River mockRiver0 = new River();
    River mockRiver1 = new River();
    River mockRiver2 = new River();
    List<River> rivers = Arrays.asList(mockRiver0, mockRiver1, mockRiver2);
    Mockito.when(mockRepository.findAll()).thenReturn(rivers);
    Assert.assertEquals(3, mockService.findAllRivers().size());
  }

  @Test
  public void findById_null() {
    Mockito.when(mockRepository.findById(0L)).thenReturn(Optional.empty());
    Assert.assertNull(mockService.findById(0L));
  }

  @Test
  public void findById_found() {
    River mockRiver = new River();
    mockRiver.setId(1L);
    Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.of(mockRiver));
    Assert.assertEquals(mockRiver, mockService.findById(1L));
  }
}
