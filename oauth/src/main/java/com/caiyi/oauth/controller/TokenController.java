package com.caiyi.oauth.controller;

import com.caiyi.oauth.entities.*;
import com.caiyi.oauth.mapper.AppInfoMapper;
import com.caiyi.oauth.mapper.UserMapper;
import com.caiyi.oauth.service.TokenService;
import com.caiyi.oauth.utils.MD5Util;
import com.caiyi.oauth.utils.NotRepeatSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("ALL")
@Slf4j
@RestController
@RequestMapping("/api/token")
public class TokenController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AppInfoMapper appInfoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    /**
     * API Token
     *
     * @param sign
     * @return
     */
    @PostMapping("/api_token")
    public ApiResponse apiToken(@RequestHeader("appid") String appId, @RequestHeader("key") String key, @RequestHeader("timestamp") String timestamp, @RequestHeader("sign") String sign) {
        Assert.isTrue(!StringUtils.isEmpty(appId) && !StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty(sign), "参数错误");
        long requestInterval = System.currentTimeMillis() - Long.parseLong(timestamp);
        Assert.isTrue(requestInterval < 5 * 60 * 1000, "请求过期，请重新请求");
        // 1. 根据appId查询数据库获取appSecret
        AppInfo appInfo = appInfoMapper.getByAppidAndKey(appId, key);
        Assert.notNull(appInfo, "app认证失败！");
        // 2. 校验签名
        String signString = timestamp + appId + appInfo.getKey();
        String signature = MD5Util.encode(signString);
        log.info(signature);
        Assert.isTrue(signature.equals(sign), "签名错误");
        // 3. 如果正确生成一个token保存到redis中，如果错误返回错误信息
        AccessToken accessToken = tokenService.saveToken(0, appInfo, null);
        return ApiResponse.success(accessToken);
    }

    @NotRepeatSubmit(5000)
    @PostMapping("/user_token")
    public ApiResponse<UserInfo> userToken(String userid, String password, @RequestHeader("token") String token) {
        // 1. 根据用户名查询密码, 并比较密码(密码可以RSA加密一下)
        UserInfo userInfo = userMapper.getByUserId(userid, "1");
        Assert.notNull(userInfo, "获取用户出错");
        Assert.isTrue(password.equals(userInfo.getPassword()), "密码错误");
        // 2. 保存Token
        AppInfo appInfo = tokenService.getTokenInfo(token).getAppInfo();//从api_token信息中获取app信息
        AccessToken accessToken = tokenService.saveToken(1, appInfo, userInfo);
        userInfo.setAccessToken(accessToken);
        return ApiResponse.success(userInfo);
    }

    public static void main(String[] args) {
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        String signString = timestamp + "test" + "123";
        String sign = MD5Util.encode(signString);
        System.out.println(sign);
        System.out.println("-------------------");
        signString = "123" + "1c5330f3-41c9-4f27-b2e9-e908544e3341" + timestamp + "A1scr6";
//        signString = "password=sa&userid=CY&123" + "4462b558-7743-4f40-9dcb-a140c5e7e399" + timestamp + "A1scr6";
        sign = MD5Util.encode(signString);
        System.out.println(sign);
    }
}