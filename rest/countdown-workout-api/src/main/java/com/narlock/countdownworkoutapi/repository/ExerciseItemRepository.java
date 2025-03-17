package com.narlock.countdownworkoutapi.repository;

import com.narlock.countdownworkoutapi.model.ExerciseItem;
import com.narlock.countdownworkoutapi.model.ExerciseItemId;
import org.springframework.data.repository.CrudRepository;

public interface ExerciseItemRepository extends CrudRepository<ExerciseItem, ExerciseItemId> {
}
