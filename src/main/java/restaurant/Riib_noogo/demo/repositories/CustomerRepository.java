package restaurant.Riib_noogo.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.Riib_noogo.demo.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

