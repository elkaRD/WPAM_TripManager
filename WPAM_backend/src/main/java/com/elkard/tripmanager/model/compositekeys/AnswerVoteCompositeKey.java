package com.elkard.tripmanager.model.compositekeys;

import java.io.Serializable;
import java.util.Objects;

public class AnswerVoteCompositeKey implements Serializable {

    private Long userId;
    private Long answerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerVoteCompositeKey that = (AnswerVoteCompositeKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(answerId, that.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, answerId);
    }
}
