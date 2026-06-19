package com.pfe.backend.security;

import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class LoginRateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        if(uri.equals("/api/auth/login")
                || uri.equals("/api/auth/register/client")
                || uri.equals("/api/auth/register/organisateur")) {

            String key = request.getRemoteAddr() + ":" + request.getParameter("username");

            Bucket bucket = rateLimitService.resolveBucket(key);

            if(!bucket.tryConsume(1)) {

                response.setStatus(429);
                response.setContentType("application/json");

                response.getWriter().write("""
                    {
                      "message":"Trop de tentatives. Réessayez dans 1 minute."
                    }
                """);

                return;
            }
        }
        filterChain.doFilter(request,response);
    }
}