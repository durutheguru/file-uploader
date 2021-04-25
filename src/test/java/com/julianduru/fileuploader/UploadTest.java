package com.julianduru.fileuploader;


import com.github.javafaker.Faker;
import com.julianduru.fileuploader.config.TestDataSourceConfig;
import com.julianduru.fileuploader.providers.aws.AWSConfig;
import com.julianduru.fileuploader.repositories.FileUploadRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by julian
 */
@Import({TestDataSourceConfig.class})
public class UploadTest extends BaseServiceIntegrationTest {


    @Value("classpath:files/upload.txt")
    Resource resource;


    @Autowired
    AWSConfig awsConfig;


    @Autowired
    private Upload upload;


    @Autowired
    private FileUploadRepository fileUploadRepository;
    
    
    private Faker faker = new Faker();



    @Test
    public void testUploadingFile() throws Exception {
        String reference = faker.code().isbn13();

        upload.uploadFile(
            UploadRequest.builder()
                .reference(reference)
                .containerName(awsConfig.getExistingTestBucketName())
                .fileKey("upload/file-sample.txt")
                .fileName("file-sample.txt")
                .fileType("text/plain; charset=UTF-8")
                .inputStream(resource.getInputStream())
                .build()
        );

        assertThat(
            fileUploadRepository
                .existsByReference(reference)
        ).isTrue();
    }



}
