package com.caiyi.oauth.mapper;

import com.caiyi.oauth.OauthApplication;
import com.caiyi.oauth.entities.AppInfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OauthApplication.class)
class AppInfoMapperTest {
    @Autowired
    private AppInfoMapper appInfoMapper;

    @Test
    void getByAppidAndKey() {
        AppInfo app = appInfoMapper.getByAppidAndKey("test", "123");
        Assert.notNull(app, "null。。。。。。");
        System.out.println(app.toJSONString());
    }

    @Test
    void getAll() {
        List<AppInfo> allApps = appInfoMapper.getAll();
        for (AppInfo allApp : allApps) {
            System.out.println(allApp.toJSONString());
        }
    }
}