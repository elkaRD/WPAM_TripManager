package com.elkard.tripmanager.dto.requests.trip;

import com.elkard.tripmanager.dto.requests.BaseRequest;

public class LeaveTripRequest extends BaseRequest {

    private Long tripId;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }
}
