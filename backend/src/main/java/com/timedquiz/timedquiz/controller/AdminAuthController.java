
package com.timedquiz.timedquiz.controller;

import com.timedquiz.timedquiz.dto.AdminLoginRequest;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AdminAuthController {


    private final AuthenticationManager authenticationManager;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AdminAuthController(
            AuthenticationManager authenticationManager) {

        this.authenticationManager =
                authenticationManager;
    }


    // =========================================================
    // LOGIN
    //
    // POST /api/auth/login
    // =========================================================

    @PostMapping("/login")
    public ResponseEntity<?> login(

            @RequestBody AdminLoginRequest request,

            HttpServletRequest httpRequest) {


        try {


            // =====================================================
            // VALIDATE USERNAME
            // =====================================================

            if (
                request.getUsername() == null ||
                request.getUsername()
                        .trim()
                        .isEmpty()
            ) {

                return ResponseEntity
                        .badRequest()
                        .body(
                            Map.of(
                                "success",
                                false,

                                "message",
                                "Username is required."
                            )
                        );
            }


            // =====================================================
            // VALIDATE PASSWORD
            // =====================================================

            if (
                request.getPassword() == null ||
                request.getPassword().isEmpty()
            ) {

                return ResponseEntity
                        .badRequest()
                        .body(
                            Map.of(
                                "success",
                                false,

                                "message",
                                "Password is required."
                            )
                        );
            }


            // =====================================================
            // AUTHENTICATE
            // =====================================================

            Authentication authentication =

                    authenticationManager.authenticate(

                        new UsernamePasswordAuthenticationToken(

                            request.getUsername()
                                    .trim(),

                            request.getPassword()
                        )
                    );


            // =====================================================
            // SET SECURITY CONTEXT
            // =====================================================

            SecurityContext context =
                    SecurityContextHolder
                            .createEmptyContext();


            context.setAuthentication(
                    authentication
            );


            SecurityContextHolder.setContext(
                    context
            );


            // =====================================================
            // RESPONSE
            // =====================================================

            Map<String, Object> response =
                    new LinkedHashMap<>();


            response.put(
                    "success",
                    true
            );


            response.put(
                    "message",
                    "Login successful."
            );


            response.put(
                    "username",
                    authentication.getName()
            );


            return ResponseEntity.ok(
                    response
            );


        }
        catch (Exception e) {


            e.printStackTrace();


            return ResponseEntity
                    .status(401)
                    .body(
                        Map.of(
                            "success",
                            false,

                            "message",
                            "Invalid username or password."
                        )
                    );
        }
    }


    // =========================================================
    // LOGOUT
    //
    // POST /api/auth/logout
    // =========================================================

    @PostMapping("/logout")
    public ResponseEntity<?> logout(

            HttpServletRequest request) {


        request.getSession(false);

        SecurityContextHolder.clearContext();


        return ResponseEntity.ok(
                Map.of(
                    "success",
                    true,

                    "message",
                    "Logout successful."
                )
        );
    }
}
