package com.julianduru.fileuploader.controller;

import com.julianduru.fileuploader.Upload;
import com.julianduru.fileuploader.api.FileData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * created by julian on 15/02/2022
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(FileController.PATH)
public class FileController {

    public final static String PATH = "/_files";


    private final Upload upload;


    @GetMapping
    public List<FileData> getFileData(@RequestParam("ref") List<String> references) {
        return upload.getFileData(references);
    }


}
