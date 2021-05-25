package com.elkard.tripmanager.dto.responses.trip;

import com.elkard.tripmanager.dto.responses.BaseResponse;

public class JoinTripResponse extends BaseResponse {

    private String tripName;
    private String host;

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
