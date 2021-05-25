package com.elkard.tripmanager.dto;

import java.util.List;

public class QuestionDetails {

    private Long questionId;
    private String question;
    private List<AnswerDetails> answers;
    private Long allVotes;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswerDetails> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDetails> answers) {
        this.answers = answers;
    }

    public Long getAllVotes() {
        return allVotes;
    }

    public void setAllVotes(Long allVotes) {
        this.allVotes = allVotes;
    }
}
