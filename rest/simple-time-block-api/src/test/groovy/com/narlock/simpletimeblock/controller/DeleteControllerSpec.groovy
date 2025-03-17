package com.narlock.simpletimeblock.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.narlock.simpletimeblock.service.SimpleTimeBlockService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import static com.narlock.simpletimeblock.util.TestUtil.*

@SpringBootTest
@AutoConfigureMockMvc
class DeleteControllerSpec extends Specification {
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

    def 'delete calendar event id success'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/time-block/$ID")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn()

        then:
        result
        result.response.status == 204
        1 * simpleTimeBlockService.deleteCalendarEventById(ID)
        0 * _
    }

    def 'delete calendar events on a date success'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/time-block")
                        .param('date', VALID_DATE_STRING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        result
        result.response.status == 204
        1 * simpleTimeBlockService.deleteCalendarEventByDate(VALID_DATE_LOCAL)
        0 * _
    }
}
