package com.julianduru.fileuploader.data;

import com.julianduru.fileuploader.api.FileUpload;
import com.julianduru.fileuploader.providers.UploadProvider;
import com.julianduru.fileuploader.repositories.FileUploadRepository;
import com.julianduru.util.test.DataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

/**
 * created by julian on 15/02/2022
 */
@Component
@RequiredArgsConstructor
public class FileUploadProvider implements DataProvider<FileUpload> {


    private final FileUploadRepository fileUploadRepository;


    @Override
    public FileUpload provide() {
        var upload = new FileUpload();

        upload.setProvider(UploadProvider.AWS);
        upload.setFileKey(faker.code().isbn10());
        upload.setFileType(MimeTypeUtils.APPLICATION_JSON.toString());
        upload.setReference(faker.code().isbn13(false));
        upload.setOriginalFileName(faker.file().fileName());
        upload.setContainerName(faker.code().asin());
        upload.setPublicUrl(faker.internet().url());

        return upload;
    }


}
