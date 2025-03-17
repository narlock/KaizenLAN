package com.narlock.habitapi.service;

import com.narlock.habitapi.model.Habit;
import com.narlock.habitapi.model.HabitEntry;
import com.narlock.habitapi.model.HabitEntryId;
import com.narlock.habitapi.model.HabitId;
import com.narlock.habitapi.model.error.NotFoundException;
import com.narlock.habitapi.repository.HabitEntryRepository;
import com.narlock.habitapi.repository.HabitRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitService {
  private final HabitRepository habitRepository;
  private final HabitEntryRepository habitEntryRepository;

  public Habit getHabit(String name, Integer profileId) {
    Optional<Habit> habitOptional =
        habitRepository.findById(HabitId.builder().name(name).profileId(profileId).build());
    if (habitOptional.isPresent()) {
      return habitOptional.get();
    }

    throw new NotFoundException("No habit called " + name + " for profile with id " + profileId);
  }

  public Habit putHabit(Habit habit) {
    return habitRepository.saveAndFlush(habit);
  }

  public List<Habit> getHabitsByProfileId(Integer profileId) {
    return habitRepository.getHabitsByProfileId(profileId);
  }

  public void deleteHabit(Habit habit) {
    habitRepository.delete(habit);
    List<HabitEntry> habitEntries =
        habitEntryRepository.getHabitEntriesByNameAndProfileId(
            habit.getName(), habit.getProfileId());
    for (HabitEntry habitEntry : habitEntries) {
      habitEntryRepository.delete(habitEntry);
    }
  }

  public HabitEntry createHabitEntry(HabitEntry habitEntry) {
    getHabit(habitEntry.getName(), habitEntry.getProfileId());
    return habitEntryRepository.save(habitEntry);
  }

  public List<String> getHabitCompletedDate(String name, Integer profileId) {
    getHabit(name, profileId);
    List<HabitEntry> habitEntries =
        habitEntryRepository.getHabitEntriesByNameAndProfileId(name, profileId);
    return habitEntries.stream().map(e -> e.getDate().toString()).collect(Collectors.toList());
  }

  public List<String> getHabitEntryByDate(String name, Integer profileId, LocalDate date) {
    log.info("Attempting to retrieve habit entry by date with {} {} {}", name, profileId, date);
    getHabit(name, profileId);
    HabitEntryId habitEntryId =
        HabitEntryId.builder().name(name).profileId(profileId).date(date).build();
    Optional<HabitEntry> habitEntryOptional = habitEntryRepository.findById(habitEntryId);
    if (habitEntryOptional.isPresent()) {
      return List.of(habitEntryOptional.get().getName());
    }

    throw new NotFoundException("No habit entry found on " + date);
  }

  public Integer getStreakForHabit(Habit habit) {
    getHabit(habit.getName(), habit.getProfileId());

    LocalDate date = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
    Integer streak = 0;
    try {
      while (true) {
        getHabitEntryByDate(habit.getName(), habit.getProfileId(), date);
        streak++;
        date = date.minusDays(1);
      }
    } catch (NotFoundException e) {
      return streak;
    }
  }
}
