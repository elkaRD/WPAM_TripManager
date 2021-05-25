package com.elkard.tripmanager.dto;

import com.elkard.tripmanager.model.entities.Place;

public class PlaceDetails {

    private Long placeId;
    private String addressName;
    private Boolean containsCoords;
    private String lon;
    private String lat;
    private Long votes;
    private Boolean selected;

    public PlaceDetails()
    {

    }

    public PlaceDetails(Place place)
    {
        this.placeId = place.getPlaceId();
        this.addressName = place.getName();
        if (place.getLatitude() != null && place.getLatitude().length() > 0)
        {
            this.containsCoords = true;
            this.lat = place.getLatitude();
            this.lon = place.getLongitude();
        }
        else
        {
            this.containsCoords = false;
        }
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String name) {
        this.addressName = name;
    }

    public Boolean getContainsCoords() {
        return containsCoords;
    }

    public void setContainsCoords(Boolean containsCoords) {
        this.containsCoords = containsCoords;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
