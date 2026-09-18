package com.timedquiz.timedquiz.repository;

import com.timedquiz.timedquiz.entity.QuizAnswer;
import com.timedquiz.timedquiz.entity.QuizAttempt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizAnswerRepository
        extends JpaRepository<QuizAnswer, Long> {

    List<QuizAnswer>
    findByAttemptOrderByQuestionNumberAsc(
            QuizAttempt attempt
    );
}