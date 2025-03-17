package com.narlock.habitapi.repository;

import com.narlock.habitapi.model.Habit;
import com.narlock.habitapi.model.HabitId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, HabitId> {
  List<Habit> getHabitsByProfileId(Integer profileId);
}
