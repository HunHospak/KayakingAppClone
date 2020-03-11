package com.flow.opal.models.dtos;

import com.google.gson.annotations.SerializedName;

public class RiverPlaceDTO {
  // fields for getting RiverPlaces from Google Maps Places API

  @SerializedName(value = "place_id")
  private String placeId;
  private String name;
  private String type;

  public RiverPlaceDTO() {
  }

  public RiverPlaceDTO(String placeId, String name, String type) {
    this.placeId = placeId;
    this.name = name;
    this.type = type;
  }

  public String getPlaceId() {
    return placeId;
  }

  public void setPlaceId(String placeId) {
    this.placeId = placeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
