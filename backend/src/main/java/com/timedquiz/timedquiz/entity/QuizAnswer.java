package com.timedquiz.timedquiz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_answers")
public class QuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // =========================================================
    // QUIZ ATTEMPT
    // =========================================================

   @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "attempt_id", nullable = false)
        @JsonIgnore
        private QuizAttempt attempt;


    // =========================================================
    // QUESTION NUMBER
    // =========================================================

    @Column(
            name = "question_number",
            nullable = false
    )
    private Integer questionNumber;


    // =========================================================
    // QUESTION TEXT
    // =========================================================

    @Column(
            name = "question_text",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String questionText;


    // =========================================================
    // OPTIONS
    // =========================================================

    @Column(
            name = "options",
            columnDefinition = "TEXT"
    )
    private String options;


    // =========================================================
    // STUDENT ANSWER
    // =========================================================

    @Column(
            name = "student_answer"
    )
    private String studentAnswer;


    // =========================================================
    // CORRECT ANSWER
    // =========================================================

    @Column(
            name = "correct_answer"
    )
    private String correctAnswer;


    // =========================================================
    // CORRECT
    // =========================================================

    @Column(
            name = "is_correct"
    )
    private Boolean correct;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public QuizAnswer() {
    }


    // =========================================================
    // GETTERS
    // =========================================================

    public Long getId() {

        return id;
    }


    public QuizAttempt getAttempt() {

        return attempt;
    }


    public Integer getQuestionNumber() {

        return questionNumber;
    }


    public String getQuestionText() {

        return questionText;
    }


    public String getOptions() {

        return options;
    }


    public String getStudentAnswer() {

        return studentAnswer;
    }


    public String getCorrectAnswer() {

        return correctAnswer;
    }


    public Boolean getCorrect() {

        return correct;
    }


    // =========================================================
    // SETTERS
    // =========================================================

    public void setAttempt(
            QuizAttempt attempt) {

        this.attempt = attempt;
    }


    public void setQuestionNumber(
            Integer questionNumber) {

        this.questionNumber =
                questionNumber;
    }


    public void setQuestionText(
            String questionText) {

        this.questionText =
                questionText;
    }


    public void setOptions(
            String options) {

        this.options =
                options;
    }


    public void setStudentAnswer(
            String studentAnswer) {

        this.studentAnswer =
                studentAnswer;
    }


    public void setCorrectAnswer(
            String correctAnswer) {

        this.correctAnswer =
                correctAnswer;
    }


    public void setCorrect(
            Boolean correct) {

        this.correct =
                correct;
    }
}