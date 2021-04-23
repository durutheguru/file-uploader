package com.julianduru.fileuploader.aws;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import software.amazon.awssdk.regions.Region;

/**
 * created by julian
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "file.uploader.aws")
public class AWSConfig {


    private String defaultRegion = Region.CA_CENTRAL_1.id();


}
