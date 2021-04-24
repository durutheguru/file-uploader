package com.julianduru.fileuploader;


import com.julianduru.fileuploader.providers.UploadProvider;
import lombok.*;

import java.io.File;

/**
 * created by julian
 */
@Data
@Builder
public class UploadRequest {


    private String reference;


    private String containerName;


    private String fileKey;


    private File file;


    private UploadProvider provider;


    private String metaData;


    public boolean providerless() {
        return this.getProvider() == null;
    }


}

