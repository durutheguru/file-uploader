package com.julianduru.fileuploader.providers.aws;


import com.julianduru.fileuploader.exception.UploaderException;
import com.julianduru.fileuploader.providers.UploadProvider;
import com.julianduru.fileuploader.providers.Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * created by julian
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AWSFileUploader implements Uploader {


    private final AWSConfig awsConfig;



    @Override
    public void uploadFile(String bucketName, String fileKey, File file) throws UploaderException {
        var region = Region.of(awsConfig.getDefaultRegion());

        try (var s3Client = initClient(region)) {
            setup(s3Client, bucketName, region);

            log.info("Uploading Object: {}:{}", bucketName, fileKey);

            s3Client.putObject(
                PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .build(),

                RequestBody.fromFile(file)
            );

            log.info("Upload Complete..");
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new UploaderException(e);
        }
    }


    @Override
    public boolean containerExists(String containerName) {
        try (var s3Client = initClient()){
            return bucketExists(containerName, s3Client);
        }
    }


    @Override
    public InputStream downloadFile(String bucketName, String fileKey) throws UploaderException {
        var s3Client = initClient();
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(fileKey)
            .build();

        return s3Client.getObject(getObjectRequest);
    }


    @Override
    public void deleteFile(String bucketName, String fileKey) throws UploaderException {
        try (var s3Client = initClient()) {
            deleteFile(s3Client, bucketName, fileKey);
        }
    }


    private void deleteFile(S3Client s3Client, String bucketName, String fileKey) throws UploaderException {
        log.info("Deleting {} from bucket {}...", fileKey, bucketName);

        try {
            var deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();

            s3Client.deleteObject(deleteObjectRequest);

            log.info("{} has been deleted.", fileKey);
        } catch (S3Exception e) {
            log.error(e.awsErrorDetails().errorMessage(), e);
            throw new UploaderException(e);
        }
    }


    @Override
    public void deleteContainer(String bucketName) throws UploaderException {
        try (var s3Client = initClient()) {
            deleteContainer(s3Client, bucketName);
        }
    }


    private void deleteContainer(S3Client s3Client, String bucketName) throws UploaderException {
        log.info("Deleting Bucket: {}", bucketName);

        try {
            var deleteBucketRequest = DeleteBucketRequest.builder()
                .bucket(bucketName)
                .build();

            s3Client.deleteBucket(deleteBucketRequest);

            log.info("{} has been deleted.", bucketName);
        } catch (S3Exception e) {
            log.error(e.awsErrorDetails().errorMessage(), e);
            throw new UploaderException(e);
        }
    }


    @Override
    public void fullDelete(String bucketName, String fileKey) throws UploaderException {
        try (var s3Client = initClient()) {
            deleteFile(s3Client, bucketName, fileKey);
            deleteContainer(s3Client, bucketName);
        }
    }


    @Override
    public UploadProvider provider() {
        return UploadProvider.AWS;
    }


    private synchronized void setup(S3Client s3Client, String bucketName, Region region) throws UploaderException {
        try {
            if (bucketExists(bucketName, s3Client)) {
                log.info("Bucket Name {} already exists", bucketName);
                return;
            }

            createBucket(s3Client, bucketName, region);
        } catch (S3Exception e) {
            log.error(e.awsErrorDetails().errorMessage(), e);
            throw new UploaderException(e);
        }
    }


    private S3Client initClient() {
        var region = Region.of(awsConfig.getDefaultRegion());
        return initClient(region);
    }


    private S3Client initClient(Region region) {
        var awsCredentials = AwsBasicCredentials.create(
            awsConfig.getAccessKeyId(),
            awsConfig.getSecretAccessKey()
        );

        return S3Client.builder()
            .region(region)
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .build();
    }


    private void createBucket(S3Client s3Client, String bucketName, Region region) {
        s3Client.createBucket(
            CreateBucketRequest.builder()
                .bucket(bucketName)
                .createBucketConfiguration(
                    CreateBucketConfiguration.builder()
                        .locationConstraint(region.id())
                        .build()
                ).build()
        );

        log.info("Creating bucket: {}", bucketName);

        s3Client
            .waiter()
            .waitUntilBucketExists(
                HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build()
            );

        log.info("{} is ready.", bucketName);
    }


    private boolean bucketExists(String bucketName, S3Client s3Client) {;
        try {
            s3Client.headBucket(
                HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build()
            );

            return true;
        } catch (NoSuchBucketException e) {
            return false;
        }
    }


    private List<Bucket> listBuckets(S3Client s3Client) {
        return s3Client.listBuckets().buckets();
    }


}




