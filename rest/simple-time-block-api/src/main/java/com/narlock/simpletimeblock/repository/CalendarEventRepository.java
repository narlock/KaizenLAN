package com.narlock.simpletimeblock.repository;

import com.narlock.simpletimeblock.model.CalendarEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Integer> {

  // Create a time-blocked event: insert a new entry into the table
  CalendarEvent save(CalendarEvent event);

  // Overwrite an existing time-blocked event: update all fields of a specific entry in a table
  CalendarEvent saveAndFlush(CalendarEvent event);

  // Delete an existing time-blocked event by id
  void deleteById(Integer id);

  // Delete by date - transactional is required for custom query
  @Modifying
  @Transactional
  @Query("DELETE FROM CalendarEvent ce WHERE ce.date = :date")
  void deleteByDate(@Param("date") LocalDate date);

  // Retrieve all events that match a given date
  List<CalendarEvent> findByDate(LocalDate date);

  // Retrieve a specific time-blocked event
  Optional<CalendarEvent> findById(Integer id);
}
