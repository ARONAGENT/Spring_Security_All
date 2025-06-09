# 🔐 Spring Security Complete Guide

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-red.svg)](https://spring.io/projects/spring-security)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![JWT](https://img.shields.io/badge/JWT-Auth-blue.svg)](https://jwt.io/)
[![OAuth2](https://img.shields.io/badge/OAuth2-Google-yellow.svg)](https://developers.google.com/identity/protocols/oauth2)

A comprehensive **Spring Security implementation** covering authentication, authorization, JWT tokens, OAuth2, role-based access control, and advanced security features. This project demonstrates production-ready security mechanisms with real-world examples and best practices.


## 🏗️ Architecture Overview

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Client App    │────│  Security Chain │────│  Authentication │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                    ┌───────────┼───────────┐
                    │           │           │
            ┌───────▼────┐ ┌────▼────┐ ┌───▼──────┐
            │    JWT     │ │ OAuth2  │ │   RBAC   │
            │   Filter   │ │ Client  │ │  Filter  │
            └────────────┘ └─────────┘ └──────────┘
                    │           │           │
                    └───────────┼───────────┘
                                │
                    ┌───────────▼───────────┐
                    │   Security Context    │
                    │   & User Details      │
                    └─────────────────────--┘
```

## ✨ Features

### 🔐 Core Security Features
- **JWT Authentication** with Access & Refresh Tokens
- **OAuth2 Integration** with Google Sign-In
- **Role-Based Access Control** (RBAC)
- **Method-Level Security** with annotations
- **Session Management** with database persistence
- **Password Encryption** using BCrypt
- **Custom Security Filters** and Filter Chain

### 🛡️ Security Mechanisms
- **CSRF Protection** and mitigation strategies
- **XSS Prevention** techniques
- **SQL Injection** protection
- **Custom Exception Handling** for auth failures
- **Token Validation** and expiration handling
- **Stateless Authentication** architecture

### 🎯 Advanced Features
- **Granular Permissions** system
- **Dynamic Role Assignment**
- **Token Refresh Mechanism**
- **Custom Authentication Providers**
- **Security Method Annotations**
- **Cookie-based Token Storage**

## 🛠️ Technologies Used

| Category | Technology | Purpose |
|----------|------------|---------|
| **Framework** | Spring Boot 3.x | Application foundation |
| **Security** | Spring Security 6.x | Authentication & Authorization |
| **Authentication** | JWT | Stateless token-based auth |
| **OAuth2** | Google OAuth2 | Third-party authentication |
| **Database** | JPA/Hibernate | User & session persistence |
| **Password** | BCrypt | Secure password hashing |
| **Build Tool** | Maven | Dependency management |
| **Java** | Java 21 | Programming language |

## 🚀 Getting Started

### Prerequisites
- ☕ **Java 21+**
- 📦 **Maven 3.8+**
- 🗄️ **Database** (H2/MySQL/PostgreSQL)
- 🌐 **Google OAuth2 Credentials** (for OAuth features)

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/ARONAGENT/Spring_Security_All.git
   cd Spring_Security_All
   ```

2. **Configure Google OAuth2** (Optional)
   ```yaml
   # application.yml
   spring:
     security:
       oauth2:
         client:
           registration:
             google:
               client-id: your-google-client-id
               client-secret: your-google-client-secret
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the application**
   - 🌐 **Main App**: http://localhost:8080
   - 📖 **Swagger UI**: http://localhost:8080/swagger-ui.html
   - 🔐 **Login**: http://localhost:8080/login

## 🔒 Security Implementation

### Security Filter Chain Configuration

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
```

### Core Security Components

#### 1. UserDetailsService Implementation
```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        // Load user from database
        // Convert to UserDetails with roles and authorities
    }
}
```

#### 2. Password Encoder
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
}
```

## 🎯 JWT Authentication

### JWT Token Structure
- **Header**: Algorithm and token type
- **Payload**: User claims and metadata
- **Signature**: Verification signature

### Token Generation Process
```java
@Service
public class JwtService {
    
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }
    
    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }
}
```

### Authentication Flow
1. **User Login** → Validate credentials
2. **Generate Tokens** → Access + Refresh tokens
3. **Store in Cookies** → Secure HTTP-only cookies
4. **Validate Requests** → JWT filter validation
5. **Token Refresh** → Generate new access token

## 🌐 OAuth2 Integration

### Google OAuth2 Configuration
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${GOOGLE_CLIENT_ID}
            client-secret: ${GOOGLE_CLIENT_SECRET}
            scope: openid,profile,email
        provider:
          google:
            authorization-uri: https://accounts.google.com/o/oauth2/auth
            token-uri: https://oauth2.googleapis.com/token
            user-info-uri: https://www.googleapis.com/oauth2/v2/userinfo
```

### OAuth2 Success Handler
```java
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                      HttpServletResponse response,
                                      Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        
        // Create or update user
        // Generate JWT token
        // Redirect to dashboard
    }
}
```

## 👥 Role-Based Authorization

### User Roles and Authorities
```java
@Entity
public class User {
    private String username;
    private String password;
    private String email;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
}

@Entity
public class Role {
    private String name; // ADMIN, USER, CREATOR
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissions;
}

@Entity
public class Permission {
    private String name; // READ, WRITE, DELETE
}
```

### Method-Level Security
```java
@RestController
public class AdminController {
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    
    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping("/admin/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_CREATOR"})
    @PostMapping("/admin/posts")
    public Post createPost(@RequestBody Post post) {
        return postService.save(post);
    }
}
```

## 📊 Session Management

### Database Session Storage
```java
@Entity
public class UserSession {
    private String sessionId;
    private String username;
    private String jwtToken;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean active;
}
```

### Session Service
```java
@Service
public class SessionService {
    
    public void createSession(String username, String jwtToken) {
        UserSession session = new UserSession();
        session.setUsername(username);
        session.setJwtToken(jwtToken);
        session.setActive(true);
        sessionRepository.save(session);
    }
    
    public boolean isSessionValid(String token) {
        return sessionRepository.findByJwtTokenAndActiveTrue(token)
            .map(session -> session.getExpiresAt().isAfter(LocalDateTime.now()))
            .orElse(false);
    }
}
```

## ⚡ Security Filters

### Custom JWT Authentication Filter
```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtService.extractUsername(jwt);
        }
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
```

### Custom Logging Filter
```java
@Component
public class LoggingFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.info("Request: {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());
        
        chain.doFilter(request, response);
        
        log.info("Response processed for: {}", httpRequest.getRequestURI());
    }
}
```

## 🛡️ Exception Handling

### Custom Authentication Entry Point
```java
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException authException) throws IOException {
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        
        String jsonResponse = """
            {
                "error": "Unauthorized",
                "message": "Authentication required",
                "timestamp": "%s",
                "path": "%s"
            }
            """.formatted(Instant.now(), request.getRequestURI());
            
        response.getWriter().write(jsonResponse);
    }
}
```

### JWT Exception Handling
```java
@ControllerAdvice
public class JwtExceptionHandler {
    
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
        ErrorResponse error = new ErrorResponse("JWT_ERROR", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
    
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        ErrorResponse error = new ErrorResponse("TOKEN_EXPIRED", "JWT token has expired");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
```

## 📸 Screenshots

### Spring Security Flow Chart
![Spring Security FlowChart](https://github.com/user-attachments/assets/6910a91d-bb10-4e14-be40-327e3543e9a8)

### JWT Token Generation
![JWT Token Generation](https://github.com/user-attachments/assets/c0614b1f-d5ea-45ac-964c-c84b1dba27f5)

### JWT Token Verification
![JWT Token Verification](https://github.com/user-attachments/assets/4e1f5893-42ac-4609-8917-5ab4e0a2899f)

### User Registration with Password Encoding
![Sign Up Process](https://github.com/user-attachments/assets/77ffe071-37f7-4444-9927-40eab4efdc05)

### Database Storage with Encoded Password
![Database Storage](https://github.com/user-attachments/assets/c7f3c5f4-12dd-46c2-985e-86a6d2a01271)

### JWT Token in Login Response
![JWT Login](https://github.com/user-attachments/assets/cadb0749-0cf4-47df-ba9c-2a9b53e2c38e)

### JWT Token Saved as Cookie
![JWT Cookie](https://github.com/user-attachments/assets/fc96e7fc-362f-4230-82f5-9ed7511254c5)

### Protected Endpoint Access
![Protected Access](https://github.com/user-attachments/assets/ce252ecc-9967-4f85-94f7-22f9a2fc7f84)

### Unauthorized Access - 403 Forbidden
![403 Forbidden](https://github.com/user-attachments/assets/4d056140-663d-44cf-9be1-02d8b7222fb2)

### Authentication Exception Handling
![Auth Exception](https://github.com/user-attachments/assets/31b82ffa-2153-4294-8d76-0957cf5dfa50)

### JWT Expiration Exception
![JWT Expired](https://github.com/user-attachments/assets/0e69eab7-825e-4752-b19e-33148de793a5)

### Custom Logging Filter
![Logging Filter](https://github.com/user-attachments/assets/fab314ba-6c58-4643-b262-54ddc15ca05f)

### Refresh Token Implementation
![Refresh Token Login](https://github.com/user-attachments/assets/e46aba2b-1587-4fa5-951c-ebdc40d73deb)
![Refresh Token Generation](https://github.com/user-attachments/assets/acf5e240-d6cc-48ea-837a-29afda7450e8)

### Google OAuth2 Integration
![OAuth2 Login](https://github.com/user-attachments/assets/dcb0aaf6-67f0-4a69-878c-c32d0874e9fd)
![Google Account Selection](https://github.com/user-attachments/assets/145ba61a-4b37-4bc7-a279-75d7c5857b3b)
![OAuth2 Success](https://github.com/user-attachments/assets/0716ecb9-166a-45b9-bf49-3e866a744e94)

### Session Management
![Session Creation](https://github.com/user-attachments/assets/8771e085-ee37-4eef-8951-b58e92c1faa3)
![Session Database](https://github.com/user-attachments/assets/d846c4fe-5e73-4694-9605-314bf0010734)

### Role Based Authority
![00 tables](https://github.com/user-attachments/assets/d3e1a44e-09c6-4e70-bc79-adee27f7b927)


## 🎥 Demo Videos 


https://github.com/user-attachments/assets/d605152e-9059-4cd7-8974-595c96dcb885




https://github.com/user-attachments/assets/98746d47-e051-41c3-a1bd-8b50c06cac8e



https://github.com/user-attachments/assets/a3d0cf93-761f-44f4-8a2f-51e31fbd6cd4




https://github.com/user-attachments/assets/9a1c773e-a439-443a-a6a5-d9cdbf42e909




## 📚 Documentation
### PDF Notes - 
   **1.Internal Working** - [Spring Security Working Flow.pdf](https://github.com/user-attachments/files/18874505/Spring.Security.Working.Flow.pdf)
   

   **2.JWT And Refresh Token** - [JWT and Refresh Token.pdf](https://github.com/user-attachments/files/18875841/JWT.and.Refresh.Token.pdf)
   
   
   **3.Steps of Oauth2Client Registration** - [OAuth2Client - GOOGLE.pdf](https://github.com/user-attachments/files/18875843/OAuth2Client.-.GOOGLE.pdf)

   
   **4.Session Management** - [Session Management in Spring Security.pdf](https://github.com/user-attachments/files/18875847/Session.Management.in.Spring.Security.pdf)

   
   **5.Role Based Authority** - [Role Based Authorization And Granular based Authorization and Security Annotations.pdf](https://github.com/user-attachments/files/18875857/Role.Based.Authorization.And.Granular.based.Authorization.and.Security.Annotations.pdf)

   ---

### API Endpoints

#### Authentication Endpoints
```http
POST /auth/signup          # User registration
POST /auth/login           # User login
POST /auth/refresh         # Refresh access token
POST /auth/logout          # User logout
```

#### Protected Endpoints
```http
GET  /post/**          # All users (ADMIN only)
PUT /post/**          # Update user (ADMIN only)
```

#### OAuth2 Endpoints
```http
GET  /login/authorization/google   # Google OAuth2 login
GET  /login/oauth2/code/google      # Google OAuth2 callback
```

### Configuration Examples

#### JWT Configuration
```yaml
jwt:
  secret: your-secret-key
  expiration: 86400000      # 24 hours
  refresh-expiration: 604800000  # 7 days
```

#### Security Configuration
```yaml
spring:
  security:
    require-ssl: true
    headers:
      frame-options: DENY
      content-type-options: nosniff
```

## 🔧 Advanced Features

### Dynamic Role Assignment
```java
@PostMapping("/admin/users/{id}/roles")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> assignRole(@PathVariable Long id, @RequestBody RoleRequest request) {
    userService.assignRole(id, request.getRoleName());
    return ResponseEntity.ok().build();
}
```

### Permission-Based Access
```java
@PreAuthorize("hasPermission(#postId, 'POST', 'DELETE')")
@DeleteMapping("/posts/{postId}")
public ResponseEntity<?> deletePost(@PathVariable Long postId) {
    postService.delete(postId);
    return ResponseEntity.ok().build();
}
```

### Custom Security Annotations
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ADMIN') or (hasRole('CREATOR') and #userId == authentication.principal.id)")
public @interface AdminOrOwner {
}

@AdminOrOwner
@PutMapping("/users/{userId}")
public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
    return ResponseEntity.ok(userService.update(userId, user));
}
```


## 🎯 Learning Outcomes

This project demonstrates:
- ✅ **Complete Spring Security** implementation
- ✅ **JWT Authentication** with refresh tokens
- ✅ **OAuth2 Integration** with Google
- ✅ **Role-Based Authorization** (RBAC)
- ✅ **Method-Level Security** with annotations
- ✅ **Exception Handling** for security failures
- ✅ **Session Management** with database persistence
- ✅ **Production-Ready** security features

## 🚀 Future Enhancements

- [ ] Multi-factor Authentication (MFA)
- [ ] Redis-based session storage
- [ ] Rate limiting implementation
- [ ] Security audit logging
- [ ] Password policy enforcement
- [ ] Account lockout mechanism
- [ ] Social login with multiple providers
- [ ] API key authentication
- [ ] SAML integration

## 👨‍💻 Author

**Rohan Uke**  
Backend Developer | Java & Spring Security Expert

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/rohan-uke)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/ARONAGENT)

---

## ⭐ Show your support

Give a ⭐️ if this project helped you understand Spring Security!

## 📞 Support

If you have any questions or need help with the project, please:
1. Check the [Issues](https://github.com/ARONAGENT/Spring_Security_All/issues) page
2. Create a new issue if your question isn't already answered
3. Contact me via [LinkedIn](https://linkedin.com/in/rohan-uke)

---

*Built with ❤️ using Spring Security and modern security practices*
