package com.caiyi.oauth.entities;

import java.util.Date;

public class UserInfo {
    private String userid;
    private String openid;
    private String token;
    private Date expired_time;
    private String session_key;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(Date expired_time) {
        this.expired_time = expired_time;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }
}
