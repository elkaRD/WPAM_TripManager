package com.elkard.tripmanager.dto.responses.trip;

import com.elkard.tripmanager.dto.responses.BaseResponse;

import java.util.List;

public class GetTripListResponse extends BaseResponse {

    private List<TripListItem> trips;

    public List<TripListItem> getTrips() {
        return trips;
    }

    public void setTrips(List<TripListItem> trips) {
        this.trips = trips;
    }
}
