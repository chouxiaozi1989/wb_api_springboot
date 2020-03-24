package com.caiyi.oauth.config;

import com.caiyi.oauth.interceptors.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    private static final String[] excludePathPatterns = {"/api/token/api_token"};
    private static final String[] logPathPatterns = {"/user/**"};
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/api/**")
                .addPathPatterns(logPathPatterns)
                .excludePathPatterns(excludePathPatterns);
    }
}
