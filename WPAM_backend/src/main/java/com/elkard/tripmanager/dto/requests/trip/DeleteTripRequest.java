package com.elkard.tripmanager.dto.requests.trip;

import com.elkard.tripmanager.dto.requests.BaseRequest;

public class DeleteTripRequest extends BaseRequest {

    private Long tripId;
    private String explanation;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
