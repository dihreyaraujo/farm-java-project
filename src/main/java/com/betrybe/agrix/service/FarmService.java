package com.betrybe.agrix.service;

import com.betrybe.agrix.entity.Farm;
import com.betrybe.agrix.repository.FarmRepository;
import com.betrybe.agrix.service.exception.FarmNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Farm service.
 */
@Service
public class FarmService {

  private final FarmRepository farmRepository;

  @Autowired
  public FarmService(FarmRepository farmRepository) {
    this.farmRepository = farmRepository;
  }

  public Farm create(Farm farm) {
    return farmRepository.save(farm);
  }

  public List<Farm> getAll() {
    return farmRepository.findAll();
  }

  public Farm getById(Long id) throws FarmNotFoundException {
    return farmRepository.findById(id).orElseThrow(FarmNotFoundException::new);
  }

}
