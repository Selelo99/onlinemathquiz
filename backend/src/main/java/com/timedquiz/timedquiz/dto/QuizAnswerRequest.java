package com.timedquiz.timedquiz.dto;

public class QuizAnswerRequest {

    private Integer questionNumber;

    private String questionText;

    private String selectedAnswer;

    private String correctAnswer;

    private Boolean correct;


    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(
            Integer questionNumber) {

        this.questionNumber =
                questionNumber;
    }


    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(
            String questionText) {

        this.questionText =
                questionText;
    }


    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(
            String selectedAnswer) {

        this.selectedAnswer =
                selectedAnswer;
    }


    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(
            String correctAnswer) {

        this.correctAnswer =
                correctAnswer;
    }


    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(
            Boolean correct) {

        this.correct =
                correct;
    }

}