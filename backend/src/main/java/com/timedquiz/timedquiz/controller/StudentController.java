package com.timedquiz.timedquiz.controller;

import com.timedquiz.timedquiz.entity.Student;
import com.timedquiz.timedquiz.service.StudentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService){

        this.studentService = studentService;
    }

    // =========================
    // CREATE STUDENT
    // =========================

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){

        Student savedStudent = studentService.createStudent(student);

        return ResponseEntity.ok(savedStudent);
    }


    // =========================
    // GET ALL STUDENTS
    // =========================

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {

        return ResponseEntity.ok(studentService.getAllStudents());
    }


    // =========================
    // GET STUDENT BY NUMBER
    // =========================

    @GetMapping("/{studentNumber}")
    public ResponseEntity<Student> getStudent(@PathVariable String studentNumber){

        return ResponseEntity.ok(studentService.getByStudentNumber(studentNumber));
    }

}
