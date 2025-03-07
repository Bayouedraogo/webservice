package restaurant.Riib_noogo.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.Riib_noogo.demo.models.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {
}

