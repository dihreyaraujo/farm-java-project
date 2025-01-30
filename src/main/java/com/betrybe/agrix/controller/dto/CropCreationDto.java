package com.betrybe.agrix.controller.dto;

import com.betrybe.agrix.entity.Crop;
import com.betrybe.agrix.entity.Farm;
import java.time.LocalDate;

/**
 * The type Crop creation dto.
 */
public record CropCreationDto(
    String name,
    Double plantedArea,
    LocalDate plantedDate,
    LocalDate harvestDate
) {
  public Crop toEntity(Farm farm) {
    return new Crop(name, plantedArea, farm, plantedDate, harvestDate);
  }
}
