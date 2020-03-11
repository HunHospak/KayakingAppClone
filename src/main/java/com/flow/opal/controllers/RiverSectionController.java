package com.flow.opal.controllers;

import com.flow.opal.services.HtmlDataService;
import com.flow.opal.services.RiverService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RiverSectionController {

  private HtmlDataService service;
  private RiverService riverService;

  @Autowired
  public RiverSectionController(HtmlDataService service, RiverService riverService) {
    this.service = service;
    this.riverService = riverService;
  }

  @PostMapping("/riversections/build")
  public ResponseEntity<?> setRiverSections() {
    service.parseHtmlAndSaveRiverSections(service.getHtml());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/riversections/update")
  @Scheduled(cron = "0 0 14 * * *") //updates water levels every day at 14:00
  public ResponseEntity<?> updateWaterLevels() {
    service.parseHtmlAndUpdateWaterLevels(service.getHtml());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/api/riversections/{river}")
  public ResponseEntity<?> listAllRiverSections(
      @PathVariable(value = "river") String name) throws NotFoundException {
    if (riverService.isRiverNameValid(name)) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(riverService.findByName(name).getSections());
    } else {
      throw new NotFoundException("Could not find a river with that name");
    }
  }
}
