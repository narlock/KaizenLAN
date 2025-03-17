package com.narlock.weighttrackapi.service;

import com.narlock.weighttrackapi.model.WeightEntry;
import com.narlock.weighttrackapi.model.WeightEntryId;
import com.narlock.weighttrackapi.model.error.NotFoundException;
import com.narlock.weighttrackapi.repository.WeightEntryRepository;
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
public class WeightTrackService {

  private final WeightEntryRepository weightEntryRepository;

  public WeightEntry createWeightEntry(WeightEntry weightEntry) {
    return weightEntryRepository.save(weightEntry);
  }

  public WeightEntry updateWeightEntry(WeightEntry weightEntry) {
    return weightEntryRepository.saveAndFlush(weightEntry);
  }

  @Transactional
  public void deleteWeightEntriesByProfileId(Integer profileId) {
    weightEntryRepository.deleteWeightEntriesByProfileId(profileId);
  }

  public List<WeightEntry> getWeightEntryById(Integer profileId, LocalDate entryDate) {
    WeightEntryId weightEntryId =
        WeightEntryId.builder().profileId(profileId).entryDate(entryDate).build();
    Optional<WeightEntry> weightEntryOptional = weightEntryRepository.findById(weightEntryId);
    if (weightEntryOptional.isPresent()) {
      return List.of(weightEntryOptional.get());
    }

    throw new NotFoundException(
        "Weight entry for profile with id " + profileId + " not found on " + entryDate);
  }

  public List<WeightEntry> getWeightEntries(Integer profileId) {
    return weightEntryRepository.getWeightEntriesByProfileId(profileId);
  }

  public List<WeightEntry> getWeightEntriesRange(
      Integer profileId, LocalDate startDate, LocalDate endDate) {
    return weightEntryRepository.findByProfileIdRange(profileId, startDate, endDate);
  }
}
