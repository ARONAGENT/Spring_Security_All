package com.springJourney.Week5Practice.Controllers;

import com.springJourney.Week5Practice.DTOs.LoginDTO;
import com.springJourney.Week5Practice.DTOs.LoginResponseDTO;
import com.springJourney.Week5Practice.DTOs.SignUpDTO;
import com.springJourney.Week5Practice.DTOs.UserDTO;
import com.springJourney.Week5Practice.Services.AuthServices;
import com.springJourney.Week5Practice.Services.SessionManagementServices;
import com.springJourney.Week5Practice.Services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServices authServices;
    private final UserService userService;
    private final SessionManagementServices sessionServices;

    @Value("${deploy.env}")
    private String deployenv;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest req,
                                                  HttpServletResponse response) {
        LoginResponseDTO loginResponseDTO = authServices.login(loginDTO);
        Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployenv));
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDTO);
    }


    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() ->
                        new AuthenticationServiceException("Refresh token not found in Cookie"));
        LoginResponseDTO loginResponseDTO = authServices.refreshToken(refreshToken);
        return ResponseEntity.ok(loginResponseDTO);

    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Get the refresh token from the cookies
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                String refreshToken = cookie.getValue();

                // Delete the session associated with the refresh token
                sessionServices.deleteSession(refreshToken);

                // Remove the refresh token cookie
                Cookie clearCookie = new Cookie("refreshToken", null);
                clearCookie.setHttpOnly(true);
                clearCookie.setSecure("production".equals(deployenv));
                clearCookie.setPath("/");
                clearCookie.setMaxAge(0); // Immediately expire the cookie
                response.addCookie(clearCookie);

            }
        }
        return ResponseEntity.ok("Logged out successfully.");
    }
}

