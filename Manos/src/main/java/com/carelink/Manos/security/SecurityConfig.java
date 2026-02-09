package com.carelink.Manos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Define the security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/public/**").permitAll()  // Allow public endpoints
                .anyRequest().authenticated()         // Secure all other endpoints
            .and()
            .formLogin()
                .loginPage("/login")                  // Custom login page
                .permitAll()                           // Allow anyone to access the login page
            .and()
            .logout()
                .permitAll();                         // Allow anyone to access logout

        return http.build();  // Return the configured SecurityFilterChain
    }

    // Configure the UserDetailsService to manage user authentication
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        userDetailsService.createUser(User.withUsername("user")
                .password("{noop}password")   // Noop encoder for simplicity (don't use in production)
                .roles("USER")
                .build());
        return userDetailsService;
    }
}
