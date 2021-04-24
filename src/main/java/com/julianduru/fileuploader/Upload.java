package com.julianduru.fileuploader;


import com.julianduru.fileuploader.entities.FileUpload;
import com.julianduru.fileuploader.exception.UploaderException;
import com.julianduru.fileuploader.providers.UploadProvider;
import com.julianduru.fileuploader.repositories.FileUploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * created by julian
 */
@Service
@RequiredArgsConstructor
public class Upload {


    @Value("${file.uploader.default-provider}")
    private UploadProvider defaultUploadProvider;


    private final FileUploadRepository uploadRepository;


    private final FileUploadService uploadService;



    public void uploadFile(UploadRequest uploadRequest) throws UploaderException {
        var fileUpload = FileUpload.fromRequest(uploadRequest, defaultUploadProvider);
        
        uploadService.uploadFile(
            fileUpload.getProvider(),
            fileUpload.getContainerName(),
            fileUpload.getFileKey(),
            uploadRequest.getFile()
        );
        
        uploadRepository.save(fileUpload);
    }


    public boolean containerExists(String containerName) {
        return uploadService.containerExists(containerName);
    }
    
    
    public FileUpload getFileUpload(String reference) {
        return uploadRepository
            .findByReference(reference)
            .orElseThrow(() -> new UploaderException(
                String.format("Upload Ref %s not found", reference)
            ));
    }


    public InputStream downloadFile(String reference) throws UploaderException {
        var fileUpload = getFileUpload(reference);
        return uploadService.downloadFile(
            fileUpload.getProvider(), fileUpload.getContainerName(), fileUpload.getFileKey()
        );
    }


    public void deleteFile(String reference) throws UploaderException {
        var fileUpload = getFileUpload(reference);
        uploadService.deleteFile(
            fileUpload.getProvider(), fileUpload.getContainerName(), fileUpload.getFileKey()
        );
    }


    public void deleteContainer(String containerName) throws UploaderException {
        uploadService.deleteContainer(containerName);
    }


    public void deleteFileAndContainer(String containerName, String fileKey) throws UploaderException {
        uploadService.deleteFileAndContainer(containerName, fileKey);
    }


    public boolean containerExists(UploadProvider provider, String containerName) {
        return uploadService.containerExists(provider, containerName);
    }


    public void deleteContainer(UploadProvider provider, String containerName) throws UploaderException {
        uploadService.deleteContainer(provider, containerName);
    }


    public void deleteFileAndContainer(UploadProvider provider, String containerName, String fileKey) throws UploaderException {
        uploadService.deleteFileAndContainer(provider, containerName, fileKey);
    }


}

