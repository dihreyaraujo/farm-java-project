package com.betrybe.agrix.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 * The type Farm.
 */
@Entity
@Table(name = "farms")
public class Farm {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Double size;

  @OneToMany(mappedBy = "farm")
  private List<Crop> crops;

  public Farm() {}

  public Farm(String name, Double size) {
    this.name = name;
    this.size = size;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getSize() {
    return size;
  }

  public void setSize(Double size) {
    this.size = size;
  }

  public List<Crop> getCrops() {
    return crops;
  }

  public void setCrops(List<Crop> crops) {
    this.crops = crops;
  }

  public Long getId() {
    return this.id;
  }
}
