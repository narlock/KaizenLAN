package com.narlock.simpletimeblock.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.databind.SerializationFeature
import com.narlock.simpletimeblock.exception.CalendarEventNotFoundException
import com.narlock.simpletimeblock.exception.NoCalendarEventOnDayException
import com.narlock.simpletimeblock.exception.response.ErrorResponse;
import com.narlock.simpletimeblock.model.CalendarEvent
import com.narlock.simpletimeblock.service.SimpleTimeBlockService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

import static com.narlock.simpletimeblock.util.TestUtil.*

@SpringBootTest
@AutoConfigureMockMvc
class ReadControllerSpec extends Specification {
    @Autowired
    MockMvc mockMvc

    @SpringBean
    SimpleTimeBlockService simpleTimeBlockService = Mock()

    ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        // We configure the object mapper this way so that we can properly serialize the
        // response bodies into LocalTime
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
    }

    def 'get calendar item by id success'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.get("/time-block/$ID")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn()

        then:
        result
        result.response.status == 200
        1 * simpleTimeBlockService.getCalendarEventById(ID) >> CALENDAR_EVENT
        0 * _

        and:
        def responseEvent = objectMapper.readValue(result.response.getContentAsString(), CalendarEvent)
        with(responseEvent) {
            id == 1
            name == 'Sample Event'
            note == 'Sample Note'
            startTime == LocalTime.of(10, 0)
            endTime == LocalTime.of(12, 0)
            date == LocalDate.of(2024, 2, 17)
            meta == 'Sample Meta'
        }
    }

    def 'get calendar item by id not found'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.get("/time-block/$ID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        result
        result.response.status == 404
        1 * simpleTimeBlockService.getCalendarEventById(ID) >> { throw new CalendarEventNotFoundException(ID, "Calendar event not found") }
        0 * _

        and:
        def errorResponse = objectMapper.readValue(result.response.getContentAsString(), ErrorResponse)
        errorResponse
        errorResponse.status == '404 Not Found'
        errorResponse.message == 'Calendar Event was not found for id 1'
        errorResponse.detail == 'Please enter a calendar event id that is valid'
        errorResponse.timestamp
    }

    def 'get calendar item by id runtime exception'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.get("/time-block/$ID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        result
        result.response.status == 500
        1 * simpleTimeBlockService.getCalendarEventById(ID) >> { throw new RuntimeException("Unexpected test exception") }
        0 * _

        and:
        def errorResponse = objectMapper.readValue(result.response.getContentAsString(), ErrorResponse)
        errorResponse
        errorResponse.status == '500 Internal Server Error'
        errorResponse.message == 'Unexpected test exception'
        errorResponse.timestamp
    }

    def 'get calendar events - all events success'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.get("/time-block")
                    .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        result
        result.response.status == 200
        1 * simpleTimeBlockService.getAllCalendarEvents() >> List.of(CALENDAR_EVENT)
        0 * _

        and:
        List<CalendarEvent> responseEventList= objectMapper.readValue(result.response.getContentAsString(), List<ErrorResponse>)
        with(responseEventList.get(0)) {
            id == 1
            name == 'Sample Event'
            note == 'Sample Note'
            startTime == '10:00:00'
            endTime == '12:00:00'
            date == '2024-02-17'
            meta == 'Sample Meta'
        }
    }

    def 'get calendar events - all events runtime exception'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.get("/time-block")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        result
        result.response.status == 500
        1 * simpleTimeBlockService.getAllCalendarEvents() >> { throw new RuntimeException("Unexpected test exception") }
        0 * _

        and:
        def errorResponse = objectMapper.readValue(result.response.getContentAsString(), ErrorResponse)
        errorResponse
        errorResponse.status == '500 Internal Server Error'
        errorResponse.message == 'Unexpected test exception'
        errorResponse.timestamp
    }

    def 'get calendar events - on day success'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.get("/time-block")
                        .param('date', VALID_DATE_STRING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        result
        result.response.status == 200
        1 * simpleTimeBlockService.getCalendarEventsOnDay(VALID_DATE_LOCAL) >> List.of(CALENDAR_EVENT)
        0 * _

        and:
        List<CalendarEvent> responseEventList= objectMapper.readValue(result.response.getContentAsString(), List<ErrorResponse>)
        with(responseEventList.get(0)) {
            id == 1
            name == 'Sample Event'
            note == 'Sample Note'
            startTime == '10:00:00'
            endTime == '12:00:00'
            date == '2024-02-17'
            meta == 'Sample Meta'
        }
    }

    def 'get calendar events - on day invalid date format'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.get("/time-block")
                        .param('date', '2024-2-24')
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        result
        result.response.status == 400
        0 * _

        and:
        def errorResponse = objectMapper.readValue(result.response.getContentAsString(), ErrorResponse)
        errorResponse
        errorResponse.status == '400 Bad Request'
        errorResponse.message == 'Parameter date failed to be parsed, please check format'
        errorResponse.timestamp
    }

    def 'get calendar events - on day no calendar events on day'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.get("/time-block")
                        .param('date', VALID_DATE_STRING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        result
        result.response.status == 404
        1 * simpleTimeBlockService.getCalendarEventsOnDay(VALID_DATE_LOCAL) >> { throw new NoCalendarEventOnDayException(VALID_DATE_LOCAL, "No calendar events were found on date " + VALID_DATE_LOCAL) }
        0 * _

        and:
        def errorResponse = objectMapper.readValue(result.response.getContentAsString(), ErrorResponse)
        errorResponse
        errorResponse.status == '404 Not Found'
        errorResponse.message == "No calendar events on $VALID_DATE_STRING"
        errorResponse.timestamp
    }
}
