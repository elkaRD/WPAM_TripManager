package com.elkard.tripmanager.dto.requests.trip;

import com.elkard.tripmanager.dto.PlaceDetails;
import com.elkard.tripmanager.dto.requests.BaseRequest;

import java.util.List;

public class CreateTripRequest extends BaseRequest {

    private String tripName;
    private String nickname;
    private List<String> days;
    private List<PlaceDetails> places;
    private Boolean allowAddingPlaces;
    private Boolean allowAddingQuestions;
    private Boolean allowInviting;
    private Long prevTripId;

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public List<PlaceDetails> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceDetails> places) {
        this.places = places;
    }

    public Boolean getAllowAddingPlaces() {
        return allowAddingPlaces;
    }

    public void setAllowAddingPlaces(Boolean allowAddingPlaces) {
        this.allowAddingPlaces = allowAddingPlaces;
    }

    public Boolean getAllowAddingQuestions() {
        return allowAddingQuestions;
    }

    public void setAllowAddingQuestions(Boolean allowAddingQuestions) {
        this.allowAddingQuestions = allowAddingQuestions;
    }

    public Boolean getAllowInviting() {
        return allowInviting;
    }

    public void setAllowInviting(Boolean allowInviting) {
        this.allowInviting = allowInviting;
    }

    public Long getPrevTripId() {
        return prevTripId;
    }

    public void setPrevTripId(Long prevTripId) {
        this.prevTripId = prevTripId;
    }
}
