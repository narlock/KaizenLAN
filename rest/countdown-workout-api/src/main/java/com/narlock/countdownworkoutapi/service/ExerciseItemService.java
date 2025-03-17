package com.narlock.countdownworkoutapi.service;

import com.narlock.countdownworkoutapi.model.ExerciseItem;
import com.narlock.countdownworkoutapi.model.ExerciseItemId;
import com.narlock.countdownworkoutapi.model.error.NotFoundException;
import com.narlock.countdownworkoutapi.model.request.ExerciseItemRequest;
import com.narlock.countdownworkoutapi.repository.ExerciseItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExerciseItemService {
    private final ExerciseItemRepository exerciseItemRepository;

    public ExerciseItem createExerciseItem(ExerciseItemRequest exerciseItemRequest) {
        return exerciseItemRepository.save(exerciseItemRequest.toExerciseItem());
    }

    public ExerciseItem getExerciseItemById(Long workoutId, Long exerciseId, Long itemIndex) {
        ExerciseItemId exerciseItemId = new ExerciseItemId(workoutId, exerciseId, itemIndex);
        Optional<ExerciseItem> exerciseItemOptional = exerciseItemRepository.findById(exerciseItemId);
        if (exerciseItemOptional.isPresent()) {
            return exerciseItemOptional.get();
        }

        throw new NotFoundException("No exercise item for workoutId " + workoutId + " and exerciseId " + exerciseId + " found");
    }

    public void deleteExerciseItem(Long workoutId, Long exerciseId, Long itemIndex) {
        ExerciseItemId exerciseItemId = new ExerciseItemId(workoutId, exerciseId, itemIndex);
        exerciseItemRepository.deleteById(exerciseItemId);
    }
}
