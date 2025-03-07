package restaurant.Riib_noogo.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurant.Riib_noogo.demo.configurations.JwtUtil;
import restaurant.Riib_noogo.demo.models.AuthResponse;

import restaurant.Riib_noogo.demo.models.LoginRequest;
import restaurant.Riib_noogo.demo.models.User;
import restaurant.Riib_noogo.demo.repositories.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Authentification de l'utilisateur
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Récupération de l'utilisateur depuis la base de données
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Génération du token JWT
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        // Retourne le token dans la réponse
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
