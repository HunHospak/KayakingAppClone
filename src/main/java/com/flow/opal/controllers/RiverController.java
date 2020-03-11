package com.flow.opal.controllers;

import com.flow.opal.models.entities.River;
import com.flow.opal.services.RiverService;
import java.util.List;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RiverController {

  private RiverService riverService;

  @Autowired
  public RiverController(RiverService riverService) {
    this.riverService = riverService;
  }

  @GetMapping("/api/rivers")
  public ResponseEntity<?> listAllRivers() throws NotFoundException {
    List<River> rivers = riverService.findAllRivers();
    if (!rivers.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK).body(rivers);
    } else {
      throw new NotFoundException("There are no rivers in the database");
    }
  }


}
