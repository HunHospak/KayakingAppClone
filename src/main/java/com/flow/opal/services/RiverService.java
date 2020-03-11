package com.flow.opal.services;

import com.flow.opal.models.entities.River;
import java.util.List;

public interface RiverService {

  void save(River river);

  River findById(Long id);

  River findByName(String name);

  boolean isRiverNameNotNull(River river);

  boolean isRiverNameValid(String riverName);

  List<River> findAllRivers();

  void saveAllRivers();

}
