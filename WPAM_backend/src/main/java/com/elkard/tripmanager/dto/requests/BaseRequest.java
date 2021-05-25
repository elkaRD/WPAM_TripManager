package com.elkard.tripmanager.dto.requests;

public abstract class BaseRequest {

    protected String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
