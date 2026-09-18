package com.timedquiz.timedquiz.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "students",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_student_number",
                        columnNames = "student_number"
                )
        }
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "student_number",
            nullable = false,
            unique = true
    )
    private String studentNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;


    // =========================================================
    // CONSTRUCTORS
    // =========================================================

    public Student() {
    }


    public Student(
            String studentNumber,
            String name,
            String surname) {

        this.studentNumber = studentNumber;
        this.name = name;
        this.surname = surname;
        this.createdAt = LocalDateTime.now();
    }


    // =========================================================
    // PRE PERSIST
    // =========================================================

    @PrePersist
    public void onCreate() {

        if (createdAt == null) {

            createdAt = LocalDateTime.now();
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


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    // =========================================================
    // SETTERS
    // =========================================================

    public void setId(Long id) {
        this.id = id;
    }


    public void setStudentNumber(
            String studentNumber) {

        this.studentNumber = studentNumber;
    }


    public void setName(String name) {

        this.name = name;
    }


    public void setSurname(String surname) {

        this.surname = surname;
    }


    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }
}