package com.caiyi.oauth.service.impl;

import com.caiyi.oauth.entities.UserInfo;
import com.caiyi.oauth.mapper.UserMapper;
import com.caiyi.oauth.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public List<UserInfo> getAll() {
        return userMapper.getAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void UpdateOpenid(String userid, String openid) {
        userMapper.UpdateOpenid(userid, openid);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void UpdateSessionKey(String userid, String session_key) {
        userMapper.UpdateSessionKey(userid, session_key);
    }
}
