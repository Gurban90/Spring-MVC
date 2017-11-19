/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.security;

import application.model.Account;
import application.service.AccountService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider{
    
    @Autowired
    AccountService accountService;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String name = a.getName();
        String password = a.getCredentials().toString();
         
        if(accountService.matchUsernameAndPassword(name, password)){
            Account account = accountService.getAccount(name);
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority(account.getTheRole()));
            return new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
        }
        else{
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> type) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(type));
    }
    
}
