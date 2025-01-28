package com.springJourney.Week5Practice.Services;
import com.springJourney.Week5Practice.Entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Service
public class JwtServices {

    Logger log = LoggerFactory.getLogger(JwtServices.class);


    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UserEntity userEntity){
        log.info("Attempting generateAccessToken method");
        String key=Jwts.builder()
                .subject(userEntity.getUserid().toString())
                .claim("email",userEntity.getEmail())
                .issuedAt(new Date())
                // testing purpose = expired access token after every 60 sec
                .expiration(new Date(System.currentTimeMillis()+1000*60*5))
                .signWith(getSecretKey())
                .compact();
        log.info("Successfully generate access token with id : {} and email : {}", userEntity.getUserid(), userEntity.getEmail());
        return key;

    }
    public String generateRefreshToken(UserEntity userEntity){
        log.info("Attempting generateRefreshToken method");
        String key=Jwts.builder()
                .subject(userEntity.getUserid().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*600*600))
                .signWith(getSecretKey())
                .compact();
        log.info("Successfully generate refresh token with id : {} ", userEntity.getUserid());
        return key;

    }

//    public String generateJwtToken(UserEntity userEntity) {
    // Code Can't  work Properly for DB Active SessionEntity
//        // Set token expiration time (15 minutes from now)
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime expirationTime = now.plusMinutes(15);
//
//        // Create JWT token with expiration
//        String token = Jwts.builder()
//                .setSubject(userEntity.getEmail())
//                .setIssuedAt(new Date())
//                .setExpiration(Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant())) // Set expiration
//                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
//                .compact();
//
//        // Save token with expiration in the session entity
//        SessionEntity session = new SessionEntity();
//        session.setUserId(userEntity.getUserid());
//        session.setToken(token);
//        session.setCreatedAt(now);
//        session.setExpiresAt(expirationTime);
//
//        sessionRepository.save(session); // Save the session with expiration date
//
//        return token;
//    }


    public Long getUserIdFromToken(String token){
        log.info("Attempting getUserIdFromToken method");
        Claims claims=Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        log.info("Successfully retrieve Id From Token ");
        return Long.valueOf(claims.getSubject());
    }
}
