package com.elkard.tripmanager.model.entities;

import com.elkard.tripmanager.model.compositekeys.AvailabilityCompositeKey;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TM_AVAILABILITY", schema = "TRIPS")
@IdClass(AvailabilityCompositeKey.class)
public class Availability {

    @Id
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Id
    @Column(name = "TRIP_ID", nullable = false)
    private Long tripId;

    @Id
    @Column(name = "DAY", nullable = false)
    private String day;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Availability that = (Availability) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(tripId, that.tripId) &&
                Objects.equals(day, that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, tripId, day);
    }

    @Override
    public String toString() {
        return "Availability{" +
                "userId=" + userId +
                ", tripId=" + tripId +
                ", day='" + day + '\'' +
                '}';
    }
}
