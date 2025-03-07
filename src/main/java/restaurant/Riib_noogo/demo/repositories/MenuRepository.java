package restaurant.Riib_noogo.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.Riib_noogo.demo.models.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}

