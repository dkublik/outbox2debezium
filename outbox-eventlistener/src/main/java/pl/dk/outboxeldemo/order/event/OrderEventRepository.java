package pl.dk.outboxeldemo.order.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.dk.outboxeldemo.order.event.OrderPlacedStorableEvent.Status;

import java.util.List;
import java.util.UUID;

public interface OrderEventRepository extends JpaRepository<OrderPlacedStorableEvent, UUID> {

    List<OrderPlacedStorableEvent> findByStatusOrderByCreated(Status status, Pageable pageable);

    List<OrderPlacedStorableEvent> findByOrderId(UUID orderId);
}
