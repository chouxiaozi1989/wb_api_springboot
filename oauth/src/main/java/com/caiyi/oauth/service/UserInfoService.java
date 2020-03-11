package com.caiyi.oauth.service;

import com.caiyi.oauth.entities.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserInfoService {
    List<UserInfo> getAll();

    void UpdateOpenid(String userid, String openid);
    void UpdateSessionKey(String userid, String session_key);
}