package com.ems;
import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//Marks this class as a configuration class for Spring
@Configuration

//Enables Spring Security for the application
@EnableWebSecurity
public class SecurityConfig {

 // Defines a security filter chain bean for configuring HTTP security
 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http
         // Disables CSRF protection (only do this if absolutely necessary, like for APIs or testing)
         .csrf(csrf -> csrf.disable())
         
         // Configures authorization rules for different URL patterns
         .authorizeHttpRequests(auth -> auth
             // Allows public access to login page, static resources, and root
             .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/").permitAll()
             
             // Restricts access to admin dashboard URLs to users with ADMIN role
             .requestMatchers("/adminDashboard/**").hasRole("ADMIN")
             
             // Allows both ADMIN and EMPLOYEE roles to access employee dashboard URLs
             .requestMatchers("/employeeDashboard/**").hasAnyRole("ADMIN", "EMPLOYEE")
             
             // Restricts client dashboard access to CLIENT role
             .requestMatchers("/clientDashboard/**").hasRole("CLIENT")
             
             // Requires authentication for any other request
             .anyRequest().authenticated()
         )
         
         // Configures custom form login behavior
         .formLogin(form -> form
             // Specifies the custom login page URL
             .loginPage("/login")
             
             // URL to submit the login form
             .loginProcessingUrl("/login")
             
             // Form field name for the username (in this case, email)
             .usernameParameter("email")
             
             // Form field name for the password
             .passwordParameter("password")
             
             // Redirects user after successful login based on role
             .defaultSuccessUrl("/redirect-by-role", true)
             
             // Redirects back to login with error on failure
             .failureUrl("/login?error=true")
             
             // Allows all users to access login form
             .permitAll()
         )
         
         // Configures logout behavior
         .logout(logout -> logout
             // URL to trigger logout
             .logoutUrl("/logout")
             
             // Redirect after successful logout
             .logoutSuccessUrl("/login?logout=true")
             
             // Invalidates the session after logout
             .invalidateHttpSession(true)
             
             // Deletes session cookie after logout
             .deleteCookies("JSESSIONID")
             
             // Allows all users to access logout functionality
             .permitAll()
         )
         
         // Configures what happens when access is denied (403 error)
         .exceptionHandling(exception -> exception
             // Redirects to access-denied page
             .accessDeniedPage("/access-denied")
         );
     
     // Builds and returns the security filter chain
     return http.build();
 }

 // Bean for encoding passwords using BCrypt
 @Bean
 public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
 }
}

