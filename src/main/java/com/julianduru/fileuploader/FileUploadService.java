package com.julianduru.fileuploader;


import com.julianduru.fileuploader.exception.UploaderException;
import com.julianduru.fileuploader.providers.UploadProvider;

import java.io.File;
import java.io.InputStream;

/**
 * created by julian
 */
public interface FileUploadService {


    void uploadFile(String containerName, String fileKey, File file) throws UploaderException;


    boolean containerExists(String containerName);


    InputStream downloadFile(String containerName, String fileKey) throws UploaderException;


    void deleteFile(String containerName, String fileKey) throws UploaderException;


    void deleteContainer(String containerName) throws UploaderException;


    void deleteFileAndContainer(String containerName, String fileKey) throws UploaderException;


    void uploadFile(UploadProvider provider, String containerName, String fileKey, File file) throws UploaderException;


    boolean containerExists(UploadProvider provider, String containerName);


    InputStream downloadFile(UploadProvider provider, String containerName, String fileKey) throws UploaderException;


    void deleteFile(UploadProvider provider, String containerName, String fileKey) throws UploaderException;


    void deleteContainer(UploadProvider provider, String containerName) throws UploaderException;


    void deleteFileAndContainer(UploadProvider provider, String containerName, String fileKey) throws UploaderException;


}
