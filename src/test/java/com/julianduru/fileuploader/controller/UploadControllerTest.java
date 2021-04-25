package com.julianduru.fileuploader.controller;


import com.julianduru.fileuploader.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.hasSize;
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


    @Test
    public void testDownloadingFile() throws Exception {
        var uploadRef = testUploadingFile();

        mockMvc.perform(
            get("/upload/" + uploadRef)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
    }


}


