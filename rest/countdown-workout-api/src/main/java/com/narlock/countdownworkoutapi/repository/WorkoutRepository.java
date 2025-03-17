package com.narlock.countdownworkoutapi.repository;

import com.narlock.countdownworkoutapi.model.Workout;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
  Workout save(Workout entry);

  void deleteById(Long id);

  Optional<Workout> findById(Long id);
}
