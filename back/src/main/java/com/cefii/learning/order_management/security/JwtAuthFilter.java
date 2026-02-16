package com.cefii.learning.order_management.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean; // Classe de base pour les filtres

import java.io.IOException;

@Component // Fait de cette classe un bean Spring
public class JwtAuthFilter extends GenericFilterBean { // Étend GenericFilterBean pour faciliter l'intégration
    @Autowired private JwtUtil jwtUtil; // Injecte l'utilitaire JWT
    @Autowired private CustomUserDetailsService userDetailsService; // Injecte le service de détails utilisateur

    @Override // Méthode principale du filtre
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        final String authHeader = request.getHeader("Authorization"); // Récupère le header Authorization
        final String jwt;
        final String username;


        String path = request.getServletPath();
        if (path.equals("/api/auth/login") || path.equals("/api/users/register")) {
            chain.doFilter(req, res);
            return;
        }



        jwt = authHeader.substring(7); // Extrait le token JWT (après "Bearer ")
        username = jwtUtil.extractUsername(jwt); // Extrait le nom d'utilisateur du token

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); // Charge les détails de l'utilisateur

            if (jwtUtil.isTokenValid(jwt, userDetails)) { // Valide le token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities() // Assigne les rôles/autorisations
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken); // Met à jour le contexte de sécurité
            }
        }
        chain.doFilter(request, res); // Passe la requête au prochain filtre dans la chaîne
    }
}