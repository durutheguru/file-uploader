package com.julianduru.fileuploader;


import com.julianduru.fileuploader.providers.UploadProvider;
import lombok.*;

import java.io.File;
import java.io.InputStream;

/**
 * created by julian
 */
@Data
@Builder
public class UploadRequest {


    private String reference;


    private String containerName;


    private String fileKey;


    private String fileName;
    
    
    private String fileType;


    private InputStream inputStream;


    private UploadProvider provider;


    private String metaData;


    public boolean providerless() {
        return this.getProvider() == null;
    }


}

