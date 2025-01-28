package com.springJourney.Week5Practice.Services;

import com.springJourney.Week5Practice.DTOs.LoginDTO;
import com.springJourney.Week5Practice.DTOs.LoginResponseDTO;
import com.springJourney.Week5Practice.Entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServices {
    private final AuthenticationManager authenticationManager;
    private final JwtServices jwtServices;
    private final UserService userService;

    private final SessionManagementServices sessionServices;

    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
        );
        UserEntity userEntity= (UserEntity) authentication.getPrincipal();
        String accessToken=jwtServices.generateAccessToken(userEntity);
        String refreshToken=jwtServices.generateRefreshToken(userEntity);
        sessionServices.generateSession(userEntity,refreshToken);
        return new LoginResponseDTO(userEntity.getUserid(),accessToken,refreshToken);
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        Long userId=jwtServices.getUserIdFromToken(refreshToken);
        sessionServices.validateSession(refreshToken);
        UserEntity userEntity=userService.findById(userId);
        String accessToken=jwtServices.generateAccessToken(userEntity);
        return new LoginResponseDTO(userEntity.getUserid(),accessToken,refreshToken);
    }
}

/*
// homework week 5 3rd problem .....

 * private final SessionRepository sessionRepository; // Inject session repository
 *
 *     public String login(LoginDTO loginDTO) {
 *         // Authenticate the user using Spring Security
 *         Authentication authentication = authenticationManager.authenticate(
 *                 new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
 *         );
 *
 *         // Get the authenticated user entity
 *         UserEntity userEntity = (UserEntity) authentication.getPrincipal();
 *
 *         // Generate a JWT token
 *         String token = jwtServices.generateJwtToken(userEntity);
 *
 *         // Remove any previous session for this user
 *         sessionRepository.deleteByUserId(userEntity.getUserid());
 *         // Create a new session for the user
 *         SessionEntity session = new SessionEntity();
 *         session.setUserId(userEntity.getUserid());
 *         session.setToken(token);
 *         session.setCreatedAt(java.time.LocalDateTime.now());
 *
 *         sessionRepository.save(session);
 *
 *         // Return the generated token
 *         return token;
 *     }
 */