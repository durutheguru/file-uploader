package com.julianduru.fileuploader.entities;


import com.julianduru.fileuploader.UploadConstants;
import com.julianduru.fileuploader.UploadRequest;
import com.julianduru.fileuploader.providers.UploadProvider;
import com.julianduru.fileuploader.util.ZonedDateTimeConverter;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * created by julian
 */
@Data
@Entity
@Table(name = UploadConstants.TABLE_PREFIX + "file_upload")
public class FileUpload {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, updatable = false)
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime timeAdded;


    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime timeUpdated;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private UploadProvider provider;


    @Column(nullable = false, length = 200)
    private String containerName;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String fileKey;


    @Column(nullable = false, unique = true, length = 200)
    private String reference;


    @Column(columnDefinition = "TEXT")
    private String metaData;



    @PrePersist
    public void prePersist() {
        timeAdded = ZonedDateTime.now();
    }


    @PreUpdate
    public void preUpdate() {
        timeUpdated = ZonedDateTime.now();
    }

    
    public static FileUpload fromRequest(UploadRequest uploadRequest, UploadProvider defaultUploadProvider) {
        var upload = new FileUpload();
        
        upload.setContainerName(uploadRequest.getContainerName());
        upload.setFileKey(uploadRequest.getFileKey());
        upload.setReference(uploadRequest.getReference());
        upload.setMetaData(uploadRequest.getMetaData());
        
        if (uploadRequest.providerless()) {
            upload.setProvider(defaultUploadProvider);
        }
        else {
            upload.setProvider(uploadRequest.getProvider());
        }
        
        return upload;
    }
    
    

}
