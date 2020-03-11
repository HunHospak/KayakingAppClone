package com.flow.opal.controllers;

import com.flow.opal.models.dtos.RiverPlaceListDTO;
import com.flow.opal.services.RiverPlaceService;
import com.flow.opal.services.RiverSectionService;
import javax.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class RiverPlaceController {

  private RiverPlaceService service;
  private RiverSectionService sectionService;

  @Autowired
  public RiverPlaceController(RiverPlaceService service, RiverSectionService sectionService) {
    this.service = service;
    this.sectionService = sectionService;
  }

  @GetMapping(value = "/api/places")
  public ResponseEntity<?> listPlaces() {
    return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
  }

  @PostMapping(value = "api/places/{riversection}")
  public ResponseEntity<?> saveNewPlaces(@RequestBody RiverPlaceListDTO placesToAdd,
      @PathVariable String riversection) {
    if (sectionService.findByName(riversection) == null) {
      throw new BadRequestException("No riversection with that name");
    } else if (placesToAdd == null || placesToAdd.getPlacesToAdd().size() == 0) {
      throw new BadRequestException("Please provide some places to add to this section");
    } else if (service.checkIfNewPlacesAreFilledProperly(placesToAdd)) {
      service.saveNewPlaces(placesToAdd, riversection);
      return new ResponseEntity(HttpStatus.CREATED);
    } else {
      throw new BadRequestException("The place list you posted is invalid");
    }
  }
}
