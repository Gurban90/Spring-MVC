/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.crypto.KeyGenerator;


public class JwtUtil {
    private static final Key key = generateKey();
    
    public String generateJwt(String username){
        generateKey();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, 2);
        Date expirationDate = calendar.getTime();
        
        if(key != null){
            String compactJws = Jwts.builder()
                .setSubject(username)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

            if(verifyJwt(compactJws)){
                return compactJws;
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }
    }
    
    public boolean verifyJwt(String compactJws){
        try {
            if(key == null){
                return false;
            }
            Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws);
            return true;
        } catch (SignatureException e) {
            //TODO: log exception
            return false;
        }
    }
    
    public String getUsername(String jws){
        Claims claims = getClaims(jws);
        if(claims == null){
            return null;
        }
        return claims.getSubject();
    }
    
    private Claims getClaims(String jws){
        if(key == null){
            return null;
        }
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jws).getBody();
    }
    
    private static Key generateKey(){
        try {
            return KeyGenerator.getInstance("HmacSHA256").generateKey();
        }
        catch (NoSuchAlgorithmException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
}
