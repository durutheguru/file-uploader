package com.julianduru.fileuploader.providers.cloudinary;

import com.cloudinary.Cloudinary;
import com.julianduru.fileuploader.api.UploadResponse;
import com.julianduru.fileuploader.exception.UploaderException;
import com.julianduru.fileuploader.providers.UploadProvider;
import com.julianduru.fileuploader.providers.Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;

/**
 * created by julian on 18/11/2022
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CloudinaryFileUploader implements Uploader {


    private final CloudinaryConfig cloudinaryConfig;


    @Override
    public UploadResponse uploadFile(String containerName, String fileKey, InputStream inputStream) throws UploaderException {
        try {
            var cloudinary = initCloudinary();

            log.info("Uploading Object to {}: {}:{}", provider(), containerName, fileKey);

            var params = Map.of(
                "public_id", containerName + "/" + fileKey,
                "overwrite", true
            );
            var response = cloudinary.uploader().upload(IOUtils.toByteArray(inputStream), params);

            log.info("Upload Complete...");

            return UploadResponse.builder()
                .publicUrl(response.get("secure_url").toString())
                .build();
        }
        catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new UploaderException(e);
        }
    }


    @Override
    public boolean containerExists(String containerName) {
        return false;
    }


    @Override
    public InputStream downloadFile(String containerName, String fileKey) throws UploaderException {
        try {
            var cloudinary = initCloudinary();

            log.info("Downloading Object from {}: {}:{}", provider(), containerName, fileKey);

            var publicId = containerName + "/" + fileKey;
            var response = cloudinary.api().resource(publicId, Map.of());

            log.info("Fetched Resource details...");

            return new URL(response.get("url").toString()).openStream();
        }
        catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new UploaderException(e);
        }
    }


    @Override
    public void deleteFile(String containerName, String fileKey) throws UploaderException {
        try {
            var cloudinary = initCloudinary();

            log.info("Deleting Object from {}: {}:{}", provider(), containerName, fileKey);

            var publicId = containerName + "/" + fileKey;
            var response = cloudinary.uploader().destroy(publicId, Map.of());

            log.info("Delete Complete...");
        }
        catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new UploaderException(e);
        }
    }


    @Override
    public void deleteContainer(String containerName) throws UploaderException {
        try {
            var cloudinary = initCloudinary();

            log.info("Deleting Folder from {}: {}", provider(), containerName);

            var response = cloudinary.api().deleteFolder(containerName, Map.of());

            log.info("Delete Complete...");
        }
        catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new UploaderException(e);
        }
    }


    @Override
    public void fullDelete(String containerName, String fileKey) throws UploaderException {
        deleteFile(containerName, fileKey);
        deleteContainer(containerName);
    }


    @Override
    public String generateUrl(String bucketName, String fileKey) {
        return null;
    }


    @Override
    public UploadProvider provider() {
        return UploadProvider.CLOUDINARY;
    }


    private Cloudinary initCloudinary() {
        return new Cloudinary(
            Map.of(
                "cloud_name", cloudinaryConfig.getCloudName(),
                "api_key", cloudinaryConfig.getApiKey(),
                "api_secret", cloudinaryConfig.getApiSecret()
            )
        );
    }


}
