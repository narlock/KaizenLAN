package com.narlock.simpletimeblock.service;

import static com.narlock.simpletimeblock.util.RecurringEventParser.*;

import com.narlock.simpletimeblock.exception.CalendarEventNotFoundException;
import com.narlock.simpletimeblock.exception.NoCalendarEventOnDayException;
import com.narlock.simpletimeblock.model.CalendarEvent;
import com.narlock.simpletimeblock.model.request.CalendarEventRequest;
import com.narlock.simpletimeblock.model.request.CreateRecurringCalendarEventsRequest;
import com.narlock.simpletimeblock.model.response.RecurringCalendarEventsResponse;
import com.narlock.simpletimeblock.repository.CalendarEventRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleTimeBlockService {
  private final CalendarEventRepository calendarEventRepository;

  /**
   * Service attempts to retrieve a calendar event from the back end database. If the CalendarEvent
   * is present it will be returned, otherwise, it is not found.
   *
   * @param id
   * @return CalendarEvent matching id if found
   */
  public CalendarEvent getCalendarEventById(Integer id) {
    Optional<CalendarEvent> calendarEventOptional = calendarEventRepository.findById(id);
    if (calendarEventOptional.isPresent()) {
      log.info("Calendar event successfully found for id {}", id);
      return calendarEventOptional.get();
    } else {
      log.error("Calendar event not found for id {}", id);
      throw new CalendarEventNotFoundException(id, "Calendar event not found");
    }
  }

  /**
   * Service will retrieve all calendar events on the back end database. If no calendar events are
   * on the back end system, an exception will be thrown to indicate as such.
   *
   * @return all calendar events on back end database
   */
  public List<CalendarEvent> getAllCalendarEvents() {
    List<CalendarEvent> calendarEvents = calendarEventRepository.findAll();
    if (!calendarEvents.isEmpty()) {
      return calendarEvents;
    } else {
      final String message = "No calendar events exist on back-end database";
      log.error(message);
      throw new RuntimeException(message);
    }
  }

  /**
   * Service will retrieve all calendar events on the back end database that match date parameter.
   * If no calendar events are on the back end system, an exception will be thrown to indicate that
   * no calendar events exist for the date.
   *
   * @param date
   * @return calendar events that match date parameter
   */
  public List<CalendarEvent> getCalendarEventsOnDay(LocalDate date) {
    List<CalendarEvent> calendarEvents = calendarEventRepository.findByDate(date);
    if (!calendarEvents.isEmpty()) {
      return calendarEvents;
    } else {
      final String message = "No calendar events were found on date " + date;
      log.error(message);
      throw new NoCalendarEventOnDayException(date, message);
    }
  }

  /**
   * Creates a calendar event
   *
   * @param request
   * @return the created calendar event
   */
  public CalendarEvent createCalendarEvent(CalendarEventRequest request) {
    return calendarEventRepository.save(request.toCalendarEvent());
  }

  /**
   * Creates a set of calendar events according to the request
   *
   * @param request
   * @return response containing amount of events created
   */
  public RecurringCalendarEventsResponse createRecurringCalendarEvents(
      CreateRecurringCalendarEventsRequest request) {
    List<CalendarEventRequest> eventsToCreate = new ArrayList<>();

    // Parse repeat and determine the days to repeat
    int repeatDays = parseRepeatDays(request.getRepeat());

    // Calculate the end date based on the until field
    LocalDate endDate = calculateEndDate(request.getEvent().getDate(), request.getUntil());

    // Generate recurring events
    LocalDate currentDate = request.getEvent().getDate();
    while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
      eventsToCreate.add(createEventFromRequest(request, currentDate));
      currentDate = currentDate.plusDays(repeatDays);
    }

    // Save events to the repository
    for (CalendarEventRequest eventRequest : eventsToCreate) {
      calendarEventRepository.save(eventRequest.toCalendarEvent());
    }

    // Return response with the amount of events created
    return new RecurringCalendarEventsResponse(
        eventsToCreate.size(), request.getEvent().getDate(), endDate, request.getRepeat());
  }

  /**
   * Partial update fields on a calendar event
   *
   * @param id
   * @param request
   * @return The calendar event that was updated
   */
  public CalendarEvent updateFieldsOnCalendarEvent(Integer id, CalendarEventRequest request) {
    // Retrieve current CalendarEvent
    CalendarEvent currentCalendarEvent = getCalendarEventById(id);

    // Update fields from currentCalendarEvent if request field is not null
    Optional.ofNullable(request.getName()).ifPresent(currentCalendarEvent::setName);
    Optional.ofNullable(request.getNote()).ifPresent(currentCalendarEvent::setNote);
    Optional.ofNullable(request.getStartTime()).ifPresent(currentCalendarEvent::setStartTime);
    Optional.ofNullable(request.getEndTime()).ifPresent(currentCalendarEvent::setEndTime);
    Optional.ofNullable(request.getDate()).ifPresent(currentCalendarEvent::setDate);
    Optional.ofNullable(request.getMeta()).ifPresent(currentCalendarEvent::setMeta);

    return calendarEventRepository.saveAndFlush(currentCalendarEvent);
  }

  /**
   * Overwrite an existing calendar event
   *
   * @param id
   * @param request
   * @return the calendar event that was updated
   */
  public CalendarEvent overwriteCalendarEvent(Integer id, CalendarEventRequest request) {
    CalendarEvent calendarEvent =
        new CalendarEvent(
            id,
            request.getName(),
            request.getNote(),
            request.getStartTime(),
            request.getEndTime(),
            request.getDate(),
            request.getMeta());
    return calendarEventRepository.saveAndFlush(calendarEvent);
  }

  /**
   * Delete a calendar event by its id
   *
   * @param id
   */
  public void deleteCalendarEventById(Integer id) {
    calendarEventRepository.deleteById(id);
    log.info("Successfully deleted calendar event with id {}", id);
  }

  /**
   * Delete a calendar event by its date
   *
   * @param date
   */
  public void deleteCalendarEventByDate(LocalDate date) {
    calendarEventRepository.deleteByDate(date);
    log.info("Successfully deleted calendar event with date {}", date);
  }
}
