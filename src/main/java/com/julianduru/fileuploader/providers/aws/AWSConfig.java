package com.julianduru.fileuploader.providers.aws;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import software.amazon.awssdk.regions.Region;

import javax.validation.constraints.NotEmpty;

/**
 * created by julian
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "file.uploader.aws")
@EnableConfigurationProperties
public class AWSConfig {


    private String defaultRegion = Region.CA_CENTRAL_1.id();


    @NotEmpty(message = "Access Key ID is required")
    private String accessKeyId;


    @NotEmpty(message = "Secret Access Key is required")
    private String secretAccessKey;


    private String existingTestBucketName;


}

