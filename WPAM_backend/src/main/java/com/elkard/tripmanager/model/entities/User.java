package com.elkard.tripmanager.model.entities;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TM_USERS", schema = "TRIPS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TM_SEQ_USERS")
    @SequenceGenerator(name = "TM_SEQ_USERS", sequenceName = "TM_SEQ_USERS", allocationSize = 1)
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "DEVICE_ID", nullable = false)
    private String deviceId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(deviceId, user.deviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, deviceId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
