package pl.dk.outboxdemo.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.dk.outboxdemo.order.OrderPlacedEvent.Status;

import java.util.List;
import java.util.UUID;

public interface OrderEventRepository extends JpaRepository<OrderPlacedEvent, UUID> {

    List<OrderPlacedEvent> findByStatusOrderByCreated(Status status, Pageable pageable);

    List<OrderPlacedEvent> findByOrderId(UUID orderId);
}
