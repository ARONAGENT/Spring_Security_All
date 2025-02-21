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
     <br>
   **Add Custom Security Filter -> Logging Filter**
     *Execution->*
     ![19 LoggingFilter Add In SecurityFilterChain-- Working Fine](https://github.com/user-attachments/assets/fab314ba-6c58-4643-b262-54ddc15ca05f)


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
     <br><br>
     Steps -> [JWT and Refresh Token.pdf](https://github.com/user-attachments/files/18875841/JWT.and.Refresh.Token.pdf)
<br>
**Executions ->**
1.Login the User and Generate 2 Tokens ->
<br>

![00 login the User and generate 2 Tokens ](https://github.com/user-attachments/assets/e46aba2b-1587-4fa5-951c-ebdc40d73deb)

2.With the help of stored refresh Cookie generate a newly access token ->
<br>
![01 with the help of stored refresh Cookie generate a newly access token](https://github.com/user-attachments/assets/acf5e240-d6cc-48ea-837a-29afda7450e8)

3.Generating newly access token with cookies valid  Refresh token

![02 Generating newly access token with cookies valid  Refresh token ](https://github.com/user-attachments/assets/fa75aa53-0a67-4f9b-9f8d-e468933dbbb3)

4.AccessTokenLookLikes
<br>
![03 AccessTokenLookLikes](https://github.com/user-attachments/assets/d3932c76-65e6-47f1-b940-9f9584fde49f)

5.RefreshTokenLooksLike
<br>
![04 RefreshTokenLooksLike](https://github.com/user-attachments/assets/8398fa7e-5a8c-467a-8555-63311ffe3352)


10. **Google OAuth2 Client in Spring Security**
   - Implementing Google OAuth2 authentication.
   - Integrating Spring Security with OAuth2 login flow.
     Steps -> [OAuth2Client - GOOGLE.pdf](https://github.com/user-attachments/files/18875843/OAuth2Client.-.GOOGLE.pdf)

     <br>
     
  **Executions ->**
  <br>
1.Hits the login Request 'http://localhost:8080/login'-> 
<br>

![13 hit the request -login](https://github.com/user-attachments/assets/dcb0aaf6-67f0-4a69-878c-c32d0874e9fd)

2.For Registration Click Your respective Google Account ->
<br>
![14  we are got to our spring security app](https://github.com/user-attachments/assets/145ba61a-4b37-4bc7-a279-75d7c5857b3b)

3.Read the Terms and Conditions and Proceed to Continue ->
<br>
![15 click your email and continue](https://github.com/user-attachments/assets/55d4c159-9482-48e8-ab8f-b2b9669e87c3)

4.When you Click continue And Your Authentication done  ->
<br>
![17 Successfully Response give back to our application with accessToken ](https://github.com/user-attachments/assets/0716ecb9-166a-45b9-bf49-3e866a744e94)

5.You can see the Console the info get Properly and also generate a JWT Token ->
<br>
![16  retrieve email with OAuth2SuccessHandler](https://github.com/user-attachments/assets/0e9e77f1-4f56-45f4-a58d-39721baeff59)

6.JWT Token VERIFIED ->
<br>
![18 this is your valid token ](https://github.com/user-attachments/assets/80eca843-0410-4d32-a659-6186188fa53c)

7.Also Successfully Saved in Your data in Database -> 
<br>
![19 In Db we successfully Save the Information of OAuth2Client](https://github.com/user-attachments/assets/81d9cce0-ba8e-4ea4-aa58-dd9ee06056e6)

11. **User Session Management With JWT**
   - Stateless authentication using JWT.
   - Managing user sessions securely.
     <br><br>
      Steps -> [Session Management in Spring Security.pdf](https://github.com/user-attachments/files/18875847/Session.Management.in.Spring.Security.pdf)
<br><br>
     **Executions ->**
     1.Login the user and Created a Active Session ->
     <br>
     ![20 LoginRequest with save token in Db (see time)](https://github.com/user-attachments/assets/8771e085-ee37-4eef-8951-b58e92c1faa3)

     2.User Session Saved in Database ->
     <br>
     ![21 Add JWT Token In DB So that Session is Active anytime](https://github.com/user-attachments/assets/d846c4fe-5e73-4694-9605-314bf0010734)

     

12. **Role-Based Authorization**
   - Implementing role-based access control (RBAC).
   - Assigning user roles dynamically.
     <br><br>
     Steps -> [Role Based Authorization And Granular based Authorization and Security Annotations.pdf](https://github.com/user-attachments/files/18875857/Role.Based.Authorization.And.Granular.based.Authorization.and.Security.Annotations.pdf)

     **Executions ->**
1.With the help of database Record You can understand who is ADMIN,CREATOR,USER
<br>

![00 tables](https://github.com/user-attachments/assets/5f792f22-6a2f-4cdd-91f5-ea4d9d924e18)



13. **Granular Authorization with Authorities and Permissions**
   - Using authorities and permissions for fine-grained access control.
   - Defining custom permissions for users.

14. **Security Method Annotations - @Secured and @PreAuthorize**
   - Using `@Secured` and `@PreAuthorize` annotations for securing methods.
   - Difference between `@Secured` and `@PreAuthorize`.

---

## **How to Run the Project**

### **Prerequisites**
- Java 21
- IntelliJ IDEA (or any preferred IDE)

### **Steps to Run**
1. **Clone the Repository**
   ```bash
   git clone https://github.com/ARONAGENT/Spring_Security_All.git
   cd Spring_Security_All 
   ```
2. **Run the Spring Boot Application**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Access the Application**
   - The application runs on `http://localhost:8080/`
   - Swagger documentation (if enabled) can be accessed at `http://localhost:8080/swagger-ui.html`

---

This project demonstrates a comprehensive implementation of Spring Security, ensuring secure authentication, authorization, and session management.

