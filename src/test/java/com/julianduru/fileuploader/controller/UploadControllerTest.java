package com.julianduru.fileuploader.controller;


import com.julianduru.fileuploader.BaseControllerTest;
import com.julianduru.fileuploader.Upload;
import com.julianduru.fileuploader.api.FileUpload;
import com.julianduru.fileuploader.repositories.FileUploadRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * created by julian
 */

public class UploadControllerTest extends BaseControllerTest {


    @Value("classpath:files/upload.txt")
    Resource uploadResource;


    @SpyBean
    private FileUploadRepository fileUploadRepository;


    @MockBean
    private Upload upload;



    @Test
    public String testUploadingFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile(
            "file", "upload.txt", "text/plain", uploadResource.getInputStream()
        );

        var actions = mockMvc.perform(
            multipart("/upload").file(multipartFile)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());

        return actions.andReturn().getResponse().getContentAsString();
    }


}



