package com.julianduru.fileuploader.providers.cloudinary;

import com.julianduru.fileuploader.BaseServiceIntegrationTest;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * created by julian on 19/11/2022
 */
public class CloudinaryFileUploaderTest extends BaseServiceIntegrationTest {


    @Value("classpath:files/photo.jpg")
    Resource uploadResource;


    @Autowired
    CloudinaryFileUploader fileUploader;


    @Autowired
    CloudinaryConfig cloudinaryConfig;


    @Test
    public void testCloudinaryUploaderMethods() throws Exception {
        var containerName = "test-container-0x09" + System.currentTimeMillis();
        var fileKey = "upload/file-samples";

        var response = fileUploader.uploadFile(
            containerName, fileKey, uploadResource.getInputStream()
        );

        assertThat(response).isNotNull();
        assertThat(response.getPublicUrl()).isNotBlank();

        var fileStream = fileUploader.downloadFile(containerName, fileKey);
        var outStream = new ByteArrayOutputStream();

        IOUtils.copy(fileStream, outStream);

        var fileContent = outStream.toString();
        assertThat(fileContent).isNotBlank();

        fileUploader.fullDelete(containerName, fileKey);

        assertThrows(Throwable.class, () -> fileUploader.downloadFile(containerName, fileKey));
    }


}
