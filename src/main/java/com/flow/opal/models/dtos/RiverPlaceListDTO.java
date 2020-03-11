package com.flow.opal.models.dtos;

import java.util.List;

public class RiverPlaceListDTO {

  private List<RiverPlaceJson> placesToAdd;

  public RiverPlaceListDTO() {
  }

  public RiverPlaceListDTO(List<RiverPlaceJson> placesToAdd) {
    this.placesToAdd = placesToAdd;
  }

  public List<RiverPlaceJson> getPlacesToAdd() {
    return placesToAdd;
  }

  public void setPlacesToAdd(List<RiverPlaceJson> placesToAdd) {
    this.placesToAdd = placesToAdd;
  }
}
