package com.elkard.tripmanager.dto.requests.trip;

import com.elkard.tripmanager.dto.requests.BaseRequest;

public class JoinTripRequest extends BaseRequest
{
    private String code;
    private String nickname;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
