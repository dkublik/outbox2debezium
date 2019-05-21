package pl.dk.outboxdemo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.dk.outboxdemo.order.OrderEventRepository;

import javax.transaction.Transactional;

import static pl.dk.outboxdemo.order.OrderPlacedEvent.Status.NEW;

@Service
class EmailSendingJob {

    static final int BATCH_SIZE = 10;

    @Autowired
    private OrderEventRepository orderEventRepository;

    @Autowired
    private EmailSender emailSender;

    @Scheduled(fixedDelay = 100)
    @Transactional
    public void sendEmailsOnEvents() {
        orderEventRepository.findByStatusOrderByCreated(NEW, PageRequest.of(0, BATCH_SIZE)).forEach(event -> {
            emailSender.send(new Email("received order: " + event.getOrderId()));
            event.processed(); // At least once guarantee
        });
    }
}
