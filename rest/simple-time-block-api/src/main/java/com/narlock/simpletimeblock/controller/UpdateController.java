package com.narlock.simpletimeblock.controller;

import com.narlock.simpletimeblock.model.CalendarEvent;
import com.narlock.simpletimeblock.model.request.CalendarEventRequest;
import com.narlock.simpletimeblock.service.SimpleTimeBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * This is the UPDATE controller, responsible for the PATCH/PUT operations on the REST API
 *
 * @author narlock
 * @version 1.0.0
 * @since 2024-02-17
 */
@Slf4j
@RestController
@RequestMapping("/time-block/{id}")
@RequiredArgsConstructor
public class UpdateController {
  private final SimpleTimeBlockService simpleTimeBlockService;

  /**
   * Partial update for a calendar event. With this endpoint, the user can update any fields on the
   * calendar event
   *
   * @param id
   * @param request
   * @return the updated calendar event
   */
  @PatchMapping
  @ResponseStatus(HttpStatus.OK)
  public CalendarEvent updateFieldsOnCalendarEvent(
      @PathVariable("id") Integer id, @RequestBody CalendarEventRequest request) {
    return simpleTimeBlockService.updateFieldsOnCalendarEvent(id, request);
  }

  /**
   * Overwrite an existing calendar event. This is a full update.
   *
   * @param id
   * @param request
   * @return the overwritten calendar event
   */
  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public CalendarEvent overwriteCalendarEvent(
      @PathVariable("id") Integer id, @RequestBody CalendarEventRequest request) {
    return simpleTimeBlockService.overwriteCalendarEvent(id, request);
  }
}
