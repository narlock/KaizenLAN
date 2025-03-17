package com.narlock.simpletimeblock.controller;

import com.narlock.simpletimeblock.model.CalendarEvent;
import com.narlock.simpletimeblock.model.request.CalendarEventRequest;
import com.narlock.simpletimeblock.model.request.CreateRecurringCalendarEventsRequest;
import com.narlock.simpletimeblock.model.response.RecurringCalendarEventsResponse;
import com.narlock.simpletimeblock.service.SimpleTimeBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * This is the POST controller, responsible for the POST operations on the REST API
 *
 * @author narlock
 * @version 1.0.0
 * @since 2024-02-17
 */
@Slf4j
@RestController
@RequestMapping("/time-block")
@RequiredArgsConstructor
public class CreateController {

  private final SimpleTimeBlockService simpleTimeBlockService;

  /**
   * Creates a single calendar event based on the request payload
   *
   * @param request
   * @return the created calendar event
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CalendarEvent createCalendarEvent(@RequestBody CalendarEventRequest request) {
    return simpleTimeBlockService.createCalendarEvent(request);
  }

  /**
   * Create a set of recurring calendar events. This endpoint can be used for setting up the same
   * event to have it occur on different days
   *
   * @param request
   * @return
   */
  @PostMapping("/recurring")
  @ResponseStatus(HttpStatus.CREATED)
  public RecurringCalendarEventsResponse createRecurringCalendarEvents(
      @RequestBody CreateRecurringCalendarEventsRequest request) {
    return simpleTimeBlockService.createRecurringCalendarEvents(request);
  }
}
