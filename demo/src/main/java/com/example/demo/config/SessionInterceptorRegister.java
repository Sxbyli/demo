package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SessionInterceptorRegister implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor getInterceptor() {
        return new SessionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        List<String> pathPatterns = new ArrayList<>();
        pathPatterns.add("/update");
        registry.addInterceptor(getInterceptor()).addPathPatterns(pathPatterns);
    }
}
