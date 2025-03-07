package restaurant.Riib_noogo.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.Riib_noogo.demo.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

