package com.narlock.habitapi.controller;

import com.narlock.habitapi.model.Habit;
import com.narlock.habitapi.service.HabitService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/habit")
@RequiredArgsConstructor
public class HabitController {

  private final HabitService habitService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Habit createHabit(@RequestBody Habit body) {
    return habitService.putHabit(body);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Habit> getListsByProfileId(@RequestParam Integer profileId) {
    return habitService.getHabitsByProfileId(profileId);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteHabit(@RequestParam String name, @RequestParam Integer profileId) {
    Habit habit = Habit.builder().name(name).profileId(profileId).build();
    habitService.deleteHabit(habit);
  }

  @GetMapping("/streak")
  @ResponseStatus(HttpStatus.OK)
  public Integer getStreakForHabit(@RequestBody Habit habit) {
    return habitService.getStreakForHabit(habit);
  }
}
