package com.treeleaf.blog.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treeleaf.blog.common.APIResponse;
import com.treeleaf.blog.common.APIRoutes;
import com.treeleaf.blog.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final String APPLICATION_JSON = "application/json";

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(APIRoutes.LOGIN);
    }

    /**
     * Validate the input
     *
     * @param request
     */
    private void validateInput(HttpServletRequest request){
        System.out.println(request.getParameter("email"));
        System.out.println(request.getParameter("password"));
        if(!StringUtils.hasText(request.getParameter("email"))){
            throw new IllegalArgumentException("Username is required");
        }else if(!StringUtils.hasText(request.getParameter("password"))){
            throw  new IllegalArgumentException("Password is required");
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        validateInput(request);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( request.getParameter("email"), request.getParameter("password"));

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setContentType(APPLICATION_JSON);

        Map<String, String> res = new HashMap<>();
        res.put("status", "false");
        res.put("message", "Invalid credentials");
        new ObjectMapper().writeValue(response.getOutputStream(), res);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        //super.successfulAuthentication(request, response, chain, authResult);
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String accessToken = JWT.create()
                                .withSubject(user.getUsername())
                                .withExpiresAt(new Date(System.currentTimeMillis() + 60 *60*1000))
                                .withIssuer("BLOG POST")
                                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                                .sign(algorithm);


        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage("Welcome to ...");
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(tokens);

        response.setContentType(APPLICATION_JSON);
        new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
    }
}
