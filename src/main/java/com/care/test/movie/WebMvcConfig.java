package com.care.test.movie;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new org.springframework.http.converter.json.MappingJackson2HttpMessageConverter());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.forEach(converter -> {
            if (converter instanceof org.springframework.http.converter.json.MappingJackson2HttpMessageConverter) {
                org.springframework.http.converter.json.MappingJackson2HttpMessageConverter jsonConverter =
                        (org.springframework.http.converter.json.MappingJackson2HttpMessageConverter) converter;
                jsonConverter.getObjectMapper().registerModule(new com.fasterxml.jackson.databind.module.SimpleModule()
                        .addSerializer(new ByteArrayResourceSerializer()));
            }
        });
    }
}
