package com.elkard.tripmanager.model.entities;

import com.elkard.tripmanager.model.compositekeys.PlaceVoteCompositeKey;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TM_PLACE_VOTES", schema = "TRIPS")
@IdClass(PlaceVoteCompositeKey.class)
public class PlaceVote {

    @Id
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Id
    @Column(name = "PLACE_ID", nullable = false)
    private Long placeId;

    @ManyToOne
    @JoinColumn(name = "TRIP_ID", referencedColumnName = "TRIP_ID")
    private Trip trip;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceVote placeVote = (PlaceVote) o;
        return Objects.equals(userId, placeVote.userId) &&
                Objects.equals(placeId, placeVote.placeId) &&
                Objects.equals(trip, placeVote.trip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, placeId, trip);
    }

    @Override
    public String toString() {
        return "PlaceVote{" +
                "userId=" + userId +
                ", placeId=" + placeId +
                ", trip=" + trip +
                '}';
    }
}
