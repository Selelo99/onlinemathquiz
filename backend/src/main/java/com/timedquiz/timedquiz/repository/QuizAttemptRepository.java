
package com.timedquiz.timedquiz.repository;

import com.timedquiz.timedquiz.entity.QuizAttempt;
import com.timedquiz.timedquiz.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizAttemptRepository
        extends JpaRepository<QuizAttempt, Long> {


    // =========================================================
    // GET ALL ATTEMPTS
    // =========================================================

    List<QuizAttempt> findAllByOrderBySubmittedAtAsc();


    // =========================================================
    // GET ATTEMPTS FOR STUDENT
    // =========================================================

    List<QuizAttempt> findByStudentOrderBySubmittedAtAsc(
            Student student
    );

    //List<QuizAttempt> findByStudentNumber( String studentNumber );
     List<QuizAttempt> findByStudent_StudentNumber(String studentNumber);

    // =========================================================
    // COUNT ATTEMPTS FOR STUDENT + CHAPTER
    // =========================================================

    long countByStudentAndChapter(
            Student student,
            Integer chapter
    );
}