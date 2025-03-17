package com.narlock.countdownworkoutapi.service;

import com.narlock.countdownworkoutapi.model.Workout;
import com.narlock.countdownworkoutapi.model.error.NotFoundException;
import com.narlock.countdownworkoutapi.model.request.WorkoutRequest;
import com.narlock.countdownworkoutapi.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkoutService {

  private final WorkoutRepository workoutRepository;

  public Workout createWorkout(WorkoutRequest workoutRequest) {
    return workoutRepository.save(workoutRequest.toWorkout());
  }

  public Workout getWorkoutById(Long id) {
    Optional<Workout> workoutOptional = workoutRepository.findById(id);
    if(workoutOptional.isPresent()) {

      // TODO need to find each exerciseItem associated to this workout
      // need to add the other services...
      return workoutOptional.get();
    }

    throw new NotFoundException("Workout with id " + id + " was not found");
  }

  public Workout updateWorkout(Long id, WorkoutRequest workoutRequest) {
    Workout workout = workoutRequest.toWorkout();
    workout.setId(id);
    return workoutRepository.save(workout);
  }

  public void deleteWorkout(Long id) {
    workoutRepository.deleteById(id);
  }

  public Workout incrementWorkoutStreak(Long id) {
    Workout workout = getWorkoutById(id);
    workout.setStreak(workout.getStreak() + 1);
    return workoutRepository.save(workout);
  }

  public Workout resetWorkoutStreak(Long id) {
    Workout workout = getWorkoutById(id);
    workout.setStreak(0);
    return workoutRepository.save(workout);
  }
}
