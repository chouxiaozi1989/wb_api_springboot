package com.caiyi.oauth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.caiyi.oauth.entities.UserInfo;
import com.caiyi.oauth.utils.httpUtils;
import com.caiyi.oauth.mapper.UserMapper;
import com.caiyi.oauth.service.UserService;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.net.URL;
import java.util.List;

@Component
@EnableApolloConfig("weixin")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${appid}")
    private String appid;

    @Value("${secret}")
    private String secret;

    @Value("${grant_type}")
    private String grant_type;

    @Value("${url_jscode2session}")
    private String url_jscode2session;

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public List<UserInfo> getAll() {
        return userMapper.getAll();
    }

    @Override
    public UserInfo getUserById(String userid) {
        return userMapper.getByUserId(userid, "1");
    }

    @Override
    public UserInfo getUserByOpenId(String openid) {
        return userMapper.getByOpenId(openid, "1");
    }

    @Override
    public UserInfo login(String userid, String password) {
        UserInfo userInfo = userMapper.getByUserId(userid, "1");
        //verify password
        if (password.equals(userInfo.getPassword())) {
            return userInfo;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void UpdateOpenid(String userid, String openid) {
        userMapper.UpdateOpenid(userid, openid);
    }

    @Override
    public JSONObject jscode2session(String code) {
        String str = "", session_key = "", openid = "";
        JSONObject response = new JSONObject();
        try {
            String urlStr = url_jscode2session + "?appid=" + appid +
                    "&secret=" +
                    secret +
                    "&js_code=" +
                    code +
                    "&grant_type=" +
                    grant_type;
            URL url = new URL(urlStr);

            str = httpUtils.doHttpRequestGET(url);
            if (!"".equals(str) && null != str) {
                JSONObject res = JSONObject.parseObject(str);

                if (!res.containsKey("openid")) {
                    response = res;
                } else {
                    session_key = res.getString("session_key");
                    openid = res.getString("openid");

                    //check isBind
                    UserInfo userInfo = this.getUserByOpenId(openid);
                    if (userInfo != null) {//如果已经绑定，则直接返回对应用户信息
                        response.put("userinfo", userInfo);
                        response.put("isBind", true);
                    } else {//没有绑定，则将openid、session_key、isBind:false返回
                        response.put("openid", openid);
                        response.put("session_key", session_key);
                        response.put("isBind", false);
                    }
                }
            } else {
                throw new Exception("Invoke Weixin API Error!Response null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserInfo oauth(String userid, String password, String openid, JSONObject userinfo) {
        UserInfo userInfo_local = userMapper.getByUserId(userid, "1");
        Assert.notNull(userInfo_local, "获取用户出错");
        Assert.isTrue(password.equals(userInfo_local.getPassword()), "密码错误");
        //bind local user
        userMapper.UpdateOpenid(userid, openid);

        //update userinfo
        userMapper.UpdateGen(userinfo.getInteger("gender"), userid);
        userMapper.UpdateCountry(userinfo.getString("country"), userid);
        userMapper.UpdateProvince(userinfo.getString("province"), userid);
        userMapper.UpdateCity(userinfo.getString("city"), userid);
        userMapper.UpdateUserName(userinfo.getString("nickName"), userid);
        userMapper.UpdateAvatarUrl(userinfo.getString("avatarUrl"), userid);

        return userMapper.getByUserId(userid, "1");
    }
}
