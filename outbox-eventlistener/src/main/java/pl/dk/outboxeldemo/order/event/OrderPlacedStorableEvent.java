package pl.dk.outboxeldemo.order.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PRIVATE;
import static pl.dk.outboxeldemo.order.event.OrderPlacedStorableEvent.Status.NEW;
import static pl.dk.outboxeldemo.order.event.OrderPlacedStorableEvent.Status.PROCESSED;

@Entity
@Table(name = "order_events")
@Getter
@NoArgsConstructor(access = PRIVATE)
public class OrderPlacedStorableEvent {

    public enum Status {
        NEW, PROCESSED
    }

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant created = now();

    @Id
    private UUID id = randomUUID();

    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private Status status = NEW;

    public OrderPlacedStorableEvent(UUID orderId) {
        this.orderId = orderId;
    }

    public void processed() {
        status = PROCESSED;
    }
}
