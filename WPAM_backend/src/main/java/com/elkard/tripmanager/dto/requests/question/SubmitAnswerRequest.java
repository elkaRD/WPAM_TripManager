package com.elkard.tripmanager.dto.requests.question;

import com.elkard.tripmanager.dto.requests.BaseRequest;

import java.util.List;

public class SubmitAnswerRequest extends BaseRequest {

    private Long tripId;
    private Long questionId;
    private List<Long> selectedAnswers;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public List<Long> getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setSelectedAnswers(List<Long> selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }
}
