package pl.dk.outboxeldemo.order

import org.springframework.beans.factory.annotation.Autowired
import pl.dk.outboxeldemo.email.EmailSender
import pl.dk.outboxeldemo.order.event.OrderEventRepository
import pl.dk.outboxeldemo.test.BaseSpecification

import static java.util.UUID.randomUUID
import static java.util.concurrent.TimeUnit.SECONDS
import static org.awaitility.Awaitility.await

class OutboxElAtomicityDemo extends BaseSpecification {

    @Autowired
    OrderRepository orderRepository

    @Autowired
    OrderEventRepository orderEventRepository

    @Autowired
    EmailSender emailSender

    static final UUID ORDER_ID = randomUUID()

    def "should persist order and send email"() {
        given:
        Order order = new Order(ORDER_ID, 'example order')

        when:
        doInTx {
            orderRepository.save(order)
        }

        then:
        orderIsSaved(ORDER_ID)
        eventIsSaved(ORDER_ID)
        await().atMost(5, SECONDS).until {
            emailIsSent(ORDER_ID)
        }
    }

    def "should persist nothing when exception occurs"() {
        given:
        Order order = new Order(ORDER_ID, 'example order')

        when:
        logException {
            doInTx {
                orderRepository.save(order)
                throw new RuntimeException()
            }
        }

        then:
        !orderIsSaved(ORDER_ID)
        !eventIsSaved(ORDER_ID)
    }

    private boolean orderIsSaved(UUID orderId) {
        return orderRepository.findById(orderId).isPresent()
    }

    private boolean eventIsSaved(UUID orderId) {
        orderEventRepository.findByOrderId(orderId).size() > 0
    }

    private boolean emailIsSent(UUID orderId) {
        emailSender.sentEmails.any({it.isForOrder(orderId)})
    }
}
