package com.elkard.tripmanager.dto.requests.question;

import com.elkard.tripmanager.dto.requests.BaseRequest;

import java.util.List;

public class SelectAnswerRequest extends BaseRequest {

    private Long tripId;
    private Long questionId;
    private Long answerId;
    private Boolean selected;

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

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
