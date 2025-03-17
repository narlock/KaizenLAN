package com.narlock.weighttrackapi.repository;

import com.narlock.weighttrackapi.model.WeightEntry;
import com.narlock.weighttrackapi.model.WeightEntryId;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeightEntryRepository extends JpaRepository<WeightEntry, WeightEntryId> {
  void deleteWeightEntriesByProfileId(Integer profileId);

  List<WeightEntry> getWeightEntriesByProfileId(Integer profileId);

  @Query(
      "SELECT w FROM WeightEntry w WHERE w.profileId = :profileId AND w.entryDate BETWEEN :startDate AND :endDate")
  List<WeightEntry> findByProfileIdRange(Integer profileId, LocalDate startDate, LocalDate endDate);
}
