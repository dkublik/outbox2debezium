package pl.dk.outboxeldemo.order.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

@Service
class EventsPersister {

    private final OrderEventRepository orderEventRepository;

    EventsPersister(OrderEventRepository orderEventRepository) {
        this.orderEventRepository = orderEventRepository;
    }

    @TransactionalEventListener(classes = OrderPlacedEvent.class, phase = BEFORE_COMMIT)
    void onScheduleCreated(OrderPlacedEvent event) {
        orderEventRepository.save(new OrderPlacedStorableEvent(event.getOrderId()));
    }
}
