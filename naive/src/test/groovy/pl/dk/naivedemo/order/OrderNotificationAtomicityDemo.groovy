package pl.dk.naivedemo.order

import org.springframework.beans.factory.annotation.Autowired
import pl.dk.naivedemo.email.Email
import pl.dk.naivedemo.email.EmailSender
import pl.dk.naivedemo.test.BaseSpecification

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
        logException {
            doInTx {
                orderRepository.save(order)
                emailSender.send(new Email("order $ORDER_ID received"))
                throw new RuntimeException()
            }
        }

        then:
        !orderIsSaved(ORDER_ID)
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
        orderIsSaved(ORDER_ID)
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
        !orderIsSaved(ORDER_ID)
    }

    private boolean orderIsSaved(UUID orderId) {
        return orderRepository.findById(orderId).isPresent()
    }

    private boolean emailIsSent(UUID orderId) {
        return emailSender.sentEmails.any({it.isForOrder(orderId)})
    }
}
