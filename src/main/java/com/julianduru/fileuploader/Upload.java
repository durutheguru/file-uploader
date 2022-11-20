package com.julianduru.fileuploader;


import com.julianduru.fileuploader.api.FileData;
import com.julianduru.fileuploader.api.FileUpload;
import com.julianduru.fileuploader.exception.ReferenceNotFoundException;
import com.julianduru.fileuploader.exception.UploaderException;
import com.julianduru.fileuploader.providers.UploadProvider;
import com.julianduru.fileuploader.repositories.FileUploadRepository;
import com.julianduru.fileuploader.util.ReferenceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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


    private final ReferenceGenerator referenceGenerator;



    public String uploadFileForRef(UploadRequest uploadRequest) throws UploaderException {
        var reference = referenceGenerator.generate();

        uploadRequest.setReference(reference);
        uploadFile(uploadRequest);

        return reference;
    }


    public void uploadFile(UploadRequest uploadRequest) throws UploaderException {
        if (uploadRepository.existsByReference(uploadRequest.getReference())) {
            throw new IllegalArgumentException(
                String.format("File Upload Ref %s already exists", uploadRequest.getReference())
            );
        }

        var fileUpload = FileUpload.fromRequest(uploadRequest, defaultUploadProvider);
        
        var uploadResponse = uploadService.uploadFile(
            fileUpload.getProvider(),
            fileUpload.getContainerName(),
            fileUpload.getFileKey(),
            uploadRequest.getInputStream()
        );
        fileUpload.setPublicUrl(uploadResponse.getPublicUrl());
        
        uploadRepository.save(fileUpload);
    }


    public boolean containerExists(String containerName) {
        return uploadService.containerExists(containerName);
    }
    
    
    public FileUpload getFileUpload(String reference) {
        return uploadRepository
            .findByReference(reference)
            .orElseThrow(() -> new ReferenceNotFoundException(
                String.format("Upload Ref %s not found", reference)
            ));
    }


    public List<FileData> getFileData(Collection<String> references) {
        return uploadRepository.findByReferenceIn(references)
            .stream().map(u -> FileData.builder()
                .reference(u.getReference())
                .originalFileName(u.getOriginalFileName())
                .fileType(u.getFileType())
                .publicUrl(u.getPublicUrl())
                .metaData(u.getMetaData())
                .build())
            .collect(Collectors.toList());
    }


    public InputStream downloadFile(String reference) throws UploaderException {
        var fileUpload = getFileUpload(reference);
        return downloadFile(fileUpload);
    }


    public InputStream downloadFile(FileUpload fileUpload) throws UploaderException {
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



