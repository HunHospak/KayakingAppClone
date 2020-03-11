package com.flow.opal.services;

import com.flow.opal.models.entities.RiverSection;
import com.flow.opal.repositories.RiverSectionRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiverSectionServiceImpl implements RiverSectionService {

  private RiverSectionRepository riverSectionRepository;

  @Autowired
  public RiverSectionServiceImpl(RiverSectionRepository riverSectionRepository) {
    this.riverSectionRepository = riverSectionRepository;
  }

  public RiverSectionServiceImpl() {
  }

  @Override
  public void save(RiverSection riverSection) {
    riverSectionRepository.save(riverSection);
  }

  @Override
  public RiverSection findById(Long id) {
    return riverSectionRepository.findById(id).orElse(null);
  }

  @Override
  public RiverSection findByName(String name) {
    return riverSectionRepository.findByName(name).orElse(null);
  }

  @Override
  public void deleteById(Long id) {
    riverSectionRepository.findById(id);
  }

  @Override
  public boolean isRiverSectionValid(RiverSection riverSection) {
    return riverSection.getName() != null
        && !riverSection.getName().equals("");
  }

  @Override
  public List<RiverSection> findAllRiverSection() {
    List<RiverSection> sections = new ArrayList<>();
    riverSectionRepository.findAll().forEach(sections::add);
    return sections;
  }

  @Override
  public void setCoordinates(RiverSection section) {
    Workbook workbook = null;
    try {
      InputStream in = getClass().getClassLoader().getResourceAsStream("./sections.xlsx");
      workbook = WorkbookFactory.create(in);
    } catch (IOException | InvalidFormatException e) {
      e.printStackTrace();
    }
    Sheet sheet = workbook.getSheetAt(0);
    for (Row row : sheet) {
      if (row.getCell(0).getStringCellValue().equals(section.getName())) {
        section.setLatitude(Double.parseDouble(row.getCell(2).getStringCellValue()));
        section.setLongitude(Double.parseDouble(row.getCell(1).getStringCellValue()));
        break;
      }
    }
  }

  @Override
  public void setIsRunnable(RiverSection section) {
    section.setRunnable(((int)section.getKm() * section.getWaterLevel()) % 2 == 0);
  }
}
