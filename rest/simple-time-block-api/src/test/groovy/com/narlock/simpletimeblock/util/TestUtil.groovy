package com.narlock.simpletimeblock.util

import com.narlock.simpletimeblock.model.CalendarEvent
import com.narlock.simpletimeblock.model.request.CalendarEventRequest
import com.narlock.simpletimeblock.model.request.CreateRecurringCalendarEventsRequest
import com.narlock.simpletimeblock.model.response.RecurringCalendarEventsResponse

import java.time.LocalDate
import java.time.LocalTime

class TestUtil {
    public static final Integer ID = 1
    public static final String VALID_DATE_STRING = '2024-02-17'
    public static final LocalDate VALID_DATE_LOCAL = LocalDate.of(2024, 2, 17)
    public static final CalendarEvent CALENDAR_EVENT = new CalendarEvent(
            id: 1,
            name: 'Sample Event',
            note: 'Sample Note',
            startTime: LocalTime.of(10, 0),
            endTime: LocalTime.of(12, 0),
            date: LocalDate.of(2024, 2, 17),
            meta: 'Sample Meta'
    )
    public static final CalendarEventRequest CALENDAR_EVENT_REQUEST = new CalendarEventRequest(
            name: 'Sample Event',
            note: 'Sample Note',
            startTime: LocalTime.of(10, 0),
            endTime: LocalTime.of(12, 0),
            date: LocalDate.of(2024, 2, 17),
            meta: 'Sample Meta'
    )
    public static final CreateRecurringCalendarEventsRequest CREATE_RECURRING_CALENDAR_EVENTS_REQUEST = new CreateRecurringCalendarEventsRequest(
            repeat: "DAILY",
            until: "WEEK",
            event: CALENDAR_EVENT_REQUEST
    )
    public static final RecurringCalendarEventsResponse RECURRING_CALENDAR_EVENTS_RESPONSE = new RecurringCalendarEventsResponse(
            size: 1,
            startDate: LocalDate.of(2024, 2, 17),
            endDate: LocalDate.of(2024, 2, 17),
            repeat: 'DAILY'
    )
}
