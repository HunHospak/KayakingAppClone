package com.flow.opal.models.dtos;

public class RiverPlaceJson {

  private String api;
  private String name;
  private String type;

  public RiverPlaceJson() {
  }

  public RiverPlaceJson(String api, String name, String type) {
    this.api = api;
    this.name = name;
    this.type = type;
  }

  public String getApi() {
    return api;
  }

  public void setApi(String api) {
    this.api = api;
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
