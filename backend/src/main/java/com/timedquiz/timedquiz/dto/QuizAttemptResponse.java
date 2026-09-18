package com.timedquiz.timedquiz.dto;

import com.timedquiz.timedquiz.entity.QuizAttempt;

import java.time.LocalDateTime;

public class QuizAttemptResponse {

    private Long id;

    private String studentNumber;

    private String name;

    private String surname;

    private Integer chapter;

    private String quizTitle;

    private Integer score;

    private Integer totalQuestions;

    private Double percentage;

    private String status;

    private Integer attemptNumber;

    private LocalDateTime submittedAt;


    // =========================================================
    // EMPTY CONSTRUCTOR
    // =========================================================

    public QuizAttemptResponse() {
    }


    // =========================================================
    // CONSTRUCTOR FROM ENTITY
    // =========================================================

    public QuizAttemptResponse(QuizAttempt attempt) {

        if (attempt == null) {
            return;
        }

        this.id = attempt.getId();

        this.chapter = attempt.getChapter();

        this.quizTitle = attempt.getQuizTitle();

        this.score = attempt.getScore();

        this.totalQuestions = attempt.getTotalQuestions();

        this.percentage = attempt.getPercentage();

        this.status = attempt.getStatus();

        this.attemptNumber = attempt.getAttemptNumber();

        this.submittedAt = attempt.getSubmittedAt();


        // =====================================================
        // STUDENT INFORMATION
        // =====================================================

        if (attempt.getStudent() != null) {

            this.studentNumber =
                    attempt.getStudent().getStudentNumber();

            this.name =
                    attempt.getStudent().getName();

            this.surname =
                    attempt.getStudent().getSurname();
        }
    }


    // =========================================================
    // GETTERS
    // =========================================================

    public Long getId() {
        return id;
    }


    public String getStudentNumber() {
        return studentNumber;
    }


    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }


    public Integer getChapter() {
        return chapter;
    }


    public String getQuizTitle() {
        return quizTitle;
    }


    public Integer getScore() {
        return score;
    }


    public Integer getTotalQuestions() {
        return totalQuestions;
    }


    public Double getPercentage() {
        return percentage;
    }


    public String getStatus() {
        return status;
    }


    public Integer getAttemptNumber() {
        return attemptNumber;
    }


    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }


    // =========================================================
    // SETTERS
    // =========================================================

    public void setId(Long id) {
        this.id = id;
    }


    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setSurname(String surname) {
        this.surname = surname;
    }


    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }


    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }


    public void setScore(Integer score) {
        this.score = score;
    }


    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }


    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }


    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}