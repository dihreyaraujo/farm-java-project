package com.betrybe.agrix.controller;

import com.betrybe.agrix.controller.dto.CropDto;
import com.betrybe.agrix.controller.dto.FertilizerDto;
import com.betrybe.agrix.entity.Crop;
import com.betrybe.agrix.entity.Fertilizer;
import com.betrybe.agrix.service.CropService;
import com.betrybe.agrix.service.exception.CropNotFoundException;
import com.betrybe.agrix.service.exception.FertilizerNotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Crop controller.
 */
@RestController
@RequestMapping(value = "/crops")
public class CropController {

  private final CropService cropService;

  @Autowired
  public CropController(CropService cropService) {
    this.cropService = cropService;
  }

  /**
   * Gets all crops.
   *
   * @return the all crops
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAuthority('ROLE_ADMIN') "
      + "or hasAuthority('ROLE_MANAGER')")
  public List<CropDto> getAllCrops() {
    List<Crop> allCrops = cropService.getAll();
    return allCrops.stream()
        .map(CropDto::fromEntity)
        .toList();
  }

  /**
   * Gets crop by id.
   *
   * @param id the id
   * @return the crop by id
   * @throws CropNotFoundException the crop not found exception
   */
  @GetMapping("/{id}")
  public CropDto getCropById(@PathVariable Long id) throws CropNotFoundException {
    return CropDto.fromEntity(
        cropService.findById(id)
    );
  }

  /**
   * Gets crop by harvest date.
   *
   * @param start the start
   * @param end   the end
   * @return the crop by harvest date
   */
  @GetMapping("/search")
  @ResponseStatus(HttpStatus.OK)
  public List<CropDto> getCropByHarvestDate(
      @RequestParam String start,
      @RequestParam String end
  ) {
    List<Crop> allCropsByDate = cropService
        .findByDates(
            LocalDate.parse(start),
            LocalDate.parse(end)
        );
    return allCropsByDate.stream()
        .map(CropDto::fromEntity)
        .toList();
  }

  /**
   * Create crop fertilizer crop dto.
   *
   * @param cropId       the crop id
   * @param fertilizerId the fertilizer id
   * @return the crop dto
   * @throws CropNotFoundException       the crop not found exception
   * @throws FertilizerNotFoundException the fertilizer not found exception
   */
  @PostMapping("/{cropId}/fertilizers/{fertilizerId}")
  @ResponseStatus(HttpStatus.CREATED)
  public String createCropFertilizer(
      @PathVariable Long cropId,
      @PathVariable Long fertilizerId
  ) throws CropNotFoundException, FertilizerNotFoundException {
    cropService.addCropFertilizer(cropId, fertilizerId);
    return "Fertilizante e plantação associados com sucesso!";
  }

  /**
   * Gets all fertilizers by crop.
   *
   * @param cropId the crop id
   * @return the all fertilizers by crop
   * @throws CropNotFoundException the crop not found exception
   */
  @GetMapping("/{cropId}/fertilizers")
  public List<FertilizerDto> getAllFertilizersByCrop(
      @PathVariable Long cropId
  ) throws CropNotFoundException {
    List<Fertilizer> allFertilizers = cropService.getFertilizersByCrop(cropId);
    return allFertilizers.stream()
        .map(FertilizerDto::fromEntity)
        .toList();
  }

}
