package com.narlock.countdownworkoutapi.controller;

import com.narlock.countdownworkoutapi.model.Workout;
import com.narlock.countdownworkoutapi.model.request.WorkoutRequest;
import com.narlock.countdownworkoutapi.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutController {

  private final WorkoutService workoutService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Workout createWorkout(@RequestBody WorkoutRequest workoutRequest) {
    return workoutService.createWorkout(workoutRequest);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Workout getWorkoutById(@PathVariable("id") Long id) {
    return workoutService.getWorkoutById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Workout updateWorkout(@PathVariable("id") Long id, @RequestBody WorkoutRequest workoutRequest) {
    return workoutService.updateWorkout(id, workoutRequest);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteWorkout(@PathVariable("id") Long id) {
    workoutService.deleteWorkout(id);
  }

  @PatchMapping("/{id}/streak")
  @ResponseStatus(HttpStatus.OK)
  public Workout incrementWorkoutStreak(@PathVariable("id") Long id) {
    return workoutService.incrementWorkoutStreak(id);
  }

  @PatchMapping("/{id}/streak/reset")
  @ResponseStatus(HttpStatus.OK)
  public Workout resetWorkoutStreak(@PathVariable("id") Long id) {
    return workoutService.resetWorkoutStreak(id);
  }
}
