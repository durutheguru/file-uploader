package com.julianduru.fileuploader;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * created by julian on 20/11/2022
 */
@Configuration
@ComponentScan(
    basePackages = {
        "com.julianduru.fileuploader",
    }
)
@EnableConfigurationProperties
public class FileUploaderConfig {
}
