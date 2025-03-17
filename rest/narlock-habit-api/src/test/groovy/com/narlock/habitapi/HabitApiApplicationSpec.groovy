package com.narlock.habitapi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class HabitApiApplicationSpec extends Specification {
    @Autowired
    ApplicationContext applicationContext

    def 'context loads'() {
        expect:
        applicationContext
    }
}
