package com.timedquiz.timedquiz.service;

import com.timedquiz.timedquiz.dto.AdminAttemptDTO;
import com.timedquiz.timedquiz.entity.QuizAttempt;
import com.timedquiz.timedquiz.entity.Student;
import com.timedquiz.timedquiz.repository.QuizAttemptRepository;
import com.timedquiz.timedquiz.repository.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizAttemptService {

    private final QuizAttemptRepository attemptRepository;
    private final StudentRepository studentRepository;

    public QuizAttemptService(
            QuizAttemptRepository attemptRepository,
            StudentRepository studentRepository) {

        this.attemptRepository = attemptRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public QuizAttempt saveAttempt(
            String studentNumber,
            String name,
            String surname,
            Integer chapter,
            String quizTitle,
            Integer score,
            Integer totalQuestions,
            Object answers) {

        /*
         * Find existing student using student number.
         * If the student does not exist, create the student.
         */
        Student student = studentRepository
                .findByStudentNumber(studentNumber)
                .orElseGet(() -> {

                    Student newStudent = new Student();

                    newStudent.setStudentNumber(studentNumber);
                    newStudent.setName(name);
                    newStudent.setSurname(surname);

                    return studentRepository.save(newStudent);
                });

        /*
         * Create the quiz attempt.
         */
        QuizAttempt attempt = new QuizAttempt();

        /*
         * IMPORTANT:
         * QuizAttempt stores the Student relationship,
         * not studentNumber/name/surname directly.
         */
        attempt.setStudent(student);

        attempt.setChapter(chapter);
        attempt.setQuizTitle(quizTitle);
        attempt.setScore(score);
        attempt.setTotalQuestions(totalQuestions);

        /*
         * Calculate percentage.
         */
        double percentage = 0.0;

        if (totalQuestions != null && totalQuestions > 0 && score != null) {
            percentage = ((double) score / totalQuestions) * 100.0;
        }

        attempt.setPercentage(percentage);

        /*
         * Pass mark = 50%.
         */
        attempt.setStatus(
                percentage >= 50.0 ? "PASS" : "FAIL"
        );

        /*
         * Determine the next attempt number
         * for this particular student.
         */
        List<QuizAttempt> previousAttempts =
                attemptRepository.findByStudent_StudentNumber(studentNumber);

        int nextAttemptNumber = previousAttempts.size() + 1;

        attempt.setAttemptNumber(nextAttemptNumber);

        /*
         * Save attempt.
         */
        return attemptRepository.save(attempt);
    }

    @Transactional(readOnly = true)
    public List<AdminAttemptDTO> getAllAttempts() {

        List<QuizAttempt> attempts =
                attemptRepository.findAll();

        List<AdminAttemptDTO> result =
                new ArrayList<>();

        for (QuizAttempt attempt : attempts) {

            result.add(convertToDTO(attempt));
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<AdminAttemptDTO> getStudentAttempts(
            String studentNumber) {

        List<QuizAttempt> attempts =
                attemptRepository.findByStudent_StudentNumber(
                        studentNumber
                );

        List<AdminAttemptDTO> result =
                new ArrayList<>();

        for (QuizAttempt attempt : attempts) {

            result.add(convertToDTO(attempt));
        }

        return result;
    }

    private AdminAttemptDTO convertToDTO(
            QuizAttempt attempt) {

        AdminAttemptDTO dto =
                new AdminAttemptDTO();

        Student student = attempt.getStudent();

        dto.setId(attempt.getId());

        if (student != null) {
            dto.setStudentNumber(
                    student.getStudentNumber()
            );

            dto.setName(
                    student.getName()
            );

            dto.setSurname(
                    student.getSurname()
            );
        }

        dto.setAttemptNumber(
                attempt.getAttemptNumber()
        );

        dto.setChapter(
                attempt.getChapter()
        );

        dto.setQuizTitle(
                attempt.getQuizTitle()
        );

        dto.setScore(
                attempt.getScore()
        );

        dto.setTotalQuestions(
                attempt.getTotalQuestions()
        );

        dto.setPercentage(
                attempt.getPercentage()
        );

        dto.setStatus(
                attempt.getStatus()
        );

        dto.setSubmittedAt(
                attempt.getSubmittedAt()
        );

        return dto;
    }

    @Transactional
    public void deleteAllAttempts() {

        attemptRepository.deleteAll();
    }
}