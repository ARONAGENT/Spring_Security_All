package com.springJourney.Week5Practice.Filters;

import com.springJourney.Week5Practice.Entities.UserEntity;
import com.springJourney.Week5Practice.Services.JwtServices;
import com.springJourney.Week5Practice.Services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtServices jwtServices;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            final String requestTokenHandler = request.getHeader("Authorization");
            if (requestTokenHandler == null || !requestTokenHandler.startsWith("Bearer")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = requestTokenHandler.replace("Bearer", "").trim();

            Long userid = jwtServices.getUserIdFromToken(token);

            if (userid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserEntity userEntity = userService.findById(userid);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userEntity, null, userEntity.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        }catch (Exception e){
            handlerExceptionResolver.resolveException(request,response,null,e);
        }
    }
}

// homework week 5 3rd problem .....
// Code for DB Active Session Some Exception Handling occur ,,.....
//@Override
//protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//        throws ServletException, IOException {
//
//    try {
//        final String requestTokenHandler = request.getHeader("Authorization");
//        log.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());
//        log.debug("Authorization header: {}", requestTokenHandler);
//
//        if (requestTokenHandler == null || !requestTokenHandler.startsWith("Bearer")) {
//            log.warn("Authorization header is missing or does not start with 'Bearer'.");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Extract token
//        String token = requestTokenHandler.replace("Bearer", "").trim();
//        log.info("Extracted token: {}", token);
//
//        // Validate token against the database
//        log.info("Checking token in database: {}", token);
//        Optional<SessionEntity> session = sessionRepository.findByToken(token);
//        if (session.isEmpty()) {
//            log.warn("Invalid token: Not found in the database.");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Token is valid, extract user ID
//        Long userId = jwtServices.getUserIdFromToken(token);
//        log.info("Token is valid. Extracted user ID: {}", userId);
//
//        // Ensure the user is authenticated in SecurityContext
//        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            log.info("Loading user details for user ID: {}", userId);
//            UserEntity userEntity = userService.findById(userId);
//
//            if (userEntity != null) {
//                log.info("User found: {}", userEntity.getEmail());
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(userEntity, null, null);
//                authenticationToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                log.info("User authentication set in SecurityContext.");
//            } else {
//                log.warn("User not found in the database for user ID: {}", userId);
//            }
//        }
//
//        // Proceed with the filter chain
//        filterChain.doFilter(request, response);
//
//    } catch (Exception e) {
//        log.error("Error occurred during token validation: {}", e.getMessage(), e);
//        handlerExceptionResolver.resolveException(request, response, null, e);
//    }
//}
