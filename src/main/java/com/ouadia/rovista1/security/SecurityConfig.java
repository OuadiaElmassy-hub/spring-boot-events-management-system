package com.ouadia.rovista1.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity   // active @PreAuthorize sur les controllers
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationProvider authenticationProvider,
                                           JwtFilter jwtFilter,
                                           LoginRateLimitFilter loginRateLimitFilter) throws Exception {
        http
                .sessionManagement(
                        sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults()) // active CORS
                //.httpBasic(Customizer.withDefaults()) toujour envouyer header Authorisation contient username et password
                // alors on vas utiliser jwt
                //.formLogin(Customizer.withDefaults())
                .headers(h -> h.frameOptions(f -> f.disable()))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            String msg = authException.getMessage() != null ? authException.getMessage() : "Non authentifié";
                            response.getWriter().write("{\"message\":\"" + msg + "\"}");
                        }))
                .authorizeHttpRequests(auth -> auth
                        // Routes publiques
                        .requestMatchers("/api/auth/register/client",
                                "/api/auth/register/organisateur",
                                "/api/auth/login").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/uploads/**").permitAll()

                        // Routes protégées par rôle
                        .requestMatchers("/api/chat/**").permitAll()
                        .requestMatchers("/api/auth/me").authenticated() // pas permitAll
                        // Routes par rôle
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/organisateur/**").hasRole("ORGANISATEUR")
                        .requestMatchers("/api/client/**").hasRole("CLIENT")

                        // Tout le reste → authentifié
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)

                // Placer le filtre JWT AVANT le filtre username/password de Spring
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(loginRateLimitFilter, JwtFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:*"));  // autorise ton frontend
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(
                List.of(
                        "Authorization",
                        "Content-Type"
                )
        );
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    //Règle : Tu dois toujours avoir UserDetailsService.
    //AuthenticationProvider = optionnel si tu fais que du JWT stateless sans page /login.
    //Mais si tu as un endpoint /auth/login → il te faut AuthenticationProvider qui utilise
    // ton UserDetailsService.Sans AuthenticationProvider, Spring sait pas comment comparer
    // le password reçu au login avec le hash en DB.

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // <- injecté
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // AuthenticationManager n’est pas lié à InMemory/JDBC.
    // Il est lié au fait que tu as un endpoint /auth/login.
    // Controller → AuthenticationManager.authenticate(token)
    //→ DaoAuthenticationProvider
    //→ UserDetailsService.loadUserByUsername()
    //→ PasswordEncoder.matches()

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .formLogin(form -> form
//                        .loginPage("/login")           // your custom login page
//                        .defaultSuccessUrl("/home")
//                        .failureUrl("/login?error")
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login")
//                        .permitAll()
//                ).cors(Customizer.withDefaults())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/public/**", "/login", "/register").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                );
//        return http.build();
//    }
}
