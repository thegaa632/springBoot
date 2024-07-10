package com.boot.s1.dto.upload;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
public class MultipartConfig {
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Value("${file.multipart.maxUploadSize:10485760}")
    private long maxUploadSize;

    @Value("${file.multipart.maxUploadSizePerFile:10485760}")
    private long maxUploadSizePerFile;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxRequestSize(DataSize.ofBytes(maxUploadSize));
        factory.setMaxFileSize(DataSize.ofBytes(maxUploadSizePerFile));


        return factory.createMultipartConfig();
    }
}