# Spring Security MAX...

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
   Brief Explanation of Internal Working -> [Spring Security Working Flow.pdf](https://github.com/user-attachments/files/18874505/Spring.Security.Working.Flow.pdf)

3. **Core Components of Spring Security**
   - UserDetails, UserDetailsService, and PasswordEncoder.
   - AuthenticationManager and ProviderManager.
   - SecurityContext and SecurityContextHolder.
<br><br>
**Flow Chart Spring Security ->**
<br><br>
![Spring Secuirty FlowChart](https://github.com/user-attachments/assets/6910a91d-bb10-4e14-be40-327e3543e9a8)


4. **Spring Security Web Configuration - SecurityFilterChain**
   - Configuring `SecurityFilterChain` using Java configuration.
   - Defining security rules for endpoints.

5. **Understanding JWT (JSON Web Token)**
   - Structure and purpose of JWT.
   - Decoding and verifying JWT.
   - Difference between access and refresh tokens.
  
    **Executions ->**
   <br><br>
   **1.Token Generation**
   
   ![08 JwtTokenGeneration](https://github.com/user-attachments/assets/c0614b1f-d5ea-45ac-964c-c84b1dba27f5)

   **2.Token Verification Successfully** 

   ![09 JwtTokenverfication](https://github.com/user-attachments/assets/4e1f5893-42ac-4609-8917-5ab4e0a2899f)
   

7. **SIGNUP and LOGIN Using JWT**
   - Implementing user registration and login functionality.
   - Storing passwords securely using bcrypt hashing.

     **Executions ->**
     <br><br>
     **1. Sign Up Request Status ->**
         
     ![10 Sign Up in Spring Security with Password Encoder](https://github.com/user-attachments/assets/77ffe071-37f7-4444-9927-40eab4efdc05)

     **2. Saved in Database ->**
        
     ![11 Saved in Db password as encoded](https://github.com/user-attachments/assets/c7f3c5f4-12dd-46c2-985e-86a6d2a01271)


8. **Authentication Request Using JWT**
   - Implementing JWT authentication filter.
   - Extracting user details from the token.

      **Executions ->**
     <br><br>
     **1.While Login JWT token Generates ->**
     
     ![12 Generate a Jwt token while Login in Sping Security](https://github.com/user-attachments/assets/cadb0749-0cf4-47df-ba9c-2a9b53e2c38e)
  
     **2.Jwt Token Save as a Cookie ->**
     
     ![13 JWT token saved in Cookie Format](https://github.com/user-attachments/assets/fc96e7fc-362f-4230-82f5-9ed7511254c5)
  
     **3. '/Post' Request valid only JWT Token in Valid ->**
     
     ![14 Get 'Post table' Request Only for Login Valid User with valid JWT token](https://github.com/user-attachments/assets/ce252ecc-9967-4f85-94f7-22f9a2fc7f84)
  
     **4.If the Token is Invalid then 403 Forbidden->**
     
     ![15 Without valid  JWT  token its given 403 Forbidden error](https://github.com/user-attachments/assets/4d056140-663d-44cf-9be1-02d8b7222fb2)


9. **Exception Handling in Spring Security**
   - Handling authentication and authorization exceptions.
   - Customizing `AccessDeniedHandler` and `AuthenticationEntryPoint`.

     **Executions ->**
     <br><br>
     **1. If the User is Unauthorized then throw Authentication Exception ->**
        
     ![16 Sping Security Exception Handling Done (Authentication exception)](https://github.com/user-attachments/assets/31b82ffa-2153-4294-8d76-0957cf5dfa50)
  
     **2.If JWT Token is Expired then ->**
     
     ![17 Jwt Exception Handling ](https://github.com/user-attachments/assets/0e69eab7-825e-4752-b19e-33148de793a5)
     
     **3.If the JWT Token is Incorrect then  ->**
     
     ![18 Jwt Exception If token in incorrect ](https://github.com/user-attachments/assets/d022b62e-3585-457e-82f7-583d5976b482)


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

