package com.narlock.countdownworkoutapi.model.request;

import com.narlock.countdownworkoutapi.model.Exercise;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseRequest {
    private String name;
    private String imageUrl;

    public Exercise toExercise() {
        return new Exercise(name, imageUrl);
    }
}
