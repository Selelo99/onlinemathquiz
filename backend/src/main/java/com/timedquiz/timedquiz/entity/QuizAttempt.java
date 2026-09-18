package com.timedquiz.timedquiz.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "quiz_attempts")
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // =========================================================
    // STUDENT
    // =========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "student_id",
            nullable = false
    )
    private Student student;


    // =========================================================
    // ANSWERS
    // =========================================================

 @OneToMany(
        mappedBy = "attempt",
        cascade = CascadeType.ALL,
        orphanRemoval = true
)
@JsonManagedReference
private List<QuizAnswer> answers = new ArrayList<>();


    // =========================================================
    // CHAPTER
    // =========================================================

    @Column(nullable = false)
    private Integer chapter;


    // =========================================================
    // QUIZ TITLE
    // =========================================================

    @Column(name = "quiz_title")
    private String quizTitle;


    // =========================================================
    // SCORE
    // =========================================================

    @Column(nullable = false)
    private Integer score;


    // =========================================================
    // TOTAL QUESTIONS
    // =========================================================

    @Column(
            name = "total_questions",
            nullable = false
    )
    private Integer totalQuestions;


    // =========================================================
    // PERCENTAGE
    // =========================================================

    @Column(nullable = false)
    private Double percentage;


    // =========================================================
    // STATUS
    // =========================================================

    @Column(nullable = false)
    private String status;


    // =========================================================
    // ATTEMPT NUMBER
    // =========================================================

    @Column(
            name = "attempt_number",
            nullable = false
    )
    private Integer attemptNumber;


    // =========================================================
    // SUBMITTED DATE
    // =========================================================

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public QuizAttempt() {
    }


    // =========================================================
    // AUTOMATIC DATE
    // =========================================================

    @PrePersist
    public void onCreate() {

        if (submittedAt == null) {

            submittedAt =
                    LocalDateTime.now();
        }
    }


    // =========================================================
    // ADD ANSWER
    // =========================================================

    public void addAnswer(
            QuizAnswer answer) {

        answers.add(answer);

        answer.setAttempt(this);
    }


    // =========================================================
    // GET ANSWERS
    // =========================================================

    public List<QuizAnswer> getAnswers() {

        return answers;
    }


    public void setAnswers(
            List<QuizAnswer> answers) {

        this.answers = answers;

        if (answers != null) {

            for (
                    QuizAnswer answer
                    : answers
            ) {

                answer.setAttempt(this);
            }
        }
    }


    // =========================================================
    // GETTERS
    // =========================================================

    public Long getId() {

        return id;
    }


    public Student getStudent() {

        return student;
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

    public void setStudent(
            Student student) {

        this.student = student;
    }


    public void setChapter(
            Integer chapter) {

        this.chapter = chapter;
    }


    public void setQuizTitle(
            String quizTitle) {

        this.quizTitle = quizTitle;
    }


    public void setScore(
            Integer score) {

        this.score = score;
    }


    public void setTotalQuestions(
            Integer totalQuestions) {

        this.totalQuestions =
                totalQuestions;
    }


    public void setPercentage(
            Double percentage) {

        this.percentage =
                percentage;
    }


    public void setStatus(
            String status) {

        this.status = status;
    }


    public void setAttemptNumber(
            Integer attemptNumber) {

        this.attemptNumber =
                attemptNumber;
    }


    public void setSubmittedAt(
            LocalDateTime submittedAt) {

        this.submittedAt =
                submittedAt;
    }
}