package com.julianduru.fileuploader.api;


import com.julianduru.fileuploader.UploadRequest;
import com.julianduru.fileuploader.providers.UploadProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * created by julian
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileUpload {


    private Long id;


    private ZonedDateTime timeAdded;


    private ZonedDateTime timeUpdated;


    private UploadProvider provider;


    private String containerName;


    private String fileKey;


    private String originalFileName;


    private String fileType;


    private String reference;


    private String publicUrl;


    private String metaData;



    public static FileUpload fromRequest(UploadRequest uploadRequest, UploadProvider defaultUploadProvider) {
        var upload = new FileUpload();
        
        upload.setContainerName(uploadRequest.getContainerName());
        upload.setFileKey(uploadRequest.getFileKey());
        upload.setReference(uploadRequest.getReference());
        upload.setMetaData(uploadRequest.getMetaData());
        upload.setOriginalFileName(uploadRequest.getFileName());
        upload.setFileType(uploadRequest.getFileType());
        
        if (uploadRequest.providerless()) {
            upload.setProvider(defaultUploadProvider);
        }
        else {
            upload.setProvider(uploadRequest.getProvider());
        }
        
        return upload;
    }
    
    

}
