package com.narlock.countdownworkoutapi.service;

import com.narlock.countdownworkoutapi.model.Exercise;
import com.narlock.countdownworkoutapi.model.error.NotFoundException;
import com.narlock.countdownworkoutapi.model.request.ExerciseRequest;
import com.narlock.countdownworkoutapi.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public Exercise createExercise(ExerciseRequest exerciseRequest) {
        return exerciseRepository.save(exerciseRequest.toExercise());
    }

    public Exercise getExerciseById(Long id) {
        Optional<Exercise> exerciseOptional = exerciseRepository.findById(id);
        if(exerciseOptional.isPresent()) {
            return exerciseOptional.get();
        }

        throw new NotFoundException("Exercise with id " + id + " was not found");
    }

    public Exercise updateExercise(Long id, ExerciseRequest exerciseRequest) {
        Exercise exercise = exerciseRequest.toExercise();
        exercise.setId(id);
        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }
}
