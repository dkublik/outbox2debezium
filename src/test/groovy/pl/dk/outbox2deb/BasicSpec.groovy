package pl.dk.outbox2deb

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [Outbox2DebeziumApp])
@SpringBootTest
class BasicSpec extends Specification {

    def "should not fail"() {
        when:
        def a = 1

        then:
        noExceptionThrown()
    }
}
