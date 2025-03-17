package com.narlock.watertrackapi.repository;

import com.narlock.watertrackapi.model.WaterEntry;
import com.narlock.watertrackapi.model.WaterEntryId;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WaterEntryRepository extends JpaRepository<WaterEntry, WaterEntryId> {
  void deleteAllWaterEntriesByProfileId(Integer profileId);

  List<WaterEntry> getWaterEntriesByProfileId(Integer profileId);

  @Query(
      "SELECT w FROM WaterEntry w WHERE w.profileId = :profileId AND w.entryDate BETWEEN :startDate AND :endDate")
  List<WaterEntry> findByEntryDateBetween(
      Integer profileId, LocalDate startDate, LocalDate endDate);
}
