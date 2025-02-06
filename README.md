# Spring Security Implementation

This project covers the implementation of Spring Security with JWT authentication, Google OAuth2, role-based authorization, and other security mechanisms. Below is a structured breakdown of the key security concepts covered in this project.

## Table of Contents

### **Section 1: Core Security Concepts and Implementation**
1. **Security Attacks**
   - Overview of common security vulnerabilities such as:
     - CSRF (Cross-Site Request Forgery)
     - XSS (Cross-Site Scripting)
     - SQL Injection
   - Strategies to mitigate these attacks.

2. **Internal Working of Spring Security**
   - Understanding the authentication and authorization flow.
   - How Spring Security intercepts requests.
   - Role of DelegatingFilterProxy and SecurityFilterChain.

3. **Core Components of Spring Security**
   - UserDetails, UserDetailsService, and PasswordEncoder.
   - AuthenticationManager and ProviderManager.
   - SecurityContext and SecurityContextHolder.

4. **Spring Security Web Configuration - SecurityFilterChain**
   - Configuring `SecurityFilterChain` using Java configuration.
   - Defining security rules for endpoints.

5. **Understanding JWT (JSON Web Token)**
   - Structure and purpose of JWT.
   - Decoding and verifying JWT.
   - Difference between access and refresh tokens.

6. **SIGNUP and LOGIN Using JWT**
   - Implementing user registration and login functionality.
   - Storing passwords securely using bcrypt hashing.

7. **Authentication Request Using JWT**
   - Implementing JWT authentication filter.
   - Extracting user details from the token.

8. **Exception Handling in Spring Security**
   - Handling authentication and authorization exceptions.
   - Customizing `AccessDeniedHandler` and `AuthenticationEntryPoint`.

---

### **Section 2: Advanced Security Features**
9. **JWT Refresh Token and Access Token-Based Authentication**
   - Implementing refresh token mechanism for session management.
   - Handling token expiration and renewal process.

10. **Google OAuth2 Client in Spring Security**
   - Implementing Google OAuth2 authentication.
   - Integrating Spring Security with OAuth2 login flow.

11. **User Session Management With JWT**
   - Stateless authentication using JWT.
   - Managing user sessions securely.

12. **Role-Based Authorization**
   - Implementing role-based access control (RBAC).
   - Assigning user roles dynamically.

13. **Granular Authorization with Authorities and Permissions**
   - Using authorities and permissions for fine-grained access control.
   - Defining custom permissions for users.

14. **Security Method Annotations - @Secured and @PreAuthorize**
   - Using `@Secured` and `@PreAuthorize` annotations for securing methods.
   - Difference between `@Secured` and `@PreAuthorize`.

---

### **Execution Screenshot**
(Screenshot attached here)

---

## **How to Run the Project**

### **Prerequisites**
- Java 21
- IntelliJ IDEA (or any preferred IDE)

### **Steps to Run**
1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd <repository-folder>
   ```
2. **Run the Spring Boot Application**
   ```bash
   ./mvnw spring-boot:run
   ```
   or if using Gradle:
   ```bash
   ./gradlew bootRun
   ```

3. **Access the Application**
   - The application runs on `http://localhost:8080/`
   - Swagger documentation (if enabled) can be accessed at `http://localhost:8080/swagger-ui.html`

---

This project demonstrates a comprehensive implementation of Spring Security, ensuring secure authentication, authorization, and session management.

