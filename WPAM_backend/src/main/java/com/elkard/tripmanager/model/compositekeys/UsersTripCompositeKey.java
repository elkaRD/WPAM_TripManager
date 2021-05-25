package com.elkard.tripmanager.model.compositekeys;

import com.elkard.tripmanager.model.entities.Trip;

import java.io.Serializable;
import java.util.Objects;

public class UsersTripCompositeKey implements Serializable {

    public UsersTripCompositeKey()
    {

    }

    public UsersTripCompositeKey(Long tripId, Long userId)
    {
        this.tripId = tripId;
        this.userId = userId;
    }

    public UsersTripCompositeKey(Trip trip)
    {
        this.tripId = trip.getTripId();
        this.userId = trip.getHost().getUserId();
    }

    private Long tripId;
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersTripCompositeKey that = (UsersTripCompositeKey) o;
        return Objects.equals(tripId, that.tripId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, userId);
    }
}
