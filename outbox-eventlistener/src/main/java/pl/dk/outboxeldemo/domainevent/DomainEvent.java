package pl.dk.outboxeldemo.domainevent;

import org.springframework.context.ApplicationEvent;

import java.time.Instant;

public abstract class DomainEvent extends ApplicationEvent {

    protected DomainEvent(Object source) {
        super(source);
    }

    public Instant published() {
        return Instant.ofEpochMilli(getTimestamp());
    }
}
