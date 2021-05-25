package com.elkard.tripmanager.dto.requests.place;

import com.elkard.tripmanager.dto.requests.BaseRequest;

public class AddPlaceRequest extends BaseRequest {

    private Long tripId;
    private String name;
    private Boolean containsCoords;
    private String coordLon;
    private String coordLat;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getContainsCoords() {
        return containsCoords;
    }

    public void setContainsCoords(Boolean containsCoords) {
        this.containsCoords = containsCoords;
    }

    public String getCoordLon() {
        return coordLon;
    }

    public void setCoordLon(String coordLon) {
        this.coordLon = coordLon;
    }

    public String getCoordLat() {
        return coordLat;
    }

    public void setCoordLat(String coordLat) {
        this.coordLat = coordLat;
    }
}
