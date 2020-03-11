package com.flow.opal.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class River {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String riverName;
  private double km;
  @JsonIgnore
  @OneToMany(cascade = CascadeType.MERGE, mappedBy = "river", fetch = FetchType.EAGER)
  private List<RiverSection> sections;

  public River() {
  }

  public River(String riverName) {
    this.riverName = riverName;
  }

  public River(String riverName, double km) {
    this.riverName = riverName;
    this.km = km;
  }

  public River(long riverId, String riverName, List<RiverSection> sections, double km) {
    this.id = riverId;
    this.riverName = riverName;
    this.sections = sections;
    this.km = km;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getRiverName() {
    return riverName;
  }

  public void setRiverName(String riverName) {
    this.riverName = riverName;
  }

  public double getKm() {
    return km;
  }

  public void setKm(double km) {
    this.km = km;
  }

  public List<RiverSection> getSections() {
    return sections;
  }

  public void setSections(List<RiverSection> sections) {
    this.sections = sections;
  }

  public void addRiverSection(RiverSection riverSection) {
    riverSection.setRiver(this);
    sections.add(riverSection);
  }
}
