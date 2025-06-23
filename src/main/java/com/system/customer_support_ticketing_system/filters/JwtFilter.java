package com.system.customer_support_ticketing_system.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.customer_support_ticketing_system.auth.JwtService;
import com.system.customer_support_ticketing_system.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        var token = authHeader.replace("Bearer ","");
        if(!jwtService.validateToken(token)){
            filterChain.doFilter(request,response);
            return;
        }
        var role = jwtService.getRoleFromToken(token);
        var userID = jwtService.getUserIdFromToken(token);

        var user = userRepository.findById(userID).orElseThrow(() ->  new UsernameNotFoundException("User not found"));
        if (user.isDeleted()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            Map<String, Object> errorBody = Map.of(
                    "success", false,
                    "message", "User account is deleted."
            );

            new ObjectMapper().writeValue(response.getOutputStream(), errorBody);
            return;
        }


        var authentication = new UsernamePasswordAuthenticationToken(
                userID,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_"+role))
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
}
