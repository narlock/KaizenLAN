package com.narlock.simpletimeblock.controller;

import com.narlock.simpletimeblock.service.SimpleTimeBlockService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * This is the DELETE controller, responsible for the DELETE operations on the REST API
 *
 * @author narlock
 * @version 1.0.0
 * @since 2024-02-17
 */
@Slf4j
@RestController
@RequestMapping("/time-block")
@RequiredArgsConstructor
public class DeleteController {
  private final SimpleTimeBlockService simpleTimeBlockService;

  /**
   * Given a calendar event's id, this endpoint will delete the endpoint from the back end database.
   *
   * @param id
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCalendarEvent(@PathVariable("id") Integer id) {
    simpleTimeBlockService.deleteCalendarEventById(id);
  }

  /**
   * Given a date, this endpoint will delete all matching calendar events that occur on that date.
   *
   * @param date
   */
  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCalendarEventByDate(
      @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate date) {
    simpleTimeBlockService.deleteCalendarEventByDate(date);
  }
}
