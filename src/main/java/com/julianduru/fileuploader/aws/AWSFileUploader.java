package com.julianduru.fileuploader.aws;


import com.julianduru.fileuploader.Uploader;
import com.julianduru.fileuploader.exception.UploaderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.util.List;

/**
 * created by julian
 */
@Slf4j
@RequiredArgsConstructor
public class AWSFileUploader implements Uploader {


    private final AWSConfig awsConfig;



    public void uploadFile(String bucketName, String fileKey, File file) throws UploaderException {
        var region = Region.of(awsConfig.getDefaultRegion());

        try (var s3Client = S3Client.builder().region(region).build()) {
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


    public synchronized void setup(S3Client s3Client, String bucketName, Region region) throws UploaderException {
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


    private boolean bucketExists(String bucketName, S3Client s3Client) {
        return listBuckets(s3Client)
            .stream()
            .map(Bucket::name)
            .anyMatch(name -> name.equalsIgnoreCase(bucketName));
    }


    private List<Bucket> listBuckets(S3Client s3Client) {
        return s3Client.listBuckets().buckets();
    }


    public void fullCleanUp(S3Client s3Client, String bucketName, String fileKey) throws UploaderException {
        log.info("Deleting {} from bucket {}...", fileKey, bucketName);

        try {
            var deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();

            s3Client.deleteObject(deleteObjectRequest);

            log.info("{} has been deleted.", fileKey);

            var deleteBucketRequest = DeleteBucketRequest.builder()
                .bucket(bucketName)
                .build();

            s3Client.deleteBucket(deleteBucketRequest);

            log.info("{} has been deleted.", bucketName);
        } catch (S3Exception e) {
            log.error(e.awsErrorDetails().errorMessage(), e);
            throw new UploaderException(e);
        }

        log.info("Cleanup complete");
    }


}
