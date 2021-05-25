package com.elkard.tripmanager.dto.responses.trip;

import com.elkard.tripmanager.dto.DateDetails;
import com.elkard.tripmanager.dto.PlaceDetails;
import com.elkard.tripmanager.dto.QuestionDetails;
import com.elkard.tripmanager.dto.responses.BaseResponse;

import java.util.List;

public class GetTripDetailsResponse extends BaseResponse {

    private TripListItem general;
    private List<PlaceDetails> places;
    private List<QuestionDetails> questions;
    private List<String> days;
    private List<String> availability;
    private Boolean allowAddingPlaces;
    private Boolean allowAddingQuestions;
    private Boolean allowInviting;
    private Long placeAllVotes;
    private Boolean isHost;
    private List<DateDetails> dateVotes;
    private String tripCode;
    private List<NicknameItem> nicknames;

    public TripListItem getGeneral() {
        return general;
    }

    public void setGeneral(TripListItem general) {
        this.general = general;
    }

    public List<PlaceDetails> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceDetails> places) {
        this.places = places;
    }

    public List<QuestionDetails> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDetails> questions) {
        this.questions = questions;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public List<String> getAvailability() {
        return availability;
    }

    public void setAvailability(List<String> availability) {
        this.availability = availability;
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

    public void setAllowAddingQuestions(Boolean allowAddingAnswers) {
        this.allowAddingQuestions = allowAddingAnswers;
    }

    public Boolean getAllowInviting() {
        return allowInviting;
    }

    public void setAllowInviting(Boolean allowInviting) {
        this.allowInviting = allowInviting;
    }

    public Long getPlaceAllVotes() {
        return placeAllVotes;
    }

    public void setPlaceAllVotes(Long placeAllVotes) {
        this.placeAllVotes = placeAllVotes;
    }

    public Boolean getIsHost() {
        return isHost;
    }

    public void setIsHost(Boolean host) {
        isHost = host;
    }

    public Boolean getHost() {
        return isHost;
    }

    public void setHost(Boolean host) {
        isHost = host;
    }

    public List<DateDetails> getDateVotes() {
        return dateVotes;
    }

    public void setDateVotes(List<DateDetails> dateVotes) {
        this.dateVotes = dateVotes;
    }

    public String getTripCode() {
        return tripCode;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
    }

    public List<NicknameItem> getNicknames() {
        return nicknames;
    }

    public void setNicknames(List<NicknameItem> nicknames) {
        this.nicknames = nicknames;
    }
}
