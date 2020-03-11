package com.flow.opal.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flow.opal.models.dtos.RiverPlaceDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class RiverPlace {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String api; //place_id for GoogleApi had to change it for SQL
  private String name;
  private String type;

  @JsonIgnore
  @ManyToOne
  private RiverSection riverSection;

  public RiverPlace() {
  }

  public RiverPlace(RiverPlaceDTO riverPlaceDTO) {
    this.api = riverPlaceDTO.getPlaceId();
    this.name = riverPlaceDTO.getName();
    this.type = riverPlaceDTO.getType();
  }

  public RiverPlace(String googleApiId, String name, String type, RiverSection riverSection) {
    this.api = googleApiId;
    this.name = name;
    this.type = type;
    this.riverSection = riverSection;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public String getGoogleApiId() {
    return api;
  }

  public void setGoogleApiId(String googleApiId) {
    this.api = googleApiId;
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

  public RiverSection getRiverSection() {
    return riverSection;
  }

  public void setRiverSection(RiverSection riverSection) {
    this.riverSection = riverSection;
  }
}

