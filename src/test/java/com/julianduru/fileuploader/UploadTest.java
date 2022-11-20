package com.julianduru.fileuploader;


import com.github.javafaker.Faker;
import com.julianduru.fileuploader.api.FileUpload;
import com.julianduru.fileuploader.providers.aws.AWSConfig;
import com.julianduru.fileuploader.repositories.FileUploadRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * created by julian
 */
public class UploadTest extends BaseServiceIntegrationTest {


    @Value("classpath:files/upload.txt")
    Resource resource;


    @Autowired
    AWSConfig awsConfig;


    @Autowired
    private Upload upload;


    @SpyBean
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

        verify(fileUploadRepository).save(Mockito.any(FileUpload.class));
    }



}
