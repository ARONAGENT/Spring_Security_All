package com.springJourney.Week5Practice.Handlers;

import com.springJourney.Week5Practice.Entities.UserEntity;
import com.springJourney.Week5Practice.Services.JwtServices;
import com.springJourney.Week5Practice.Services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtServices jwtServices;

    @Value("${deploy.env}")
    private String deployenv;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token= (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User= (DefaultOAuth2User) token.getPrincipal();
        String email=oAuth2User.getAttribute("email");
        UserEntity user= userService.findByEmail(email);
        if(user==null){
            UserEntity userEntity=UserEntity.builder().name(oAuth2User.getAttribute("name"))
                    .email(email)
                    .build();
            user=userService.save(userEntity);
        }

        String accessToken=jwtServices.generateAccessToken(user);
        String refreshToken=jwtServices.generateRefreshToken(user);
        Cookie cookie=new Cookie("refreshToken",refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployenv));
        response.addCookie(cookie);
        String frontEndUrl="http://localhost:8080/home.html?token="+accessToken;
        response.sendRedirect(frontEndUrl);
    }
}
