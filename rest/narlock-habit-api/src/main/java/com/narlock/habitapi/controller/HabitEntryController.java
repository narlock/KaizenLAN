package com.narlock.habitapi.controller;

import com.narlock.habitapi.model.HabitEntry;
import com.narlock.habitapi.service.HabitService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/habit-entry")
@RequiredArgsConstructor
public class HabitEntryController {

  private final HabitService habitService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public HabitEntry createHabitEntry(@RequestBody HabitEntry habitEntry) {
    return habitService.createHabitEntry(habitEntry);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<String> getDateEntriesForHabit(
      @RequestParam String name,
      @RequestParam Integer profileId,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate date) {
    if (date != null) {
      return habitService.getHabitEntryByDate(name, profileId, date);
    }
    return habitService.getHabitCompletedDate(name, profileId);
  }
}
