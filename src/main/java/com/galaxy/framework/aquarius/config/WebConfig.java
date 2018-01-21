package com.galaxy.framework.aquarius.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Properties;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Properties props = System.getProperties();
        registry.addResourceHandler("/ext/**").addResourceLocations("file:///" + props.getProperty("user.home") + "/resources/");
    }
}
