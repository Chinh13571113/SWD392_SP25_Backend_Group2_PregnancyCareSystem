package com.swd.pregrancycare.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.swing.*;
import javax.xml.crypto.KeySelector;

@Component
public class JwtHelper {
    @Value("${jwt.key")
    private String jwtKey;
    public boolean decrypt(String token){
        boolean result = false;
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode((jwtKey)));
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            result= true;
        }catch(Exception e){
            System.out.println("Decription: "+e.getMessage());
        }
        return result;
    }

    public String getDataToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKey));
        String role="";
        try{
            role = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
        }catch (Exception e){
            System.out.println("Error parsing: "+e.getMessage());
        }
        return role;
    }
}
