package com.timedquiz.timedquiz.service;


import com.timedquiz.timedquiz.entity.Student;
import com.timedquiz.timedquiz.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService( StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }

    // =========================
    // CREATE STUDENT
    // =========================

    public Student createStudent(Student student) {

        if (studentRepository.existsByStudentNumber(student.getStudentNumber())) {

            throw new RuntimeException("Student number already exists");
        }

        return studentRepository.save(student);
    }


    // =========================
    // GET ALL STUDENTS
    // =========================

    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }


    // =========================
    // FIND BY STUDENT NUMBER
    // =========================

    public Student getByStudentNumber(String studentNumber) {

        return studentRepository.findByStudentNumber(studentNumber).orElseThrow(() -> new RuntimeException("Student not found"));
    }

}
