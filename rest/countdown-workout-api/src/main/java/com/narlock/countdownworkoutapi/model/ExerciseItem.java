package com.narlock.countdownworkoutapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "ExerciseItem")
@IdClass(ExerciseItemId.class)
public class ExerciseItem {

    @Id
    @Column(name = "workout_id")
    private Long workoutId;

    @Id
    @Column(name = "exercise_id")
    private Long exerciseId;

    @Id
    private Long itemIndex;

    private Long workTime;
    private Long breakTime;
}
