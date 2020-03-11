package com.flow.opal.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class RiverSection {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;
  private double waterLevel;
  private double km;
  private boolean isRunnable;
  private double longitude;
  private double latitude;
  @ManyToOne
  @JsonIgnore
  private River river;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "riverSection", fetch = FetchType.EAGER)
  private List<RiverPlace> places;

  public RiverSection() {
  }

  public RiverSection(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public River getRiver() {
    return river;
  }

  public void setRiver(River river) {
    this.river = river;
  }

  public double getWaterLevel() {
    return waterLevel;
  }

  public void setWaterLevel(double waterLevel) {
    this.waterLevel = waterLevel;
  }

  public double getKm() {
    return km;
  }

  public void setKm(double km) {
    this.km = km;
  }

  public List<RiverPlace> getPlaces() {
    return places;
  }

  public void setPlaces(List<RiverPlace> places) {
    this.places = places;
  }

  public boolean isRunnable() {
    return isRunnable;
  }

  public void setRunnable(boolean runnable) {
    isRunnable = runnable;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public void addRiverPlace(RiverPlace riverPlace) {
    riverPlace.setRiverSection(this);
    places.add(riverPlace);
  }
}
