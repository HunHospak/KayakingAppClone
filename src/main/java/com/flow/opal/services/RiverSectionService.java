package com.flow.opal.services;

import com.flow.opal.models.entities.RiverSection;
import java.util.List;

public interface RiverSectionService {

  void save(RiverSection riverSection);

  RiverSection findById(Long id);

  RiverSection findByName(String name);

  void deleteById(Long id);

  boolean isRiverSectionValid(RiverSection riverSection);

  List<RiverSection> findAllRiverSection();

  void setCoordinates(RiverSection section);

  void setIsRunnable(RiverSection section);
}
