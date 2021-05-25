package com.elkard.tripmanager.model.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TM_PLACES", schema = "TRIPS")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TM_SEQ_PLACES")
    @SequenceGenerator(name = "TM_SEQ_PLACES", sequenceName = "TM_SEQ_PLACES", allocationSize = 1)
    @Column(name = "PLACE_ID", nullable = false)
    private Long placeId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "TRIP_ID", referencedColumnName = "TRIP_ID")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @Column(name = "LATITUDE")
    private String latitude;

    @Column(name = "LONGITUDE")
    private String longitude;

    @Column(name = "ACCEPTED")
    private Boolean accepted;

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(placeId, place.placeId) &&
                Objects.equals(name, place.name) &&
                Objects.equals(trip, place.trip) &&
                Objects.equals(user, place.user) &&
                Objects.equals(latitude, place.latitude) &&
                Objects.equals(longitude, place.longitude) &&
                Objects.equals(accepted, place.accepted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId, name, trip, user, latitude, longitude, accepted);
    }
}
