
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
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    // =========================================================
    // ADMIN USER
    // =========================================================

    @Bean
    public UserDetailsService userDetailsService(@Value("${timedquiz.admin.username}") String username, @Value("${timedquiz.admin.password}") String password, PasswordEncoder passwordEncoder){

        UserDetails admin = User.builder()

                        .username(username)
                        /*
                         * The password in application.properties
                         * is plain text.
                         *
                         * Encode it before giving it to
                         * Spring Security.
                         */
                        .password(passwordEncoder.encode(password))
                        .roles("ADMIN")
                        .build();


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
    // CORS
    // =========================================================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();


        /*
         * Your frontend is currently being served from
         * 127.0.0.1:5500.
         *
         * localhost:5500 is included as well so that
         * either address can be used during development.
         */

        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:5500"));


        configuration.setAllowedMethods(
                List.of(
                    "GET",
                    "POST",
                    "PUT",
                    "DELETE",
                    "OPTIONS"
                )
        );


        configuration.setAllowedHeaders(
                List.of("*")
        );


        /*
         * Required because the frontend uses:
         *
         * credentials: "include"
         */

        configuration.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();


        source.registerCorsConfiguration(
                "/**",
                configuration
        );


        return source;
    }


    // =========================================================
    // SECURITY FILTER CHAIN
    // =========================================================

    @Bean
    public SecurityFilterChain securityFilterChain(

            HttpSecurity http)

            throws Exception {


        http


            // =====================================================
            // CORS
            // =====================================================

            .cors(cors -> {
            })


            // =====================================================
            // CSRF
            // =====================================================

            .csrf(csrf ->
                csrf.disable()
            )


            // =====================================================
            // AUTHORIZATION
            // =====================================================

            .authorizeHttpRequests(auth -> auth
                // -------------------------------------------------
                // Public frontend files
                // -------------------------------------------------

                .requestMatchers(
                    "/",
                    "/index.html",
                    "/student.html",
                    "/quiz.html",
                    "/result.html",
                    "/admin-login.html",
                    "/student-attempts.html",
                    "/admin.html",
                    "/style.css",
                    "/js/**"
                )
                .permitAll()


                // -------------------------------------------------
                // Admin login
                // -------------------------------------------------

                .requestMatchers(
                    "/api/auth/login"
                )
                .permitAll()


                // -------------------------------------------------
                // Student registration
                // -------------------------------------------------

                .requestMatchers(
                    "/api/students"
                )
                .permitAll()


                // -------------------------------------------------
                // Student quiz submission
                // -------------------------------------------------

                .requestMatchers(
                    "/api/attempts"
                )
                .permitAll()


                // -------------------------------------------------
                // Student attempt history
                //
                // TEMPORARILY PUBLIC FOR TESTING
                // -------------------------------------------------

                .requestMatchers(
                    "/api/attempts/students/**"
                ).permitAll()


                // -------------------------------------------------
                // Everything else requires authentication
                // -------------------------------------------------

                .anyRequest()
                .authenticated()
            )


            // =====================================================
            // SECURITY CONTEXT
            // =====================================================

            .securityContext(
                securityContext ->
                    securityContext
                        .requireExplicitSave(false)
            );


        return http.build();
    }
}