package com.elkard.tripmanager.model.compositekeys;

import java.io.Serializable;
import java.util.Objects;

public class PlaceVoteCompositeKey implements Serializable {

    private Long userId;
    private Long placeId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceVoteCompositeKey that = (PlaceVoteCompositeKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(placeId, that.placeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, placeId);
    }
}
