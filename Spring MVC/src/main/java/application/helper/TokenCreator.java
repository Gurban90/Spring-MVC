/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package application.helper;

import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gerben
 */

/*
@Component
public class TokenCreator {

    private static String user;
    private static Key key;
    private static String jwt;

    public String jwtCreator(int userID) {
        key = MacProvider.generateKey();
        this.user = String.valueOf(userID);

        jwt
                = Jwts.builder().setIssuer("KaasApplicatie")
                        .setSubject(this.user)
                        .signWith(SignatureAlgorithm.HS256, key)
                        .compact();

        return jwt;
    }

    public boolean checkJWT() {
        boolean check = false;
        try {
            if (Jwts.parser().setSigningKey(this.key).parseClaimsJws(this.jwt).getBody().getSubject().equals(this.user)) {
                check = true;
            } else {
                System.out.println("Token is nog accepted");
            }
        } catch (Exception e) {
            System.out.println("No Token found");
        }
        return check;
    }

}
/*
    public static void main(String[] args) {
        Test tester = new Test();
        tester.jwts();
        tester.checkJWT();
        tester.getInfo();

    }
 */
