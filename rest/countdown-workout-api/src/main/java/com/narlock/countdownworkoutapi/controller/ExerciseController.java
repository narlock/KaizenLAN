package com.narlock.countdownworkoutapi.controller;

import com.narlock.countdownworkoutapi.model.Exercise;
import com.narlock.countdownworkoutapi.model.request.ExerciseRequest;
import com.narlock.countdownworkoutapi.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Exercise createExercise(@RequestBody ExerciseRequest exerciseRequest) {
        return exerciseService.createExercise(exerciseRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Exercise getExerciseById(@PathVariable("id") Long id) {
        return exerciseService.getExerciseById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Exercise updateExercise(@PathVariable("id") Long id, @RequestBody ExerciseRequest exerciseRequest) {
        return exerciseService.updateExercise(id, exerciseRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExercise(@PathVariable("id") Long id) {
        exerciseService.deleteExercise(id);
    }
}
