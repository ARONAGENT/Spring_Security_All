package com.springJourney.Week5Practice.Configuration;

import com.springJourney.Week5Practice.Filters.JwtAuthFilter;
import com.springJourney.Week5Practice.Filters.LoggingFilter;
import com.springJourney.Week5Practice.Handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static com.springJourney.Week5Practice.Entities.Enums.Roles.ADMIN;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final LoggingFilter loggingFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth ->
                        auth
                                // /auth/** and home.html routes are permitted to all i.e for (USER,ADMINS,CREATOR)
                                // which means you can signup and login
                                .requestMatchers("/auth/**","/home.html").permitAll()
                                // /post/** are permitted to ADMINS only
                                // which admin do all crud operation on post entity
                                .requestMatchers("/post/**").hasRole(ADMIN.name())
                                // get request related to /post/** are only for ADMINS and CREATORS
                                //.requestMatchers(HttpMethod.GET,"/post/**").hasAnyRole(ADMIN.name(), CREATOR.name())
                                .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(loggingFilter, JwtAuthFilter.class);
//                .oauth2Login(oauth2config ->
//                        oauth2config
//                                .failureUrl("/login?error=true")
//                                .successHandler(oAuth2SuccessHandler));
                //.formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

//    @Bean
//    UserDetailsService myInMemoryUserDetailService(){
//        UserDetails normalUsers= User
//                .withUsername("Rahul")
//                .password(passwordEncoder().encode("aron@1234"))
//                .roles("User")
//                .build();
//        UserDetails adminUsers= User
//                .withUsername("Rohan")
//                .password(passwordEncoder().encode("aron@1235"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(normalUsers,adminUsers);
//    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }
}
