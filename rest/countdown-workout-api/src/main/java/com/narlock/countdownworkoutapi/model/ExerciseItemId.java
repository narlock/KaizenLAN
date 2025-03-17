package com.narlock.countdownworkoutapi.model;

import java.io.Serializable;
import java.util.Objects;

public class ExerciseItemId implements Serializable {
    private Long exerciseId;
    private Long workoutId;

    private Long itemIndex;

    public ExerciseItemId() {}

    public ExerciseItemId(Long workoutId, Long exerciseId, Long itemIndex) {
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
        this.itemIndex = itemIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseItemId that = (ExerciseItemId) o;
        return Objects.equals(exerciseId, that.exerciseId) &&
                Objects.equals(workoutId, that.workoutId) && Objects.equals(itemIndex, that.itemIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exerciseId, workoutId, itemIndex);
    }
}
