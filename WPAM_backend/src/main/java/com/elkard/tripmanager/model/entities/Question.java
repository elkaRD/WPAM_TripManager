package com.elkard.tripmanager.model.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "TM_QUESTIONS", schema = "TRIPS")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TM_SEQ_QUESTIONS")
    @SequenceGenerator(name = "TM_SEQ_QUESTIONS", sequenceName = "TM_SEQ_QUESTIONS", allocationSize = 1)
    @Column(name = "QUESTION_ID", nullable = false)
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "TRIP_ID", referencedColumnName = "TRIP_ID")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @Column(name = "QUESTION_DATE", nullable = false)
    private Instant questionDate;

    @Column(name = "QUESTION", nullable = false)
    private String question;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(Instant questionDate) {
        this.questionDate = questionDate;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return questionId.equals(question1.questionId) &&
                trip.equals(question1.trip) &&
                user.equals(question1.user) &&
                questionDate.equals(question1.questionDate) &&
                question.equals(question1.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, trip, user, questionDate, question);
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", trip=" + trip +
                ", user=" + user +
                ", questionDate=" + questionDate +
                ", question='" + question + '\'' +
                '}';
    }
}
