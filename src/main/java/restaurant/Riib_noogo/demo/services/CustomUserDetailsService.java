package restaurant.Riib_noogo.demo.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import restaurant.Riib_noogo.demo.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import restaurant.Riib_noogo.demo.repositories.UserRepository;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Trouver l'utilisateur par le nom d'utilisateur
        Optional<restaurant.Riib_noogo.demo.models.User> optionalUser = userRepository.findByUsername(username);

        // Vérifier si l'utilisateur est présent, sinon lancer une exception
        restaurant.Riib_noogo.demo.models.User user = optionalUser
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Assurez-vous de converter le rôle en String en utilisant la méthode getRoleName()
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())  // Ici, getRole() retourne un String, donc vous n'avez pas besoin de name()
                // <-- Erreur ici
// Utilisation de name() pour obtenir le rôle sous forme de String
                .build();

    }
}
