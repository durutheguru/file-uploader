package com.julianduru.fileuploader;


import com.julianduru.fileuploader.exception.UploaderException;
import com.julianduru.fileuploader.providers.UploadProvider;
import com.julianduru.fileuploader.providers.Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * created by julian
 */
@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {


    @Value("${file.uploader.default-provider}")
    private UploadProvider defaultUploadProvider;


    private final List<Uploader> uploaders;



    @Override
    public void uploadFile(String containerName, String fileKey, InputStream inputStream) throws UploaderException {
        uploadFile(defaultUploadProvider, containerName, fileKey, inputStream);
    }


    @Override
    public boolean containerExists(String containerName) {
        return containerExists(defaultUploadProvider, containerName);
    }


    @Override
    public InputStream downloadFile(String containerName, String fileKey) throws UploaderException {
        return downloadFile(defaultUploadProvider, containerName, fileKey);
    }


    @Override
    public void deleteFile(String containerName, String fileKey) throws UploaderException {
        deleteFile(defaultUploadProvider, containerName, fileKey);
    }


    @Override
    public void deleteContainer(String containerName) throws UploaderException {
        deleteContainer(defaultUploadProvider, containerName);
    }


    @Override
    public void deleteFileAndContainer(String containerName, String fileKey) throws UploaderException {
        deleteFileAndContainer(defaultUploadProvider, containerName, fileKey);
    }


    @Override
    public void uploadFile(UploadProvider provider, String containerName, String fileKey, InputStream inputStream) throws UploaderException {
        of(provider).uploadFile(containerName, fileKey, inputStream);
    }


    @Override
    public String generatePublicUrl(UploadProvider provider, String containerName, String fileKey) throws UploaderException {
        return of(provider).generateUrl(containerName, fileKey);
    }


    @Override
    public boolean containerExists(UploadProvider provider, String containerName) {
        return of(provider).containerExists(containerName);
    }


    @Override
    public InputStream downloadFile(UploadProvider provider, String containerName, String fileKey) throws UploaderException {
        return of(provider).downloadFile(containerName, fileKey);
    }


    @Override
    public void deleteFile(UploadProvider provider, String containerName, String fileKey) throws UploaderException {
        of(provider).deleteFile(containerName, fileKey);
    }


    @Override
    public void deleteContainer(UploadProvider provider, String containerName) throws UploaderException {
        of(provider).deleteContainer(containerName);
    }


    @Override
    public void deleteFileAndContainer(UploadProvider provider, String containerName, String fileKey) throws UploaderException {
        of(provider).fullDelete(containerName, fileKey);
    }


    private Uploader of(UploadProvider provider) {
        return uploaders
            .stream()
            .filter(u -> {
                var uProvider = u.provider();
                return uProvider != null && uProvider == provider;
            })
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Cannot find Upload Provider " + provider));
    }


}


