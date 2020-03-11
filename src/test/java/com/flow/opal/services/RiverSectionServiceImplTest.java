package com.flow.opal.services;

import com.flow.opal.models.entities.RiverSection;
import com.flow.opal.repositories.RiverSectionRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class RiverSectionServiceImplTest {

  private RiverSectionService riverSectionService = new RiverSectionServiceImpl();

  @Test
  public void isRiverSectionValid_GivenName_ExpectedTrue() {
    RiverSection riverSection = new RiverSection("Felső-Duna");
    boolean isValid = riverSectionService.isRiverSectionValid(riverSection);
    Assert.assertTrue(isValid);
  }

  @Test
  public void isRiverSectionValid_NoNameGiven_ExpectedFalse() {
    RiverSection riverSection = new RiverSection();
    boolean isValid = riverSectionService.isRiverSectionValid(riverSection);
    Assert.assertFalse(isValid);
  }

  @Test
  public void isRiverSectionValid_EmptyStringGiven_ExpectedFalse() {
    RiverSection riverSection = new RiverSection("");
    boolean isValid = riverSectionService.isRiverSectionValid(riverSection);
    Assert.assertFalse(isValid);
  }

  @Test
  public void findAll_empty() {
    RiverSectionRepository mockRepository = Mockito.mock(RiverSectionRepository.class);
    RiverSectionServiceImpl mockService = new RiverSectionServiceImpl(mockRepository);
    List<RiverSection> riverSections = new ArrayList<>();
    Mockito.when(mockRepository.findAll()).thenReturn(riverSections);
    Assert.assertEquals(new ArrayList<>(), mockService.findAllRiverSection());
  }

  @Test
  public void findAll_oneSection() {
    RiverSectionRepository mockRepository = Mockito.mock(RiverSectionRepository.class);
    RiverSectionServiceImpl mockService = new RiverSectionServiceImpl(mockRepository);
    RiverSection mockSection = new RiverSection();
    List<RiverSection> sections = Arrays.asList(mockSection);
    Mockito.when(mockRepository.findAll()).thenReturn(sections);
    Assert.assertEquals(mockSection, mockService.findAllRiverSection().get(0));
  }

  @Test
  public void findAll_lengthOfThree() {
    RiverSectionRepository mockRepository = Mockito.mock(RiverSectionRepository.class);
    RiverSectionServiceImpl mockService = new RiverSectionServiceImpl(mockRepository);
    RiverSection mockSection = new RiverSection();
    RiverSection mockSection2 = new RiverSection();
    RiverSection mockSection3 = new RiverSection();
    List<RiverSection> sections = Arrays.asList(mockSection,mockSection2,mockSection3);
    Mockito.when(mockRepository.findAll()).thenReturn(sections);
    Assert.assertEquals(3, mockService.findAllRiverSection().size());
  }

  @Test
  public void findByName_notFound() {
    RiverSectionRepository mockRepository = Mockito.mock(RiverSectionRepository.class);
    RiverSectionServiceImpl mockService = new RiverSectionServiceImpl(mockRepository);
    Mockito.when(mockRepository.findByName("whatever")).thenReturn(Optional.empty());
    Assert.assertNull(mockService.findByName("whatever"));
  }

  @Test
  public void findByName_Found() {
    RiverSectionRepository mockRepository = Mockito.mock(RiverSectionRepository.class);
    RiverSectionServiceImpl mockService = new RiverSectionServiceImpl(mockRepository);
    RiverSection mockSection = new RiverSection();
    Mockito.when(mockRepository.findByName("whatever")).thenReturn(Optional.of(mockSection));
    Assert.assertEquals(mockSection,mockService.findByName("whatever"));
  }

}
