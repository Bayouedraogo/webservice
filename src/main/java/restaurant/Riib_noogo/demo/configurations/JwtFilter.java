package restaurant.Riib_noogo.demo.configurations;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private restaurant.Riib_noogo.demo.configurations.JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        System.out.println("Requête reçue : " + requestURI); // Log pour vérifier le chemin

        // Ignorer les chemins Swagger, publics et H2 Console
        if (requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/swagger-ui") ||
                requestURI.startsWith("/swagger-resources") ||
                requestURI.startsWith("/webjars") ||
                requestURI.startsWith("/api/auth/login") ||
                requestURI.startsWith("/api/users/register") ||
                requestURI.startsWith("/h2-console")) {

            System.out.println("Chemin Swagger, public ou H2 Console détecté, filtre ignoré : " + requestURI); // Log pour vérifier l'exclusion
            filterChain.doFilter(request, response);
            return;
        }

        // Extraire et valider le token JWT pour les autres chemins
        final String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Extraire le token (enlever "Bearer ")
            try {
                username = jwtUtil.extractUsername(token); // Extraire le nom d'utilisateur du token
            } catch (Exception e) {
                System.out.println("Token invalide : " + e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails)) {
                // Créer une authentification et l'ajouter au contexte de sécurité
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token JWT invalide");
                return;
            }
        }

// Continuer la chaîne de filtres
        filterChain.doFilter(request, response);
    }
}