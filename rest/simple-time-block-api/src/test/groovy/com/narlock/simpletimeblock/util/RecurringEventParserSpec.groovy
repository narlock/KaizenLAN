package com.narlock.simpletimeblock.util

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

import static com.narlock.simpletimeblock.util.TestUtil.*

class RecurringEventParserSpec extends Specification {

    @Unroll
    def 'parse repeat days'() {
        when:
        def days = RecurringEventParser.parseRepeatDays(repeat)

        then:
        days == expectedDays

        where:
        repeat    || expectedDays
        'DAILY'   || 1
        'WEEKLY'  || 7
        'MONTHLY' || 31
    }

    @Unroll
    def 'parse repeat days failure'() {
        when:
        RecurringEventParser.parseRepeatDays(invalidRepeat)

        then:
        thrown IllegalArgumentException

        where:
        invalidRepeat << [null, 'DAY', 'WEEK', 'MONTH', 'YEAR', 'randomString']
    }

    def 'calculate end date'() {
        when:
        def endDate = RecurringEventParser.calculateEndDate(VALID_DATE_LOCAL, until)

        then:
        endDate == expectedEndDate

        where:
        until     || expectedEndDate
        'WEEK'    || LocalDate.of(2024, 2, 24)
        'MONTH'   || LocalDate.of(2024, 3, 19)
        '2MONTH'  || LocalDate.of(2024, 4, 19)
        '3MONTH'  || LocalDate.of(2024, 5, 20)
        '4MONTH'  || LocalDate.of(2024, 6, 20)
        '5MONTH'  || LocalDate.of(2024, 7, 21)
        '6MONTH'  || LocalDate.of(2024, 8, 21)
        '7MONTH'  || LocalDate.of(2024, 9, 21)
        '8MONTH'  || LocalDate.of(2024, 10, 22)
        '9MONTH'  || LocalDate.of(2024, 11, 22)
        '10MONTH' || LocalDate.of(2024, 12, 23)
        '11MONTH' || LocalDate.of(2025, 1, 23)
        '12MONTH' || LocalDate.of(2025, 2, 23)
        'YEAR'    || LocalDate.of(2025, 2, 16)
    }

    @Unroll
    def 'calculate end date failure'() {
        when:
        RecurringEventParser.calculateEndDate(VALID_DATE_LOCAL, invalidUntil)

        then:
        thrown IllegalArgumentException

        where:
        invalidUntil << ['DAY', 'DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY', 'randomString']
    }
}
