package com.treeleaf.blog.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getServletPath().equals("api/v1/login")){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }else{
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
               try {
                   String token = authorizationHeader.substring("Bearer ".length());

                   Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

                   JWTVerifier verifier = JWT.require(algorithm)
                           .withIssuer("BLOG POST")
                           .build();

                   DecodedJWT decodedJWT = verifier.verify(token);

                   String username = decodedJWT.getSubject();
                   String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                   Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                   Arrays.stream(roles).forEach(role -> {
                       authorities.add(new SimpleGrantedAuthority(role));
                   });

                   UsernamePasswordAuthenticationToken authenticationToken =
                           new UsernamePasswordAuthenticationToken(username, null, authorities);
                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                   filterChain.doFilter(httpServletRequest, httpServletResponse);
               }catch (JWTVerificationException e){
                   Map<String, String> tokens = new HashMap<>();
                   tokens.put("error", "Invalid token");

                   httpServletResponse.setContentType("application/json");
                   new ObjectMapper().writeValue(httpServletResponse.getOutputStream(), tokens);
               }catch(Exception e){
                   Map<String, String> tokens = new HashMap<>();
                   tokens.put("error", "Invalid token");

                   httpServletResponse.setContentType("application/json");
                   new ObjectMapper().writeValue(httpServletResponse.getOutputStream(), tokens);
               }
            }else{
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }
    }
}
