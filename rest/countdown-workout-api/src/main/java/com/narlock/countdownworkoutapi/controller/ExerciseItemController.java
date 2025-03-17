package com.narlock.countdownworkoutapi.controller;

import com.narlock.countdownworkoutapi.model.ExerciseItem;
import com.narlock.countdownworkoutapi.model.request.ExerciseItemRequest;
import com.narlock.countdownworkoutapi.service.ExerciseItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/exercise-item")
@RequiredArgsConstructor
public class ExerciseItemController {
    private final ExerciseItemService exerciseItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseItem createExerciseItem(@RequestBody ExerciseItemRequest exerciseItemRequest) {
        return exerciseItemService.createExerciseItem(exerciseItemRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ExerciseItem getExerciseItem(@RequestParam(name = "workoutId") Long workoutId,
                                        @RequestParam(name = "exerciseId") Long exerciseId,
                                        @RequestParam(name = "itemIndex") Long itemIndex) {
        return exerciseItemService.getExerciseItemById(workoutId, exerciseId, itemIndex);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExerciseItem(@RequestParam(name = "workoutId") Long workoutId,
                                   @RequestParam(name = "exerciseId") Long exerciseId,
                                   @RequestParam(name = "itemIndex") Long itemIndex) {
        exerciseItemService.deleteExerciseItem(workoutId, exerciseId, itemIndex);
    }
}
