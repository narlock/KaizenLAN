package com.narlock.simpletimeblock.service

import com.narlock.simpletimeblock.exception.CalendarEventNotFoundException
import com.narlock.simpletimeblock.exception.NoCalendarEventOnDayException
import com.narlock.simpletimeblock.model.request.CreateRecurringCalendarEventsRequest
import com.narlock.simpletimeblock.repository.CalendarEventRepository
import spock.lang.Specification
import spock.lang.Unroll

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

import static com.narlock.simpletimeblock.util.TestUtil.*

class SimpleTimeBlockServiceSpec extends Specification {
    CalendarEventRepository calendarEventRepository = Mock()
    SimpleTimeBlockService simpleTimeBlockService = new SimpleTimeBlockService(calendarEventRepository)

    def 'get calendar event by id success'() {
        when:
        def calendarEvent = simpleTimeBlockService.getCalendarEventById(ID)

        then:
        1 * calendarEventRepository.findById(ID) >> Optional.of(CALENDAR_EVENT)
        0 * _
        calendarEvent
        calendarEvent.id == 1
        calendarEvent.name == 'Sample Event'
        calendarEvent.note == 'Sample Note'
        calendarEvent.startTime == LocalTime.of(10, 0)
        calendarEvent.endTime == LocalTime.of(12, 0)
        calendarEvent.date == LocalDate.of(2024, 2, 17)
        calendarEvent.meta == 'Sample Meta'
    }

    def 'get calendar event by id not found'() {
        when:
        simpleTimeBlockService.getCalendarEventById(ID)

        then:
        1 * calendarEventRepository.findById(ID) >> Optional.empty()
        0 * _
        thrown CalendarEventNotFoundException
    }

    def 'get all calendar events success'() {
        when:
        def calendarEvents = simpleTimeBlockService.getAllCalendarEvents()

        then:
        1 * calendarEventRepository.findAll() >> List.of(CALENDAR_EVENT)
        0 * _
        calendarEvents
        calendarEvents.size() == 1
        with(calendarEvents.get(0)) {
            id == 1
            name == 'Sample Event'
            note == 'Sample Note'
            startTime == LocalTime.of(10, 0)
            endTime == LocalTime.of(12, 0)
            date == LocalDate.of(2024, 2, 17)
            meta == 'Sample Meta'
        }
    }

    def 'get all calendar events empty'() {
        when:
        simpleTimeBlockService.getAllCalendarEvents()

        then:
        1 * calendarEventRepository.findAll() >> []
        0 * _
        thrown RuntimeException
    }

    def 'get calendar events on day success'() {
        when:
        def calendarEvents = simpleTimeBlockService.getCalendarEventsOnDay(VALID_DATE_LOCAL)

        then:
        1 * calendarEventRepository.findByDate(VALID_DATE_LOCAL) >> List.of(CALENDAR_EVENT)
        0 * _
        calendarEvents
        calendarEvents.size() == 1
        with(calendarEvents.get(0)) {
            id == 1
            name == 'Sample Event'
            note == 'Sample Note'
            startTime == LocalTime.of(10, 0)
            endTime == LocalTime.of(12, 0)
            date == LocalDate.of(2024, 2, 17)
            meta == 'Sample Meta'
        }
    }

    def 'get calendar events on day no events'() {
        when:
        simpleTimeBlockService.getCalendarEventsOnDay(VALID_DATE_LOCAL)

        then:
        1 * calendarEventRepository.findByDate(VALID_DATE_LOCAL) >> []
        0 * _
        thrown NoCalendarEventOnDayException
    }

    def 'create calendar event'() {
        when:
        def calendarEvent = simpleTimeBlockService.createCalendarEvent(CALENDAR_EVENT_REQUEST)

        then:
        1 * calendarEventRepository.save(CALENDAR_EVENT_REQUEST.toCalendarEvent()) >> CALENDAR_EVENT
        0 * _
        calendarEvent
        with(calendarEvent) {
            id == 1
            name == 'Sample Event'
            note == 'Sample Note'
            startTime == LocalTime.of(10, 0)
            endTime == LocalTime.of(12, 0)
            date == LocalDate.of(2024, 2, 17)
            meta == 'Sample Meta'
        }
    }

    @Unroll
    def 'create recurring calendar events'() {
        when:
        def response = simpleTimeBlockService.createRecurringCalendarEvents(
                new CreateRecurringCalendarEventsRequest(repeat, until, CALENDAR_EVENT_REQUEST)
        )

        then:
        response
        response.size == size
        response.startDate == startDate
        response.endDate == endDate

        where:
        repeat    | until    || size | startDate                 | endDate
        'DAILY'   | 'WEEK'   || 8    | LocalDate.of(2024, 2, 17) | LocalDate.of(2024, 2, 24)
        'WEEKLY'  | 'WEEK'   || 2    | LocalDate.of(2024, 2, 17) | LocalDate.of(2024, 2, 24)
        'MONTHLY' | 'WEEK'   || 1    | LocalDate.of(2024, 2, 17) | LocalDate.of(2024, 2, 24)
        'DAILY'   | 'MONTH'  || 32   | LocalDate.of(2024, 2, 17) | LocalDate.of(2024, 3, 19)
        'WEEKLY'  | 'MONTH'  || 5    | LocalDate.of(2024, 2, 17) | LocalDate.of(2024, 3, 19)
        'MONTHLY' | 'MONTH'  || 2    | LocalDate.of(2024, 2, 17) | LocalDate.of(2024, 3, 19)
        'DAILY'   | '2MONTH' || 63   | LocalDate.of(2024, 2, 17) | LocalDate.of(2024, 4, 19)
        'WEEKLY'  | '2MONTH' || 9    | LocalDate.of(2024, 2, 17) | LocalDate.of(2024, 4, 19)
        'MONTHLY' | '2MONTH' || 3    | LocalDate.of(2024, 2, 17) | LocalDate.of(2024, 4, 19)
        'DAILY'   | 'YEAR'   || 366  | LocalDate.of(2024, 2, 17) | LocalDate.of(2025, 2, 16)
        'WEEKLY'  | 'YEAR'   || 53   | LocalDate.of(2024, 2, 17) | LocalDate.of(2025, 2, 16)
        'MONTHLY' | 'YEAR'   || 12   | LocalDate.of(2024, 2, 17) | LocalDate.of(2025, 2, 16)
    }
}
