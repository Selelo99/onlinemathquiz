package com.timedquiz.timedquiz.repository;

import com.timedquiz.timedquiz.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    //find the student using the student number
    Optional<Student> findByStudentNumber(String studentNumber);

    //check if the student exists
    boolean existsByStudentNumber(String studentNumber);
}

    
