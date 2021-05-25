package com.elkard.tripmanager.model.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TM_ANSWERS", schema = "TRIPS")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TM_SEQ_ANSWERS")
    @SequenceGenerator(name = "TM_SEQ_ANSWERS", sequenceName = "TM_SEQ_ANSWERS", allocationSize = 1)
    @Column(name = "ANSWER_ID", nullable = false)
    private Long answerId;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID")
    private Question question;

    @Column(name = "ANSWER", nullable = false)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TRIP_ID", referencedColumnName = "TRIP_ID")
    private Trip trip;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        Answer answer1 = (Answer) o;
        return Objects.equals(answerId, answer1.answerId) &&
                Objects.equals(question, answer1.question) &&
                Objects.equals(answer, answer1.answer) &&
                Objects.equals(user, answer1.user) &&
                Objects.equals(trip, answer1.trip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId, question, answer, user, trip);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", question=" + question +
                ", answer='" + answer + '\'' +
                ", user=" + user +
                ", trip=" + trip +
                '}';
    }
}
