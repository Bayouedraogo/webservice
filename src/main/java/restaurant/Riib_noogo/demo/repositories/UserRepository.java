package restaurant.Riib_noogo.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.Riib_noogo.demo.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    // Méthodes personnalisées si nécessaire
}