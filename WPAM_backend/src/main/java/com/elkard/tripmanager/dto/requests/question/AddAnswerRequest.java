package com.elkard.tripmanager.dto.requests.question;

import com.elkard.tripmanager.dto.requests.BaseRequest;

public class AddAnswerRequest extends BaseRequest {

    private Long tripId;
    private Long questionId;
    private String answer;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
