package com.elkard.tripmanager.dto.requests.availability;

import com.elkard.tripmanager.dto.requests.BaseRequest;

import java.util.List;

public class SubmitAvailabilityRequest extends BaseRequest {

    private Long tripId;
    private List<String> selectedDates;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public List<String> getSelectedDates() {
        return selectedDates;
    }

    public void setSelectedDates(List<String> selectedDates) {
        this.selectedDates = selectedDates;
    }
}
