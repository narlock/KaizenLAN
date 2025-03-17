package com.narlock.watertrackapi.service;

import com.narlock.watertrackapi.model.WaterEntry;
import com.narlock.watertrackapi.model.WaterEntryId;
import com.narlock.watertrackapi.model.error.NotFoundException;
import com.narlock.watertrackapi.repository.WaterEntryRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaterEntryService {
  private final WaterEntryRepository waterEntryRepository;

  public WaterEntry createWaterEntry(WaterEntry waterEntry) {
    return waterEntryRepository.save(waterEntry);
  }

  public WaterEntry getWaterEntryById(WaterEntryId waterEntryId) {
    Optional<WaterEntry> waterEntryOptional = waterEntryRepository.findById(waterEntryId);
    if (waterEntryOptional.isPresent()) {
      return waterEntryOptional.get();
    }

    throw new NotFoundException(
        "No water entry found on "
            + waterEntryId.getEntryDate()
            + " for profile with id "
            + waterEntryId.getProfileId());
  }

  public WaterEntry addWaterToEntry(WaterEntry waterEntry) {
    WaterEntry existingEntry =
        getWaterEntryById(
            WaterEntryId.builder()
                .profileId(waterEntry.getProfileId())
                .entryDate(waterEntry.getEntryDate())
                .build());
    existingEntry.setEntryAmount(existingEntry.getEntryAmount() + waterEntry.getEntryAmount());
    return waterEntryRepository.saveAndFlush(existingEntry);
  }

  @Transactional
  public void deleteByProfileId(Integer profileId) {
    waterEntryRepository.deleteAllWaterEntriesByProfileId(profileId);
  }

  public List<WaterEntry> getWaterEntriesByProfileId(Integer profileId) {
    return waterEntryRepository.getWaterEntriesByProfileId(profileId);
  }

  public List<WaterEntry> getWaterEntriesByRange(
      Integer profileId, LocalDate startDate, LocalDate endDate) {
    return waterEntryRepository.findByEntryDateBetween(profileId, startDate, endDate);
  }
}
