package pl.dk.outboxdemo.email;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.dk.outboxdemo.order.OrderEventRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static pl.dk.outboxdemo.order.OrderPlacedEvent.Status.NEW;

@Service
@Getter
public class EmailSender {

    @Autowired
    private OrderEventRepository orderEventRepository;

    static final int BATCH_SIZE = 10;

    private final List<Email> sentEmails = new ArrayList<>();


    @Scheduled(fixedDelay = 100)
    @Transactional
    public void sendEmailsOnEvents() {
        orderEventRepository.findByStatusOrderByCreated(NEW, PageRequest.of(0, BATCH_SIZE)).forEach(event -> {
            sentEmails.add(new Email("received order: " + event.getOrderId()));
            event.processed();
        });
    }

    public void clear() {
        sentEmails.clear();
    }
}
