package com.elkard.tripmanager.dto.requests.place;

import com.elkard.tripmanager.dto.requests.BaseRequest;

import java.util.List;

public class SubmitPlaceRequest extends BaseRequest {

    private Long tripId;
    private List<Long> selectedPlaces;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public List<Long> getSelectedPlaces() {
        return selectedPlaces;
    }

    public void setSelectedPlaces(List<Long> selectedPlaces) {
        this.selectedPlaces = selectedPlaces;
    }
}
