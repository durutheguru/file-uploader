package com.julianduru.fileuploader.repositories;

import com.julianduru.fileuploader.api.FileUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * created by julian on 20/11/2022
 */
@Slf4j
@Component
@ConditionalOnMissingBean(type = "FileUploadRepository")
public class DefaultFileUploadRepositoryImpl implements FileUploadRepository {


    @Override
    public FileUpload save(FileUpload upload) {
        log.info("Saving File Upload to repository.");
        return upload;
    }

    @Override
    public Page<FileUpload> findAll(Pageable pageable) {
        return Page.empty(pageable);
    }

    @Override
    public Optional<FileUpload> findByReference(String reference) {
        return Optional.empty();
    }

    @Override
    public boolean existsByReference(String reference) {
        return false;
    }

    @Override
    public List<FileUpload> findByReferenceIn(Collection<String> references) {
        return Collections.emptyList();
    }


}
