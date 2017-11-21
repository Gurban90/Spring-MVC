/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.security;

import application.model.repository.AccountRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static application.security.SecurityConstants.HEADER_STRING;
import static application.security.SecurityConstants.SECRET;
import static application.security.SecurityConstants.TOKEN_PREFIX;
import io.jsonwebtoken.Claims;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;
    
  
    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
        this.jwtUtil = new JwtUtil();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        System.out.println("DoFilterInternal");
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            System.out.println("Header is null");
            chain.doFilter(req, res);
            return;
        }
        System.out.println("Header is not null");
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        System.out.println("getAuthentication");
        if (token != null) {
            System.out.println("Jwt access token found");
            String user = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("User"));///////////////////////////////////////

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, grantedAuths);
            }
            return null;
        }
        return null;
    }
}

