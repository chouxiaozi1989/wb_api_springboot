package com.caiyi.oauth.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableApolloConfig
@EnableConfigurationProperties({DataSourceProperties.class})
public class DruidConfig {
    private final Logger log = LoggerFactory.getLogger(DruidConfig.class);

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean
    public ApolloDataSourceConfiguration apolloDataSourceConfiguration() {
        ApolloDataSourceConfiguration apolloDataSourceConfiguration = new ApolloDataSourceConfiguration();
        apolloDataSourceConfiguration.setDataSourceProperties(dataSourceProperties);
        return apolloDataSourceConfiguration;
    }

    @Bean
    public DruidDataSource druidDataSource() {
        ApolloDataSourceConfiguration apolloDataSourceConfiguration = apolloDataSourceConfiguration();
        return apolloDataSourceConfiguration.createDruidDataSource();
    }
}