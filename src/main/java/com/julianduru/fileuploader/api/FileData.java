package com.julianduru.fileuploader.api;


import lombok.*;

/**
 * created by julian
 */
@Data
@Builder
public class FileData {


    private String reference;


    private String originalFileName;


    private String fileType;


    private String publicUrl;


    private String metaData;


}


