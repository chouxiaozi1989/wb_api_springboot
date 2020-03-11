package com.caiyi.oauth.service;

import com.caiyi.oauth.OauthApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OauthApplication.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void jscode2session() {
        System.out.println(userService.jscode2session("1").toJSONString());
    }
}