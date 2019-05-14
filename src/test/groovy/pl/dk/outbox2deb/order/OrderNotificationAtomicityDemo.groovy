package pl.dk.outbox2deb.order

import org.springframework.beans.factory.annotation.Autowired
import pl.dk.outbox2deb.email.Email
import pl.dk.outbox2deb.email.EmailSender
import pl.dk.outbox2deb.test.BaseSpecification

import static java.util.UUID.randomUUID

class OrderNotificationAtomicityDemo extends BaseSpecification {

    @Autowired
    OrderRepository orderRepository

    @Autowired
    EmailSender emailSender

    static final UUID ORDER_ID = randomUUID()

    def "should persist order and send email"() {
        given:
        Order order = new Order(ORDER_ID, 'example order')

        when:
        orderRepository.save(order)
        emailSender.send(new Email("order $ORDER_ID received"))

        then:
        orderisSaved(ORDER_ID)
        emailIsSent(ORDER_ID)
    }

    def "will not be atomic - email failed to send"() {
        given:
        Order order = new Order(ORDER_ID, 'example order')

        when:
        logException {
            orderRepository.save(order)
            throw new RuntimeException()
            emailSender.send(new Email("order $ORDER_ID received"))
        }

        then:
        orderisSaved(ORDER_ID)
        !emailIsSent(ORDER_ID)
    }

    def "will not be atomic - order failed to save"() {
        given:
        Order order = new Order(ORDER_ID, 'example order')

        when:
        logException {
            doInTx {
                orderRepository.save(order)
                emailSender.send(new Email("order $ORDER_ID received"))
                throw new RuntimeException()
            }
        }

        then:
        emailIsSent(ORDER_ID)
        !orderisSaved(ORDER_ID)
    }

    private boolean orderisSaved(UUID orderId) {
        return orderRepository.findById(orderId).isPresent()
    }

    private boolean emailIsSent(UUID orderId) {
        emailSender.sentEmails.any({it.isForOrder(orderId)})
    }
}
