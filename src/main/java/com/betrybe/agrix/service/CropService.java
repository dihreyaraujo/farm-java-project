package com.betrybe.agrix.service;

import com.betrybe.agrix.entity.Crop;
import com.betrybe.agrix.entity.Fertilizer;
import com.betrybe.agrix.repository.CropRepository;
import com.betrybe.agrix.repository.FarmRepository;
import com.betrybe.agrix.repository.FertilizerRepository;
import com.betrybe.agrix.service.exception.CropNotFoundException;
import com.betrybe.agrix.service.exception.FarmNotFoundException;
import com.betrybe.agrix.service.exception.FertilizerNotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Crop service.
 */
@Service
public class CropService {

  private final CropRepository cropRepository;
  private final FarmRepository farmRepository;
  private final FertilizerService fertilizerService;
  private final FertilizerRepository fertilizerRepository;

  /**
   * Instantiates a new Crop service.
   *
   * @param cropRepository    the crop repository
   * @param farmRepository    the farm repository
   * @param fertilizerService the fertilizer service
   */
  @Autowired
  public CropService(
      CropRepository cropRepository,
      FarmRepository farmRepository,
      FertilizerService fertilizerService,
      FertilizerRepository fertilizerRepository
  ) {
    this.cropRepository = cropRepository;
    this.farmRepository = farmRepository;
    this.fertilizerService = fertilizerService;
    this.fertilizerRepository = fertilizerRepository;
  }

  /**
   * Create crop.
   *
   * @param crop the crop
   * @param id   the id
   * @return the crop
   * @throws FarmNotFoundException the farm not found exception
   */
  public Crop create(Crop crop, Long id) throws FarmNotFoundException {
    farmRepository.findById(id)
        .orElseThrow(FarmNotFoundException::new);
    return cropRepository.save(crop);
  }

  /**
   * Gets all by farm id.
   *
   * @param id the id
   * @return the all by farm id
   * @throws FarmNotFoundException the farm not found exception
   */
  public List<Crop> getAllByFarmId(Long id) throws FarmNotFoundException {
    farmRepository.findById(id)
        .orElseThrow(FarmNotFoundException::new);
    return cropRepository.findByFarmId(id);
  }

  /**
   * Gets all.
   *
   * @return the all
   */
  public List<Crop> getAll() {
    return cropRepository.findAll();
  }

  public Crop findById(Long id) throws CropNotFoundException {
    return cropRepository.findById(id)
        .orElseThrow(CropNotFoundException::new);
  }

  public List<Crop> findByDates(LocalDate start, LocalDate end) {
    return cropRepository.findAllByHarvestDateBetween(start, end);
  }

  /**
   * Add crop fertilizer.
   *
   * @param cropId       the crop id
   * @param fertilizerId the fertilizer id
   * @throws CropNotFoundException       the crop not found exception
   * @throws FertilizerNotFoundException the fertilizer not found exception
   */
  public void addCropFertilizer(
      Long cropId,
      Long fertilizerId
  ) throws CropNotFoundException, FertilizerNotFoundException {
    Crop crop = findById(cropId);
    Fertilizer fertilizer = fertilizerService.getById(fertilizerId);
    crop.getFertilizers().add(fertilizer);
    cropRepository.save(crop);
  }

  public List<Fertilizer> getFertilizersByCrop(Long cropId) throws CropNotFoundException {
    findById(cropId);
    return fertilizerRepository.findAllByCropsId(cropId);
  }

}
