package com.caiyi.oauth.service.impl;

import com.caiyi.oauth.service.ApolloConfigService;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.stereotype.Component;

@Component
public class ApolloConfigServiceImpl implements ApolloConfigService {
    @ApolloConfig
    private Config config;

    @Override
    public void getConfig() {
        System.out.println(config.getProperty("test", "err"));
    }

    @ApolloConfigChangeListener
    private void someOnChange(ConfigChangeEvent changeEvent) {
        //update injected value of batch if it is changed in Apollo
        if (changeEvent.isChanged("test")) {
            System.out.println(config.getProperty("test", "err"));
        }
    }
}
