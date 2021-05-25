package com.elkard.tripmanager.dto.requests.place;

import com.elkard.tripmanager.dto.requests.BaseRequest;

import java.util.List;

public class SelectPlaceRequest extends BaseRequest {

    private Long tripId;
    private Long placeId;
    private Boolean selected;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
