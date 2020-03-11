package com.flow.opal.services;

import com.flow.opal.models.dtos.RiverPlaceDTO;
import com.flow.opal.models.dtos.RiverPlaceJson;
import com.flow.opal.models.dtos.RiverPlaceListDTO;
import com.flow.opal.models.entities.RiverPlace;
import com.flow.opal.models.entities.RiverSection;
import com.flow.opal.repositories.RiverPlaceRepository;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiverPlaceServiceImpl implements RiverPlaceService {

  private RiverPlaceRepository riverPlaceRepository;
  private RiverSectionService riverSectionService;

  public RiverPlaceServiceImpl() {
  }

  public RiverPlaceServiceImpl(RiverPlaceRepository riverPlaceRepository) {
    this.riverPlaceRepository = riverPlaceRepository;
  }

  @Autowired
  public RiverPlaceServiceImpl(RiverPlaceRepository riverPlaceRepository,
      RiverSectionService riverSectionService) {
    this.riverPlaceRepository = riverPlaceRepository;
    this.riverSectionService = riverSectionService;
  }


  public boolean checkDtoFieldsNotNull(RiverPlaceDTO riverPlaceDTO) {
    return (riverPlaceDTO.getPlaceId() != null
        && riverPlaceDTO.getName() != null
        && riverPlaceDTO.getType() != null);
  }

  public boolean validateRiverPlace(RiverPlaceJson riverPlaceJson, String sectionName) {
    List<String> placeNamesInRepo = new ArrayList<>();
    RiverSection sectionSelected = riverSectionService.findByName(sectionName);
    List<RiverPlace> placesInRepo = riverPlaceRepository.findByRiverSection(sectionSelected);
    for (RiverPlace place : placesInRepo) {
      placeNamesInRepo.add(place.getName());
    }
    return (!placeNamesInRepo.contains(riverPlaceJson.getName()));
  }

  @Override
  public List<RiverPlace> findAll() {
    List<RiverPlace> places = new ArrayList<>();
    riverPlaceRepository.findAll().forEach(places::add);
    return places;
  }

  @Override
  public RiverPlace findById(Integer id) {
    return riverPlaceRepository.findById(id).orElse(null);
  }

  @Override
  public boolean checkIfNewPlacesAreFilledProperly(RiverPlaceListDTO riverPlaceListDTO) {
    for (RiverPlaceJson place : riverPlaceListDTO.getPlacesToAdd()) {
      if (place.getApi() == null
          || place.getName() == null
          || place.getType() == null
          || place.getApi().equals("")
          || place.getName().equals("")
          || place.getType().equals("")) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void saveNewPlaces(RiverPlaceListDTO riverPlaceListDTO, String section) {
    if (!checkIfNewPlacesAreFilledProperly(riverPlaceListDTO)) {
      throw new BadRequestException("Please provide a valid list");
    } else {
      for (RiverPlaceJson place : riverPlaceListDTO.getPlacesToAdd()) {
        saveRiverPlace(place, section);
      }
    }
  }

  @Override
  public void saveRiverPlace(RiverPlaceJson riverPlace, String section) {
    if (validateRiverPlace(riverPlace, section)) {
      RiverPlace placeToSave = createRiverPlaceFromJson(riverPlace,section);
      riverPlaceRepository.save(placeToSave);
    }
  }

  @Override
  public List<RiverPlace> findByRiverSection(RiverSection riverSection) {
    return riverPlaceRepository.findByRiverSection(riverSection);
  }

  public RiverPlace createRiverPlaceFromJson(RiverPlaceJson riverPlace, String section) {
    RiverPlace placeToSave = new RiverPlace();
    placeToSave.setName(riverPlace.getName());
    placeToSave.setType(riverPlace.getType());
    placeToSave.setGoogleApiId(riverPlace.getApi());
    placeToSave.setRiverSection(riverSectionService.findByName(section));
    return placeToSave;

  }
}
