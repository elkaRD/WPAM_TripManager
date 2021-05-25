package com.elkard.tripmanager.model.entities;

import com.elkard.tripmanager.model.compositekeys.AnswerVoteCompositeKey;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TM_ANSWER_VOTES", schema = "TRIPS")
@IdClass(AnswerVoteCompositeKey.class)
public class AnswerVote {

    @Id
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Id
    @Column(name = "ANSWER_ID", nullable = false)
    private Long answerId;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "TRIP_ID", referencedColumnName = "TRIP_ID")
    private Trip trip;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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
        AnswerVote that = (AnswerVote) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(answerId, that.answerId) &&
                Objects.equals(question, that.question) &&
                Objects.equals(trip, that.trip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, answerId, question, trip);
    }

    @Override
    public String toString() {
        return "AnswerVote{" +
                "userId=" + userId +
                ", answerId=" + answerId +
                ", question=" + question +
                ", trip=" + trip +
                '}';
    }
}
