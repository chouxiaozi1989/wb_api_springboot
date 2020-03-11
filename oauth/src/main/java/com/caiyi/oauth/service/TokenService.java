package com.caiyi.oauth.service;

import com.caiyi.oauth.entities.AccessToken;
import com.caiyi.oauth.entities.AppInfo;
import com.caiyi.oauth.entities.TokenInfo;
import com.caiyi.oauth.entities.UserInfo;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    public AccessToken saveToken(int tokenType, AppInfo appInfo, UserInfo userInfo);

    public TokenInfo getTokenInfo(String token);
}