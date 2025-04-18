package com.brandingku.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.base.url}")
    private String baseUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (baseUrl.contains("localhost")) {
            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("classpath:/static/uploads/");
        } else {
            // Serve from the filesystem
            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("file:/opt/byc/uploads/");
        }

        if (baseUrl.contains("localhost")) {
            registry.addResourceHandler("/chats/**")
                    .addResourceLocations("classpath:/static/chats/");
        } else {
            // Serve from the filesystem
            registry.addResourceHandler("/chats/**")
                    .addResourceLocations("file:/opt/byc/chats/");
        }

        if (baseUrl.contains("localhost")) {
            registry.addResourceHandler("/.well-known/**")
                    .addResourceLocations("classpath:/static/uploads/config/");
        } else {
            // Serve from the filesystem
            registry.addResourceHandler("/.well-known/**")
                    .addResourceLocations("file:/opt/byc/uploads/config/");
        }






    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("https://admin-byc2024.kelolain.id")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
}
