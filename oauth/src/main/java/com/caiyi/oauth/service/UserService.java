package com.caiyi.oauth.service;

import com.alibaba.fastjson.JSONObject;
import com.caiyi.oauth.entities.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserInfo> getAll();

    UserInfo getUserById(String userid);

    UserInfo getUserByOpenId(String openid);

    UserInfo login(String userid, String password);

    void UpdateOpenid(String userid, String openid);

    JSONObject jscode2session(String code);

    UserInfo oauth(String userid, String password, String openid, JSONObject userinfo);
}