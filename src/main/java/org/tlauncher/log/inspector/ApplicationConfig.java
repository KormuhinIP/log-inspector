package org.tlauncher.log.inspector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/*
 * Class to configure core application classes
 */

@Configuration
@EnableAsync
@EnableScheduling
public class ApplicationConfig {

    @Value("${max.file.upload}")
    private int MAX_UPLOAD_SIZE;

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
        return new CommonsMultipartResolver();
    }

}
