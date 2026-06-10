package com.ouadia.rovista1.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
//@Order(Ordered.HIGHEST_PRECEDENCE + 10)
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    // private final ApplicationContext context;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token;
        String username;

        // 1. Lire le header Authorization
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraire le token (enlever "Bearer ")
        token = authHeader.substring(7);
        System.out.println(">>> URL: " + request.getRequestURI());
        System.out.println(">>> Auth Header: " + request.getHeader("Authorization"));
        // 3. Extraire l'email depuis le token
        try {
            username = jwtService.extractUsername(token);
            System.out.println(">>> Username: " + username);
        }
        catch (JwtException e){
            filterChain.doFilter(request,response);
            return;
        }

        // 4. Si username extrait et pas encore authentifié dans le contexte
        if(username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 5. Charger l'utilisateur depuis la BDD
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);
            System.out.println(">>> User found: " + userDetails.getUsername());

            boolean valid = jwtService.validateToken(token, userDetails);
            System.out.println(">>> Token valid: " + valid);
            // si il y a une cyclique redendance :
            // UserDetails userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(username);

            // 6. Valider le token
            if (jwtService.validateToken(token, userDetails)) {

                // 7. Créer l'objet d'authentification
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                System.out.println(">>> Authorities: " + userDetails.getAuthorities().toString());

                // 8. Injecter dans le SecurityContext
                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
        }





        // Token invalide ou expiré → on laisse passer sans authentifier
        // Spring renverra 401 automatiquement sur les routes protégées
        filterChain.doFilter(request, response);

    }
}
