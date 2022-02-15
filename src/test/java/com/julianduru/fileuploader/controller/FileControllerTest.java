package com.julianduru.fileuploader.controller;

import com.julianduru.fileuploader.BaseControllerTest;
import com.julianduru.fileuploader.data.FileUploadProvider;
import com.julianduru.fileuploader.entities.FileUpload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * created by julian on 15/02/2022
 */
public class FileControllerTest extends BaseControllerTest {

    @Autowired
    private FileUploadProvider uploadProvider;


    @Test
    public void testFetchingFileData() throws Exception {
        var refs = uploadProvider.save(5)
            .stream().map(FileUpload::getReference)
            .collect(Collectors.toList());

        mockMvc.perform(
            get(FileController.PATH)
                .param("ref", refs.toArray(new String[]{}))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
            .andExpect(status().is2xxSuccessful());
    }

}
