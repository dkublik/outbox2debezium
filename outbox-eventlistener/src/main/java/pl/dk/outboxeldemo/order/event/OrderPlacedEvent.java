package pl.dk.outboxeldemo.order.event;

import pl.dk.outboxeldemo.domainevent.DomainEvent;

import java.util.UUID;

public class OrderPlacedEvent extends DomainEvent {

    public OrderPlacedEvent(UUID orderId) {
        super(orderId);
    }

    public UUID getOrderId() {
        return (UUID) getSource();
    }
}
