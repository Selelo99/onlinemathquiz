package com.timedquiz.timedquiz.dto;

import java.util.List;

public class QuizSubmissionRequest {

    private String studentNumber;

    private String name;

    private String surname;

    private Integer chapter;

    private String quizTitle;

    private Integer totalQuestions;

    private Integer score;

    private Double percentage;

    private String status;

    private List<QuizAnswerRequest> answers;


    // =========================================================
    // STUDENT NUMBER
    // =========================================================

    public String getStudentNumber() {

        return studentNumber;
    }


    public void setStudentNumber(
            String studentNumber) {

        this.studentNumber = studentNumber;
    }


    // =========================================================
    // NAME
    // =========================================================

    public String getName() {

        return name;
    }


    public void setName(String name) {

        this.name = name;
    }


    // =========================================================
    // SURNAME
    // =========================================================

    public String getSurname() {

        return surname;
    }


    public void setSurname(String surname) {

        this.surname = surname;
    }


    // =========================================================
    // CHAPTER
    // =========================================================

    public Integer getChapter() {

        return chapter;
    }


    public void setChapter(Integer chapter) {

        this.chapter = chapter;
    }


    // =========================================================
    // QUIZ TITLE
    // =========================================================

    public String getQuizTitle() {

        return quizTitle;
    }


    public void setQuizTitle(String quizTitle) {

        this.quizTitle = quizTitle;
    }


    // =========================================================
    // TOTAL QUESTIONS
    // =========================================================

    public Integer getTotalQuestions() {

        return totalQuestions;
    }


    public void setTotalQuestions(
            Integer totalQuestions) {

        this.totalQuestions = totalQuestions;
    }


    // =========================================================
    // SCORE
    // =========================================================

    public Integer getScore() {

        return score;
    }


    public void setScore(Integer score) {

        this.score = score;
    }


    // =========================================================
    // PERCENTAGE
    // =========================================================

    public Double getPercentage() {

        return percentage;
    }


    public void setPercentage(
            Double percentage) {

        this.percentage = percentage;
    }


    // =========================================================
    // STATUS
    // =========================================================

    public String getStatus() {

        return status;
    }


    public void setStatus(String status) {

        this.status = status;
    }


    // =========================================================
    // ANSWERS
    // =========================================================

    public List<QuizAnswerRequest> getAnswers() {

        return answers;
    }


    public void setAnswers(
            List<QuizAnswerRequest> answers) {

        this.answers = answers;
    }
}