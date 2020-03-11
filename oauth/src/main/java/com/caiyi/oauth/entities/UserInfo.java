package com.caiyi.oauth.entities;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class UserInfo implements Serializable {
    private String userid;
    private String openid;
    private String gen;
    private String country;
    private String province;
    private String city;
    private String password;
    private Date birthday;
    private String username;
    private String email;
    private String avatarUrl;
    private AccessToken accessToken;

    public JSONObject toJSON() {
        JSONObject res = new JSONObject();
        res.put("userid", this.userid);
        res.put("openid", this.openid);
        res.put("gen", this.gen);
        res.put("country", this.country);
        res.put("province", this.province);
        res.put("city", this.city);
        res.put("password", this.password);
        res.put("birthday", this.birthday);
        res.put("username", this.username);
        res.put("email", this.email);
        res.put("avatarUrl", this.avatarUrl);
        return res;
    }
}
