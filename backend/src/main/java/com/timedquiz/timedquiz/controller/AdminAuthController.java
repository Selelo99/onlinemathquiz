package com.timedquiz.timedquiz.controller;

import com.timedquiz.timedquiz.dto.AdminLoginRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.context.SecurityContextRepository;

import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AdminAuthController {

    private final AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AdminAuthController(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {

        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }


    // =========================================================
    // LOGIN
    //
    // POST /api/auth/login
    // =========================================================

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        try {
            // =====================================================
            // VALIDATE REQUEST
            // =====================================================

            if (request == null) {

                return ResponseEntity.badRequest().body(
                    Map.of(
                        "success",
                        false,

                        "message",
                        "Login request is required."
                    )
                );
            }


            // =====================================================
            // VALIDATE USERNAME
            // =====================================================

            if(request.getUsername() == null || request.getUsername().trim().isEmpty()){

                return ResponseEntity.badRequest().body(
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

            if (request.getPassword() == null || request.getPassword().isEmpty()){

                return ResponseEntity.badRequest().body(
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

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword()));

            // =====================================================
            // CREATE SECURITY CONTEXT
            // =====================================================

            SecurityContext context = SecurityContextHolder.createEmptyContext();

            context.setAuthentication(authentication);


            // =====================================================
            // SET SECURITY CONTEXT
            // =====================================================

            SecurityContextHolder.setContext(context);


            // =====================================================
            // SAVE SECURITY CONTEXT TO HTTP SESSION
            // =====================================================

            /*
             * This is important.
             *
             * The authenticated admin must remain authenticated
             * when the browser makes the next request.
             *
             * The context is therefore explicitly saved to
             * the HTTP session.
             */

            securityContextRepository.saveContext(context, httpRequest, httpResponse);

            // =====================================================
            // RESPONSE
            // =====================================================

            Map<String, Object> response = new LinkedHashMap<>();

            response.put("success", true);

            response.put("message", "Login successful.");

            response.put("username", authentication.getName());

            return ResponseEntity.ok(response);

        }
        catch(Exception e) {

            /*
             * Do not expose the actual exception to
             * the browser.
             *
             * Log it on the backend instead.
             */

            e.printStackTrace();

            return ResponseEntity.status(401).body(
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
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

        try {

            SecurityContext context = SecurityContextHolder.createEmptyContext();

            SecurityContextHolder.clearContext();

            securityContextRepository.saveContext(context, request, response);

            if(request.getSession(false) != null) {

                request.getSession(false).invalidate();
            }


            return ResponseEntity.ok(
                Map.of(
                    "success",
                    true,

                    "message",
                    "Logout successful."
                )
            );


        }
        catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.status(500).body(
                Map.of(
                    "success",
                    false,

                    "message",
                    "Logout failed."
                )
            );
        }
    }
}