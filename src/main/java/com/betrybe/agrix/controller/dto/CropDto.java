package com.betrybe.agrix.controller.dto;

import com.betrybe.agrix.entity.Crop;
import java.time.LocalDate;

/**
 * The type Crop dto.
 */
public record CropDto(
    Long id,
    String name,
    Double plantedArea,
    Long farmId,
    LocalDate plantedDate,
    LocalDate harvestDate
) {

  /**
   * From entity crop dto.
   *
   * @param crop the crop
   * @return the crop dto
   */
  public static CropDto fromEntity(Crop crop) {

    return new CropDto(
        crop.getId(),
        crop.getName(),
        crop.getPlantedArea(),
        crop.getFarm().getId(),
        crop.getPlantedDate(),
        crop.getHarvestDate()

    );
  }
}
