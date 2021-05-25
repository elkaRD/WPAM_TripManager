package com.elkard.tripmanager.dto.responses.trip;

import com.elkard.tripmanager.dto.responses.BaseResponse;

public class CreateTripResponse extends BaseResponse {

    private String tripCode;

    public String getTripCode() {
        return tripCode;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
    }
}
