package com.cefii.learning.order_management.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean; 

import java.io.IOException;
import java.util.List;

@Component
@Profile("!test")
public class JwtAuthFilter extends GenericFilterBean { 
    @Autowired private JwtUtil jwtUtil;
    @Autowired private CustomUserDetailsService userDetailsService;
@Override
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;

    String path = request.getServletPath();

    if (path.equals("/api/auth/login") ||
        path.equals("/api/users/register") ||
        path.startsWith("/swagger-ui") ||
        path.startsWith("/v3/api-docs")) {

        chain.doFilter(req, res);
        return;
    }

    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        chain.doFilter(req, res);
        return;
    }

    String jwt = authHeader.substring(7);
    String username = jwtUtil.extractUsername(jwt);

    if (username != null &&
        SecurityContextHolder.getContext().getAuthentication() == null) {

        UserDetails userDetails =
            this.userDetailsService.loadUserByUsername(username);

        if (jwtUtil.isTokenValid(jwt, userDetails)) {

            String role = jwtUtil.extractRole(jwt);
            List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + role)
            );

            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    authorities
                );

            authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    chain.doFilter(req, res);
}
}