package com.caiyi.oauth.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ApolloDataSourceConfiguration {
    private final Logger log = LoggerFactory.getLogger(ApolloDataSourceConfiguration.class);

    private DataSourceProperties dataSourceProperties;

    private DruidDataSource dataSource;

    /**
     * apollo 注解监听配置信息更新
     */
    @ApolloConfigChangeListener
    private void onChange(ConfigChangeEvent changeEvent) {
        boolean flag = false;
        if (changeEvent.isChanged("spring.datasource.type")) {
            dataSourceProperties.setUrl(changeEvent.getChange("spring.datasource.type").getNewValue());
            flag = true;
        }
        if (changeEvent.isChanged("spring.datasource.url")) {
            dataSourceProperties.setUrl(changeEvent.getChange("spring.datasource.url").getNewValue());
            flag = true;
        }
        if (changeEvent.isChanged("spring.datasource.driver-class-name")) {
            dataSourceProperties.setDriverClassName(changeEvent.getChange("spring.datasource.driver-class-name").getNewValue());
            flag = true;
        }
        if (changeEvent.isChanged("spring.datasource.username")) {
            dataSourceProperties.setUsername(changeEvent.getChange("spring.datasource.username").getNewValue());
            flag = true;
        }
        if (changeEvent.isChanged("spring.datasource.password")) {
            dataSourceProperties.setPassword(changeEvent.getChange("spring.datasource.password").getNewValue());
            flag = true;
        }
        if (changeEvent.isChanged("spring.datasource.validationQuery")) {
            dataSourceProperties.setPassword(changeEvent.getChange("spring.datasource.validationQuery").getNewValue());
            flag = true;
        }
        // 对dataSource更新
        if (flag) {
            dataSource = createDruidDataSource();
        }
    }

    /**
     * 创建DruidDataSource
     */
    public DruidDataSource createDruidDataSource() {
        log.warn(String.format("apollo configuration = datasource connection factory driver-class-name = %s, " +
                        "url = %s ,username = %s ,password = %s",
                dataSourceProperties.getDriverClassName(), dataSourceProperties.getUrl(),
                dataSourceProperties.getUsername(), dataSourceProperties.getPassword()));
        // 判断是否存在dataSource
        if (dataSource == null) {
            dataSource = new DruidDataSource();
        } // 重启dataSource
        try {
            dataSource.restart();
        } catch (SQLException e) {
            dataSource = new DruidDataSource();
        }
        // 设置配置信息
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setValidationQuery(dataSourceProperties.getValidationQuery());

        return dataSource;
    }

    public void setDataSourceProperties(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }
}
