package com.flow.opal.services;

import com.flow.opal.models.dtos.RiverPlaceDTO;
import com.flow.opal.models.entities.RiverPlace;
import com.flow.opal.repositories.RiverPlaceRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

public class RiverPlaceServiceImplTest {

  RiverPlaceServiceImpl riverPlaceService = new RiverPlaceServiceImpl();

  @Test
  public void validateRiverPlaceDTO_valid() {
    //Arrange
    RiverPlaceDTO mockRiverPlaceDTOvalid = new RiverPlaceDTO();
    mockRiverPlaceDTOvalid.setName("mock");
    mockRiverPlaceDTOvalid.setPlaceId("1");
    mockRiverPlaceDTOvalid.setType("mock");
    //Act
    boolean testCaseValid = riverPlaceService.checkDtoFieldsNotNull(mockRiverPlaceDTOvalid);
    //Assert
    Assert.assertTrue(testCaseValid);
  }

  @Test
  public void validateRiverPlaceDTO_invalid() {
    RiverPlaceDTO mockRiverPlaceDTOinvalid = new RiverPlaceDTO();
    mockRiverPlaceDTOinvalid.setPlaceId("a");
    mockRiverPlaceDTOinvalid.setType("mock");
    boolean testCaseInvalid = riverPlaceService.checkDtoFieldsNotNull(mockRiverPlaceDTOinvalid);
    Assert.assertFalse(testCaseInvalid);
  }

  @Test
  public void findAll_empty() {
    RiverPlaceRepository mockRepository = Mockito.mock(RiverPlaceRepository.class);
    RiverPlaceServiceImpl mockService = new RiverPlaceServiceImpl(mockRepository);
    List<RiverPlace> places = new ArrayList<>();
    Mockito.when(mockRepository.findAll()).thenReturn(places);
    Assert.assertEquals(new ArrayList<>(), mockService.findAll());
  }

  @Test
  public void findAll_onePlace() {
    RiverPlaceRepository mockRepository = Mockito.mock(RiverPlaceRepository.class);
    RiverPlaceServiceImpl mockService = new RiverPlaceServiceImpl(mockRepository);
    RiverPlace mockPlace = new RiverPlace();
    List<RiverPlace> places = Arrays.asList(mockPlace);
    Mockito.when(mockRepository.findAll()).thenReturn(places);
    Assert.assertEquals(mockPlace, mockService.findAll().get(0));
  }

  @Test
  public void findAll_lengthOfFOur() {
    RiverPlaceRepository mockRepository = Mockito.mock(RiverPlaceRepository.class);
    RiverPlaceServiceImpl mockService = new RiverPlaceServiceImpl(mockRepository);
    RiverPlace mockPlace = new RiverPlace();
    RiverPlace mockPlace2 = new RiverPlace();
    RiverPlace mockPlace3 = new RiverPlace();
    RiverPlace mockPlace4 = new RiverPlace();
    List<RiverPlace> places = Arrays.asList(mockPlace, mockPlace2, mockPlace3, mockPlace4);
    Mockito.when(mockRepository.findAll()).thenReturn(places);
    Assert.assertEquals(4, mockService.findAll().size());
  }

  @Test
  public void findById_notFound() {
    RiverPlaceRepository mockRepository = Mockito.mock(RiverPlaceRepository.class);
    RiverPlaceServiceImpl mockService = new RiverPlaceServiceImpl(mockRepository);
    Mockito.when(mockRepository.findById(0)).thenReturn(Optional.empty());
    Assert.assertNull(mockService.findById(0));
  }

  @Test
  public void findById_googleApiIdEquals() {
    RiverPlaceRepository mockRepository = Mockito.mock(RiverPlaceRepository.class);
    RiverPlace mockPlace = new RiverPlace();
    mockPlace.setId(1);
    mockPlace.setGoogleApiId("googleid");
    RiverPlaceServiceImpl mockService = new RiverPlaceServiceImpl(mockRepository);
    Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockPlace));
    Assert.assertEquals(mockPlace.getGoogleApiId(), mockService.findById(1).getGoogleApiId());
  }
}
