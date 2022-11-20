package com.julianduru.fileuploader.providers.cloudinary;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * created by julian on 19/11/2022
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "file.uploader.cloudinary")
public class CloudinaryConfig {


    private String cloudName;


    private String apiKey;


    private String apiSecret;


}
