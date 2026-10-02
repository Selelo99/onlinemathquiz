package com.timedquiz.timedquiz.config;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // =========================================================
    // PASSWORD ENCODER
    // =========================================================

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }


    // =========================================================
    // ADMIN USER
    // =========================================================
                /*
                 * Password from application.properties or environment variable is plain text.
                 * Encode it before Spring Security uses it.
                 */
    @Bean
    public UserDetailsService userDetailsService(@Value("${timedquiz.admin.username}") String username, @Value("${timedquiz.admin.password}") String password, PasswordEncoder passwordEncoder){

        UserDetails admin = User.builder().username(username).password(passwordEncoder.encode(password)).roles("ADMIN").build();

        return new InMemoryUserDetailsManager(admin);
    }


    // =========================================================
    // AUTHENTICATION MANAGER
    // =========================================================

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }


    // =========================================================
    // SECURITY CONTEXT REPOSITORY
    //
    // Stores the authenticated SecurityContext
    // in the HTTP session.
    // =========================================================

    @Bean
    public SecurityContextRepository securityContextRepository() {

        return new HttpSessionSecurityContextRepository();
    }


    // =========================================================
    // CORS
    // =========================================================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // -----------------------------------------------------
        // FRONTEND ORIGINS
        // -----------------------------------------------------

        configuration.setAllowedOrigins(
            List.of(
                // Local development                                // GitHub Pages
                "http://127.0.0.1:5500", "http://localhost:5500", "https://selelo99.github.io"
        ));


        // -----------------------------------------------------
        // HTTP METHODS
        // -----------------------------------------------------

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));


        // -----------------------------------------------------
        // HEADERS
        // -----------------------------------------------------

        configuration.setAllowedHeaders(List.of("*"));


        // -----------------------------------------------------
        // COOKIES / SESSION
        // -----------------------------------------------------

        /*
         * Required because the frontend uses:
         *
         * credentials: "include"
         */
        configuration.setAllowCredentials(true);


        // -----------------------------------------------------
        // REGISTER CORS CONFIGURATION
        // -----------------------------------------------------

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


    // =========================================================
    // SECURITY FILTER CHAIN
    // =========================================================

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityContextRepository securityContextRepository) throws Exception {

        http
            // =================================================
            // CORS
            // =================================================
            .cors(cors -> {})

            // =================================================
            // CSRF
            // =================================================

            /*
             * This application uses a JavaScript API.
             *
             * CSRF is disabled for the current API architecture.
             */
            .csrf(csrf -> csrf.disable())

            // =================================================
            // SECURITY CONTEXT
            // =================================================

            /*
             * We explicitly save the SecurityContext
             * from AdminAuthController.
             */
            .securityContext(securityContext -> securityContext.securityContextRepository(securityContextRepository).requireExplicitSave(true))

            // =================================================
            // AUTHORIZATION
            // =================================================

            .authorizeHttpRequests(auth -> auth

                // -------------------------------------------------
                // PUBLIC FRONTEND FILES
                // -------------------------------------------------

                .requestMatchers("/", "/index.html", "/student.html", "/quiz.html", "/result.html", "/admin-login.html", "/student-attempts.html", "/admin.html", "/style.css", "/js/**")
                .permitAll()

                // -------------------------------------------------
                // ADMIN LOGIN
                // -------------------------------------------------

                .requestMatchers("/api/auth/login")
                .permitAll()

                // -------------------------------------------------
                // ADMIN LOGOUT
                // -------------------------------------------------

                .requestMatchers("/api/auth/logout")
                .permitAll()

                // -------------------------------------------------
                // STUDENT REGISTRATION
                // -------------------------------------------------

                .requestMatchers("/api/students")
                .permitAll()


                // -------------------------------------------------
                // QUIZ SUBMISSION
                // -------------------------------------------------

                .requestMatchers("/api/attempts")
                .permitAll()


                // -------------------------------------------------
                // STUDENT ATTEMPT HISTORY
                //
                // TEMPORARILY PUBLIC
                // -------------------------------------------------

                .requestMatchers("/api/attempts/students/**")
                .permitAll()

                // -------------------------------------------------
                // EVERYTHING ELSE
                // -------------------------------------------------

                .anyRequest()
                .authenticated()
            );


        return http.build();
    }
}