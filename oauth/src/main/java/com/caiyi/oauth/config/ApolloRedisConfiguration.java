package com.caiyi.oauth.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;

public class ApolloRedisConfiguration {
    private final Logger log = LoggerFactory.getLogger(ApolloRedisConfiguration.class);

    private RedisProperties redisProperties;

    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * apollo 注解监听配置信息更新
     */
    @ApolloConfigChangeListener
    private void onChange(ConfigChangeEvent changeEvent) {
        boolean flag = false;
        if (changeEvent.isChanged("spring.redis.database")) {
            redisProperties.setDatabase(changeEvent.getChange("spring.redis.database").getNewValue());
            flag = true;
        }
        if (changeEvent.isChanged("spring.redis.host")) {
            redisProperties.setHost(changeEvent.getChange("spring.redis.host").getNewValue());
            flag = true;
        }
        if (changeEvent.isChanged("spring.redis.port")) {
            redisProperties.setPort(changeEvent.getChange("spring.redis.port").getNewValue());
            flag = true;
        }
        if (changeEvent.isChanged("spring.redis.timeout")) {
            redisProperties.setTimeout(changeEvent.getChange("spring.redis.timeout").getNewValue());
            flag = true;
        }
        if (changeEvent.isChanged("spring.redis.maxActive")) {
            redisProperties.setMaxActive(changeEvent.getChange("spring.redis.maxActive").getNewValue());
            flag = true;
        }
        if (changeEvent.isChanged("spring.redis.maxWait")) {
            redisProperties.setMaxWait(changeEvent.getChange("spring.redis.maxWait").getNewValue());
            flag = true;
        }
        if (changeEvent.isChanged("spring.redis.maxIdle")) {
            redisProperties.setMaxIdle(changeEvent.getChange("spring.redis.maxIdle").getNewValue());
            flag = true;
        }
        if (changeEvent.isChanged("spring.redis.minIdle")) {
            redisProperties.setMinIdle(changeEvent.getChange("spring.redis.minIdle").getNewValue());
            flag = true;
        }
        // 对dataSource更新
        if (flag) {
            lettuceConnectionFactory = createLettuceConnectionFactory();
        }
    }

    public LettuceConnectionFactory createLettuceConnectionFactory() {
        // 单机版配置
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(Integer.parseInt(redisProperties.getDatabase()));
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setPort(Integer.parseInt(redisProperties.getPort()));
//        redisStandaloneConfiguration.setPassword();

        // 集群版配置
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//        String[] serverArray = clusterNodes.split(",");
//        Set<RedisNode> nodes = new HashSet<RedisNode>();
//        for (String ipPort : serverArray) {
//            String[] ipAndPort = ipPort.split(":");
//            nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
//        }
//        redisClusterConfiguration.setPassword(RedisPassword.of(password));
//        redisClusterConfiguration.setClusterNodes(nodes);
//        redisClusterConfiguration.setMaxRedirects(maxRedirects);

        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(Integer.parseInt(redisProperties.getMaxIdle()));
        genericObjectPoolConfig.setMinIdle(Integer.parseInt(redisProperties.getMinIdle()));
        genericObjectPoolConfig.setMaxTotal(Integer.parseInt(redisProperties.getMaxActive()));
        genericObjectPoolConfig.setMaxWaitMillis(Integer.parseInt(redisProperties.getMaxWait()));

        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(Integer.parseInt(redisProperties.getTimeout())))
                .poolConfig(genericObjectPoolConfig)
                .build();

        this.lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
        return this.lettuceConnectionFactory;
    }

    public void setRedisProperties(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }
}
