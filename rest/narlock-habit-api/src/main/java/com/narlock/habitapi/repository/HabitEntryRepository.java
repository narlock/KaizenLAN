package com.narlock.habitapi.repository;

import com.narlock.habitapi.model.HabitEntry;
import com.narlock.habitapi.model.HabitEntryId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitEntryRepository extends JpaRepository<HabitEntry, HabitEntryId> {
  List<HabitEntry> getHabitEntriesByNameAndProfileId(String name, Integer profileId);
}
