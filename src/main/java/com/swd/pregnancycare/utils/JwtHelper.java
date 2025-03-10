package com.swd.pregnancycare.utils;

import com.swd.pregnancycare.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.List;

@Component
public class JwtHelper {
    @Value("${jwt.key}")
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
        String role = "";

        try {
            // Parse the token to get claims
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build().parseSignedClaims(token).getPayload();

            // Extract the role or authorities from the claims
            List<String> roles = claims.get("roles", List.class);
            System.out.println(roles);// Assuming 'roles' claim is a list of strings
            if (roles != null && !roles.isEmpty()) {
                role = roles.getFirst(); // Assuming the role you need is the first one in the list
            }
        } catch (Exception e) {
            System.out.println("Error parsing: " + e.getMessage());
        }

        return role;
    }
}
