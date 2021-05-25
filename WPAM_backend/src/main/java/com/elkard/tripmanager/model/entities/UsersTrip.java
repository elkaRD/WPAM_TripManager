package com.elkard.tripmanager.model.entities;

import com.elkard.tripmanager.model.compositekeys.UsersTripCompositeKey;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TM_USERS_TRIPS", schema = "TRIPS")
@IdClass(UsersTripCompositeKey.class)
public class UsersTrip {

    @Id
    @Column(name = "TRIP_ID", nullable = false)
    private Long tripId;

    @Id
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersTrip usersTrip = (UsersTrip) o;
        return Objects.equals(tripId, usersTrip.tripId) &&
                Objects.equals(userId, usersTrip.userId) &&
                Objects.equals(username, usersTrip.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, userId, username);
    }

    @Override
    public String toString() {
        return "UsersTrip{" +
                "tripId=" + tripId +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
