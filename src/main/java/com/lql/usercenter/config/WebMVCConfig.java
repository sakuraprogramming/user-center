package com.lql.usercenter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\front\\";
//        registry.addResourceHandler("/front/**")
//                .addResourceLocations("classpath:/front/");
        registry.addResourceHandler("/front/**")
                .addResourceLocations("file:" + path);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
