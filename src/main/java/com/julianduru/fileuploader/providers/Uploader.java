package com.julianduru.fileuploader.providers;


import com.julianduru.fileuploader.exception.UploaderException;
import com.julianduru.fileuploader.providers.UploadProvider;

import java.io.File;
import java.io.InputStream;

/**
 * created by julian
 */
public interface Uploader {


    void uploadFile(String containerName, String fileKey, File file) throws UploaderException;


    boolean containerExists(String containerName);


    InputStream downloadFile(String containerName, String fileKey) throws UploaderException;


    void deleteFile(String containerName, String fileKey) throws UploaderException;


    void deleteContainer(String containerName) throws UploaderException;


    void fullDelete(String containerName, String fileKey) throws UploaderException;


    UploadProvider provider();


}
