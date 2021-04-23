package com.julianduru.fileuploader;


import com.julianduru.fileuploader.exception.UploaderException;

import java.io.File;

/**
 * created by julian
 */
public interface Uploader {


    void uploadFile(String containerName, String fileKey, File file) throws UploaderException;


}
