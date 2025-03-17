package com.narlock.simpletimeblock.controller;

import com.narlock.simpletimeblock.model.CalendarEvent;
import com.narlock.simpletimeblock.service.SimpleTimeBlockService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

/**
 * This is the READ controller, responsible for the GET operations on the REST API
 *
 * @author narlock
 * @version 1.0.0
 * @since 2024-02-17
 */
@Slf4j
@RestController
@RequestMapping("/time-block")
@RequiredArgsConstructor
public class ReadController {

  private final SimpleTimeBlockService simpleTimeBlockService;

  /**
   * Retrieve a time-blocked event
   *
   * @param id
   * @return calendar event associated to the id parameter
   */
  @GetMapping("/{id}")
  public CalendarEvent getCalendarEventById(@PathVariable("id") Integer id) {
    log.info("Received request to retrieve calendar event with id {}", id);
    return simpleTimeBlockService.getCalendarEventById(id);
  }

  /**
   * Retrieve a collection of time-blocked events. If the date parameter is not present, this
   * endpoint will retrieve all calendar events on the back end database. If the date parameter is
   * present, this endpoint will retrieve all calendar events matching the date parameter.
   *
   * @param date
   * @return collection of calendar events
   */
  @GetMapping
  public List<CalendarEvent> getCalendarEvents(
      @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate date) {
    return date == null
        ? simpleTimeBlockService.getAllCalendarEvents()
        : simpleTimeBlockService.getCalendarEventsOnDay(date);
  }
}
