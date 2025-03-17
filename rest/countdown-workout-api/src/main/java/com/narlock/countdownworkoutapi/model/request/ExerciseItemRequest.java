package com.narlock.countdownworkoutapi.model.request;

import com.narlock.countdownworkoutapi.model.ExerciseItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseItemRequest {
    private Long workoutId;
    private Long exerciseId;
    private Long itemIndex;
    private Long workTime;
    private Long breakTime;

    public ExerciseItem toExerciseItem() {
        return new ExerciseItem(workoutId, exerciseId, itemIndex, workTime, breakTime);
    }
}