package com.narlock.countdownworkoutapi.repository;

import com.narlock.countdownworkoutapi.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long>  {
    Exercise save(Exercise entry);

    void deleteById(Long id);

    Optional<Exercise> findById(Long id);
}
