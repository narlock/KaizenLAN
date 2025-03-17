package com.narlock.simpletimeblock.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.narlock.simpletimeblock.model.CalendarEvent
import com.narlock.simpletimeblock.model.response.RecurringCalendarEventsResponse
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
class CreateControllerSpec extends Specification {
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

    def 'create calendar event success'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.post("/time-block")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CALENDAR_EVENT_REQUEST)))
                .andReturn()

        then:
        result
        result.response.status == 201
        1 * simpleTimeBlockService.createCalendarEvent(CALENDAR_EVENT_REQUEST) >> CALENDAR_EVENT
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

    def 'create recurring calendar events success'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.post("/time-block/recurring")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CREATE_RECURRING_CALENDAR_EVENTS_REQUEST)))
                .andReturn()

        then:
        result
        result.response.status == 201
        1 * simpleTimeBlockService.createRecurringCalendarEvents(CREATE_RECURRING_CALENDAR_EVENTS_REQUEST) >> RECURRING_CALENDAR_EVENTS_RESPONSE
        0 * _

        and:
        def responseEvent = objectMapper.readValue(result.response.getContentAsString(), RecurringCalendarEventsResponse)
        responseEvent.size == 1
        responseEvent.startDate == LocalDate.of(2024, 2, 17)
        responseEvent.endDate == LocalDate.of(2024, 2, 17)
        responseEvent.repeat == 'DAILY'
    }
}
