package com.fisa.study.management.global.config;

import com.fisa.study.management.global.argumentresolver.LoginArgumentResolver;
import com.fisa.study.management.global.interceptor.LoginCheckInterceptor;
import com.fisa.study.management.global.interceptor.UUIDInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/rooms/**", "/room",
                        "/room/**/snapshot/register",
                        "/room/**/checkup/register",
                        "/room/**/checkup/get");
        registry.addInterceptor(new UUIDInterceptor())
                .order(2)
                .addPathPatterns("/room/*/**");
    }

}
