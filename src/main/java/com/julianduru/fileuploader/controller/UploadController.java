package com.julianduru.fileuploader.controller;


import com.julianduru.fileuploader.Upload;
import com.julianduru.fileuploader.UploadRequest;
import com.julianduru.fileuploader.api.FileData;
import com.julianduru.fileuploader.api.FileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * created by julian
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(UploadController.PATH)
public class UploadController {


    public static final String PATH = "/upload";


    private final Upload upload;


    @Value("${file.uploader.container-name}")
    private String uploadContainerName;


    @Value("${file.uploader.file-key-prefix}")
    private String uploadFileKeyPrefix;



    @ResponseBody
    @GetMapping("/{reference}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String reference) {
        var fileUpload = upload.getFileUpload(reference);
        Resource file =  new InputStreamResource(upload.downloadFile(fileUpload));
        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileUpload.getOriginalFileName() + "\""
            ).body(file);
    }


    @PostMapping
    @ResponseBody
    public FileData uploadFile(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return upload.uploadFile(
            UploadRequest.builder()
                .containerName(uploadContainerName)
                .fileKey(uploadFileKeyPrefix + multipartFile.getOriginalFilename())
                .fileName(multipartFile.getOriginalFilename())
                .fileType(multipartFile.getContentType())
                .inputStream(multipartFile.getInputStream())
                .build()
        )
        .data();
    }


}



