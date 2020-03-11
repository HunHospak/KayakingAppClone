package com.flow.opal.services;

import com.flow.opal.models.entities.River;
import com.flow.opal.repositories.RiverRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiverServiceImpl implements RiverService {

  private RiverRepository riverRepository;

  public RiverServiceImpl() {
  }

  @Autowired
  public RiverServiceImpl(RiverRepository riverRepository) {
    this.riverRepository = riverRepository;
    saveAllRivers();
  }

  @Override
  public void save(River river) {
    if (isRiverNameNotNull(river)) {
      riverRepository.save(river);
    }
  }

  @Override
  public List<River> findAllRivers() {
    List<River> response = new ArrayList<>();
    riverRepository.findAll().forEach(r -> response.add(r));
    return response;
  }

  @Override
  public void saveAllRivers() {
    if (riverRepository.findAll().size() == 0) {
      List<River> riversToSave = Arrays.asList(
          new River("Duna", 417),
          new River("Dráva", 140),
          new River("Rába", 189),
          new River("Tisza", 597),
          new River("Maros", 49),
          new River("Szamos", 50),
          new River("Sajó", 127),
          new River("Zagyva", 179),
          new River("Bodrog", 49));
      riverRepository.saveAll(riversToSave);
    }
  }

  @Override
  public River findById(Long id) {
    return riverRepository.findById(id).orElse(null);
  }

  @Override
  public River findByName(String name) {
    return riverRepository.findByRiverName(name).orElse(null);
  }

  @Override
  public boolean isRiverNameNotNull(River river) {
    return river.getRiverName() != null && !river.getRiverName().equals("");
  }

  @Override
  public boolean isRiverNameValid(String riverName) {
    return riverRepository.findByRiverName(riverName).isPresent();
  }
}

