package com.timedquiz.timedquiz.controller;

import com.timedquiz.timedquiz.dto.AdminAttemptDTO;
import com.timedquiz.timedquiz.dto.QuizAttemptResponse;
import com.timedquiz.timedquiz.dto.QuizSubmissionRequest;
import com.timedquiz.timedquiz.entity.QuizAttempt;
import com.timedquiz.timedquiz.service.QuizAttemptService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attempts")
@CrossOrigin(origins = "*")
public class QuizAttemptController {

    private final QuizAttemptService attemptService;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public QuizAttemptController(
            QuizAttemptService attemptService) {

        this.attemptService =
                attemptService;
    }


    // =========================================================
    // SUBMIT QUIZ
    //
    // POST /api/attempts
    // =========================================================

    @PostMapping
    public ResponseEntity<?> submitQuiz(
            @RequestBody QuizSubmissionRequest request) {

        try {

            QuizAttempt attempt =
                    attemptService.saveAttempt(

                            request.getStudentNumber(),

                            request.getName(),

                            request.getSurname(),

                            request.getChapter(),

                            request.getQuizTitle(),

                            request.getScore(),

                            request.getTotalQuestions(),

                            request.getAnswers()
                    );


            QuizAttemptResponse response =
                    new QuizAttemptResponse(
                            attempt
                    );


            return ResponseEntity.ok(
                    response
            );

        }
        catch (Exception e) {

            e.printStackTrace();


            Map<String, Object> error =
                    new LinkedHashMap<>();


            error.put(
                    "success",
                    false
            );


            error.put(
                    "message",
                    e.getMessage() != null
                            ? e.getMessage()
                            : "Could not save quiz attempt."
            );


            return ResponseEntity
                    .badRequest()
                    .body(error);
        }
    }


    // =========================================================
    // GET ALL ATTEMPTS
    //
    // GET /api/attempts
    // =========================================================

    @GetMapping
    public ResponseEntity<?> getAllAttempts() {

        try {

            List<AdminAttemptDTO> attempts =
                    attemptService.getAllAttempts();


            return ResponseEntity.ok(
                    attempts
            );

        }
        catch (Exception e) {

            e.printStackTrace();


            Map<String, Object> error =
                    new LinkedHashMap<>();


            error.put(
                    "success",
                    false
            );


            error.put(
                    "message",
                    e.getMessage() != null
                            ? e.getMessage()
                            : "Could not load quiz attempts."
            );


            return ResponseEntity
                    .internalServerError()
                    .body(error);
        }
    }


    // =========================================================
    // GET ONE STUDENT'S ATTEMPTS
    //
    // GET /api/attempts/students/{studentNumber}
    // =========================================================

    @GetMapping(
            "/students/{studentNumber}"
    )
    public ResponseEntity<?> getStudentAttempts(
            @PathVariable String studentNumber) {

        try {

            if (
                    studentNumber == null ||
                    studentNumber.trim().isEmpty()
            ) {

                return ResponseEntity
                        .badRequest()
                        .body(
                                Map.of(
                                        "success",
                                        false,

                                        "message",
                                        "Student number is required."
                                )
                        );
            }


            List<AdminAttemptDTO> attempts =
                    attemptService
                            .getStudentAttempts(
                                    studentNumber.trim()
                            );


            return ResponseEntity.ok(
                    attempts
            );

        }
        catch (Exception e) {

            e.printStackTrace();


            Map<String, Object> error =
                    new LinkedHashMap<>();


            error.put(
                    "success",
                    false
            );


            error.put(
                    "message",
                    e.getMessage() != null
                            ? e.getMessage()
                            : "Could not load student attempts."
            );


            return ResponseEntity
                    .badRequest()
                    .body(error);
        }
    }


    // =========================================================
    // DELETE ALL ATTEMPTS
    //
    // DELETE /api/attempts
    // =========================================================

    @DeleteMapping
    public ResponseEntity<?> deleteAllAttempts() {

        try {

            attemptService.deleteAllAttempts();


            Map<String, Object> response =
                    new LinkedHashMap<>();


            response.put(
                    "success",
                    true
            );


            response.put(
                    "message",
                    "All quiz attempts deleted successfully."
            );


            return ResponseEntity.ok(
                    response
            );

        }
        catch (Exception e) {

            e.printStackTrace();


            Map<String, Object> error =
                    new LinkedHashMap<>();


            error.put(
                    "success",
                    false
            );


            error.put(
                    "message",
                    "Could not delete attempts."
            );


            return ResponseEntity
                    .internalServerError()
                    .body(error);
        }
    }
}