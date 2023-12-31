package com.spring.authentication.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                String requestToken = request.getHeader("Authorization");

                String username = null;
                String token = null;
        
                if (requestToken != null && requestToken.startsWith("Bearer")) {
                    token = requestToken.substring(7);
                    try {
                        username = this.jwtHelper.getUsernameFromToken(token);
                    } catch (IllegalArgumentException e) {
                        // TODO: handle exception
                        System.out.println("JWT token error!");
                    } catch (ExpiredJwtException e) {
                        // TODO: handle exception
                        System.out.println("JWT token has expired!");
                    } catch (MalformedJwtException e) {
                        // TODO: handle exception
                        System.out.println("JWT has malformed!");
                    }
                }
        
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (this.jwtHelper.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
        
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    } else {
                        System.out.println("Invalid jwt token!");
                    }
                } else {
                    System.out.println("username is null");
                }
        
                filterChain.doFilter(request, response);
    }
    
}
