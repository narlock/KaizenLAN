package com.narlock.simpletimeblock.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
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
class UpdateControllerSpec extends Specification {
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

    def 'update fields on calendar event'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.patch("/time-block/$ID")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CALENDAR_EVENT_REQUEST)))
                .andReturn()

        then:
        result
        result.response.status == 200
        1 * simpleTimeBlockService.updateFieldsOnCalendarEvent(ID, CALENDAR_EVENT_REQUEST) >> CALENDAR_EVENT
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

    def 'overwrite calendar event'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.put("/time-block/$ID")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CALENDAR_EVENT_REQUEST)))
                .andReturn()

        then:
        result
        result.response.status == 200
        1 * simpleTimeBlockService.overwriteCalendarEvent(ID, CALENDAR_EVENT_REQUEST) >> CALENDAR_EVENT
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
}
