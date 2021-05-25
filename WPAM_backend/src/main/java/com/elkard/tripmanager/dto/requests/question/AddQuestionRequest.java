package com.elkard.tripmanager.dto.requests.question;

import com.elkard.tripmanager.dto.requests.BaseRequest;

import java.util.List;

public class AddQuestionRequest extends BaseRequest {

    private Long tripId;
    private String question;
    private List<String> answers;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
