package org.qk.pyq.water.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

@Component
public class WebConfiguration extends WebMvcConfigurationSupport {
    @Resource
    private RequestFilter requestFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 跨域拦截器
        registry.addInterceptor(requestFilter).addPathPatterns("/**");
    }
}
