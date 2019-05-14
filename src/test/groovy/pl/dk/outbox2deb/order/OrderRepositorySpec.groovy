package pl.dk.outbox2deb.order

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import pl.dk.outbox2deb.Outbox2DebeziumApp
import spock.lang.Specification

import static java.util.UUID.randomUUID

@ContextConfiguration(classes = [Outbox2DebeziumApp])
@SpringBootTest
@ActiveProfiles('test')
class OrderRepositorySpec extends Specification {

    @Autowired
    OrderRepository orderRepository

    static final UUID ORDER_ID = randomUUID()

    def "should persist order"() {
        Order order = new Order(ORDER_ID, 'example order')

        when:
        orderRepository.save(order)

        then:
        orderRepository.findById(ORDER_ID).get().description == 'example order'
    }
}
