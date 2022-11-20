package com.julianduru.fileuploader.providers.aws;


import com.julianduru.fileuploader.BaseServiceIntegrationTest;
import com.julianduru.fileuploader.config.TestDataSourceConfig;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * created by julian
 */
public class AWSFileUploaderTest extends BaseServiceIntegrationTest {


    @Value("classpath:files/upload.txt")
    Resource uploadResource;


    @Autowired
    AWSFileUploader fileUploader;


    @Autowired
    AWSConfig awsConfig;


    @Test
    public void testAWSUploaderMethods() throws Exception {
        var bucketName = "test-bucket-0x09" + System.currentTimeMillis();
        var fileKey = "upload/file-samples";

        var response = fileUploader.uploadFile(
            bucketName, fileKey, uploadResource.getInputStream()
        );
        
        var fileStream = fileUploader.downloadFile(bucketName, fileKey);
        var outStream = new ByteArrayOutputStream();
        
        IOUtils.copy(fileStream, outStream);

        var fileContent = new String(outStream.toByteArray());
        assertThat(fileContent).isNotBlank();

        fileUploader.fullDelete(bucketName, fileKey);

        assertThrows(Throwable.class, () -> fileUploader.downloadFile(bucketName, fileKey));
    }


    @Test
    public void testBucketExistsReturnsTrueWhenExisting() throws Exception {
        assertThat(
            fileUploader.containerExists(awsConfig.getExistingTestBucketName())
        ).isTrue();
    }


    @Test
    public void testBucketExistsReturnsFalseWhenNotExisting() throws Exception {
        assertThat(
            fileUploader.containerExists(
                "non_existent_bucket_00x0" + System.currentTimeMillis()
            )
        ).isFalse();
    }


}
