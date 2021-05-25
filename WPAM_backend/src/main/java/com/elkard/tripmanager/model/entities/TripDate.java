package com.elkard.tripmanager.model.entities;

import com.elkard.tripmanager.model.compositekeys.TripDateCompositeKey;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TM_TRIP_DATES", schema = "TRIPS")
@IdClass(TripDateCompositeKey.class)
public class TripDate {

    @Id
    @Column(name = "TRIP_ID", nullable = false)
    private Long tripId;

    @Id
    @Column(name = "DAY", nullable = false)
    private String day;

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
        TripDate tripDate = (TripDate) o;
        return Objects.equals(tripId, tripDate.tripId) &&
                Objects.equals(day, tripDate.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, day);
    }

    @Override
    public String toString() {
        return "TripDate{" +
                "tripId=" + tripId +
                ", day='" + day + '\'' +
                '}';
    }
}
