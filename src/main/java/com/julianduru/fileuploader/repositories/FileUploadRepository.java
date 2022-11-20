package com.julianduru.fileuploader.repositories;


import com.julianduru.fileuploader.api.FileUpload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * created by julian
 */
public interface FileUploadRepository  {


    FileUpload save(FileUpload upload);


    Page<FileUpload> findAll(Pageable pageable);


    Optional<FileUpload> findByReference(String reference);


    boolean existsByReference(String reference);


    List<FileUpload> findByReferenceIn(Collection<String> references);


}
