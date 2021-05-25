package com.elkard.tripmanager.model.entities;

//import javax.persistence.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TM_TRIPS", schema = "TRIPS")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TM_SEQ_TRIPS")
    @SequenceGenerator(name = "TM_SEQ_TRIPS", sequenceName = "TM_SEQ_TRIPS", allocationSize = 1)
    @Column(name = "TRIP_ID", nullable = false)
    private Long tripId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "HOST_ID", referencedColumnName = "USER_ID")
    private User host;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "ALLOW_ADDING_PLACES", nullable = false)
    private Boolean allowAddingPlaces;

    @Column(name = "ALLOW_ADDING_QUESTIONS", nullable = false)
    private Boolean allowAddingQuestions;

    @Column(name = "ALLOW_INVITING", nullable = false)
    private Boolean allowInviting;

    @Column(name = "ACCEPTED", nullable = false)
    private Boolean accepted;

    @OneToOne
    @JoinColumn(name = "ACCEPTED_PLACE_ID", referencedColumnName = "PLACE_ID")
    private Place acceptedPlace;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getAllowAddingPlaces() {
        return allowAddingPlaces;
    }

    public void setAllowAddingPlaces(Boolean allowAddingPlaces) {
        this.allowAddingPlaces = allowAddingPlaces;
    }

    public Boolean getAllowAddingQuestions() {
        return allowAddingQuestions;
    }

    public void setAllowAddingQuestions(Boolean allowAddingQuestions) {
        this.allowAddingQuestions = allowAddingQuestions;
    }

    public Boolean getAllowInviting() {
        return allowInviting;
    }

    public void setAllowInviting(Boolean allowInviting) {
        this.allowInviting = allowInviting;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Place getAcceptedPlace() {
        return acceptedPlace;
    }

    public void setAcceptedPlace(Place acceptedPlace) {
        this.acceptedPlace = acceptedPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(tripId, trip.tripId) &&
                Objects.equals(name, trip.name) &&
                Objects.equals(host, trip.host) &&
                Objects.equals(code, trip.code) &&
                Objects.equals(allowAddingPlaces, trip.allowAddingPlaces) &&
                Objects.equals(allowAddingQuestions, trip.allowAddingQuestions) &&
                Objects.equals(allowInviting, trip.allowInviting) &&
                Objects.equals(accepted, trip.accepted) &&
                Objects.equals(acceptedPlace, trip.acceptedPlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, name, host, code, allowAddingPlaces, allowAddingQuestions, allowInviting, accepted, acceptedPlace);
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripId=" + tripId +
                ", name='" + name + '\'' +
                ", host=" + host +
                ", code='" + code + '\'' +
                ", allowAddingPlaces=" + allowAddingPlaces +
                ", allowAddingQuestions=" + allowAddingQuestions +
                ", allowInviting=" + allowInviting +
                ", accepted=" + accepted +
                ", acceptedPlace=" + acceptedPlace +
                '}';
    }
}
