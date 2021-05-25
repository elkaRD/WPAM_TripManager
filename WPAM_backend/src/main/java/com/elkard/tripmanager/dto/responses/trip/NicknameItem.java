package com.elkard.tripmanager.dto.responses.trip;

public class NicknameItem {
    private String id;
    private String nickname;
    private Boolean isCurUser;
    private Boolean isHost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getCurUser() {
        return isCurUser;
    }

    public void setCurUser(Boolean curUser) {
        isCurUser = curUser;
    }

    public Boolean getHost() {
        return isHost;
    }

    public void setHost(Boolean host) {
        isHost = host;
    }
}
