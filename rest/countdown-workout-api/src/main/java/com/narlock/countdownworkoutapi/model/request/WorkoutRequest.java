package com.narlock.countdownworkoutapi.model.request;

import com.narlock.countdownworkoutapi.model.Workout;
import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutRequest {
  private String name;

  public Workout toWorkout() {
    return new Workout(name);
  }
}
