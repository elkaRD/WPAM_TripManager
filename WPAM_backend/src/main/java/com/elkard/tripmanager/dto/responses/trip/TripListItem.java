package com.elkard.tripmanager.dto.responses.trip;

public class TripListItem {

    private Long tripId;
    private String tripName;
    private String dateRange;
    private String placeSummary;
    private String hostNickname;
    private Boolean accepted;
    private Boolean past;
    private Integer notifications;
    private Boolean isOwner;
    private String nickname;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getPlaceSummary() {
        return placeSummary;
    }

    public void setPlaceSummary(String placeSummary) {
        this.placeSummary = placeSummary;
    }

    public String getHostNickname() {
        return hostNickname;
    }

    public void setHostNickname(String hostNickname) {
        this.hostNickname = hostNickname;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Boolean getPast() {
        return past;
    }

    public void setPast(Boolean past) {
        this.past = past;
    }

    public Integer getNotifications() {
        return notifications;
    }

    public void setNotifications(Integer notifications) {
        this.notifications = notifications;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
