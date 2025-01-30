package com.betrybe.agrix.controller;

import com.betrybe.agrix.controller.dto.CropCreationDto;
import com.betrybe.agrix.controller.dto.CropDto;
import com.betrybe.agrix.controller.dto.FarmCreationDto;
import com.betrybe.agrix.controller.dto.FarmDto;
import com.betrybe.agrix.entity.Crop;
import com.betrybe.agrix.entity.Farm;
import com.betrybe.agrix.service.CropService;
import com.betrybe.agrix.service.FarmService;
import com.betrybe.agrix.service.exception.FarmNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Farm controller.
 */
@RestController
@RequestMapping(value = "/farms")
public class FarmController {

  private final FarmService farmService;
  private final CropService cropService;

  @Autowired
  public FarmController(FarmService farmService, CropService cropService) {
    this.farmService = farmService;
    this.cropService = cropService;
  }

  /**
   * Create farm dto.
   *
   * @param farmCreationDto the farm creation dto
   * @return the farm dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public FarmDto createFarm(@RequestBody FarmCreationDto farmCreationDto) {
    return FarmDto.fromEntity(
        farmService.create(farmCreationDto.toEntity())
    );
  }

  /**
   * Gets all farms.
   *
   * @return the all farms
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAuthority('ROLE_ADMIN') "
      + "or hasAuthority('ROLE_USER') or"
      + " hasAuthority('ROLE_MANAGER')")
  public List<FarmDto> getAllFarms() {
    List<Farm> allFarms = farmService.getAll();
    return allFarms.stream()
        .map(FarmDto::fromEntity)
        .toList();
  }

  /**
   * Gets farm by id.
   *
   * @param id the id
   * @return the farm by id
   * @throws FarmNotFoundException the farm not found exception
   */
  @GetMapping("/{id}")
  public FarmDto getFarmById(@PathVariable Long id) throws FarmNotFoundException {
    return FarmDto.fromEntity(
        farmService.getById(id)
    );
  }

  /**
   * Create crop crop dto.
   *
   * @param farmId          the farm id
   * @param cropCreationDto the crop creation dto
   * @return the crop dto
   * @throws FarmNotFoundException the farm not found exception
   */
  @PostMapping("/{farmId}/crops")
  @ResponseStatus(HttpStatus.CREATED)
  public CropDto createCrop(
      @PathVariable Long farmId,
      @RequestBody CropCreationDto cropCreationDto
  ) throws FarmNotFoundException {
    return CropDto.fromEntity(
        cropService.create(cropCreationDto.toEntity(farmService.getById(farmId)), farmId)
    );
  }

  /**
   * Gets crop by farm id.
   *
   * @param farmId the farm id
   * @return the crop by farm id
   * @throws FarmNotFoundException the farm not found exception
   */
  @GetMapping("/{farmId}/crops")
  @ResponseStatus(HttpStatus.OK)
  public List<CropDto> getCropByFarmId(@PathVariable Long farmId) throws FarmNotFoundException {
    List<Crop> allCrops = cropService.getAllByFarmId(farmId);
    return allCrops.stream()
        .map(CropDto::fromEntity)
        .toList();
  }

}
