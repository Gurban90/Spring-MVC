/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.security;

import application.model.Account;
import static application.security.SecurityConstants.EXPIRATION_TIME;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static application.security.SecurityConstants.HEADER_STRING;
import static application.security.SecurityConstants.SECRET;
import static application.security.SecurityConstants.TOKEN_PREFIX;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.util.Date;
import org.springframework.security.core.context.SecurityContextHolder;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
            HttpServletResponse res) throws AuthenticationException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("Attempting authentication on username '" + username + " password" + password);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(token);
        /*
        try {
            ObjectMapper mapper = new ObjectMapper();
            Account creds = mapper
                    .readValue(req.getInputStream(), Account.class);

            //this.role = creds.getTheRole();
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);

        }*/
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        String name = auth.getName();
        String token = Jwts.builder()
                .setIssuer("KaasApplicatie")
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setSubject(name)
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        System.out.println("Succesfull authentication");
       
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        response.sendRedirect("/main");
    }

}
