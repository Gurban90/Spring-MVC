/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.security;

import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Gerben
 */
public class SCryptPasswordEncoder implements PasswordEncoder{

    @Override
    public String encode(CharSequence cs) {
        return SCryptUtil.scrypt(cs.toString(), 16, 16, 16);
    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        return SCryptUtil.check(cs.toString(), string);
    }
    
}
