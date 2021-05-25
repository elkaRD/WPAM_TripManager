package com.elkard.tripmanager.model.compositekeys;

import java.io.Serializable;
import java.util.Objects;

public class TripDateCompositeKey implements Serializable {

    private Long tripId;
    private String day;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripDateCompositeKey that = (TripDateCompositeKey) o;
        return Objects.equals(tripId, that.tripId) &&
                Objects.equals(day, that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, day);
    }
}
