package pl.dk.outboxeldemo.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.dk.outboxeldemo.domainevent.DomainEvents;
import pl.dk.outboxeldemo.order.event.OrderPlacedEvent;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "orders")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class Order {

    @Id
    private UUID id;

    private String description;

    @PrePersist
    public void prePersist() {
        DomainEvents.publish(new OrderPlacedEvent(id));
    }
}
