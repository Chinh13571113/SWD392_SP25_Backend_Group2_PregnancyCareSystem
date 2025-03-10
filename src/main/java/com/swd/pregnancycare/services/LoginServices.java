package com.swd.pregnancycare.services;

import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.repository.RoleRepo;
import com.swd.pregnancycare.repository.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Optional;

@Service
public class LoginServices  {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;
    @Value("${jwt.key}")
    private String keyjwt;
    public String login(String email, String password){
        String token="";
        Optional<UserEntity> user = userRepo.findByEmail(email);
        if(user.isPresent()){
            UserEntity userEntity = user.get();
            if(passwordEncoder.matches(password, userEntity.getPassword())){
                SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyjwt));
                String jws = Jwts.builder().subject(userEntity.getRole().getName()).signWith(key).compact();
                token = jws;
            }
        }
        return token;
    }

}
