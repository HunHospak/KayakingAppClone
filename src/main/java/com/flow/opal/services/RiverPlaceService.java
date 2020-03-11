package com.flow.opal.services;

import com.flow.opal.models.dtos.RiverPlaceDTO;
import com.flow.opal.models.dtos.RiverPlaceJson;
import com.flow.opal.models.dtos.RiverPlaceListDTO;
import com.flow.opal.models.entities.RiverPlace;

import com.flow.opal.models.entities.RiverSection;
import java.util.List;

public interface RiverPlaceService {

  boolean checkDtoFieldsNotNull(RiverPlaceDTO riverPlaceDTO);

  List<RiverPlace> findAll();

  RiverPlace findById(Integer id);

  boolean checkIfNewPlacesAreFilledProperly(RiverPlaceListDTO riverPlaceListDTO);

  void saveNewPlaces(RiverPlaceListDTO riverPlaceListDTO, String section);

  void saveRiverPlace(RiverPlaceJson riverPlace, String section);

  List<RiverPlace> findByRiverSection(RiverSection riverSection);
}
