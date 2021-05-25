package com.elkard.tripmanager.dto.requests.trip;

import com.elkard.tripmanager.dto.requests.BaseRequest;

import java.util.List;

public class AcceptTripRequest extends BaseRequest {

    private Long tripId;
    private List<String> days;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }
}
