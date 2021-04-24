package com.julianduru.fileuploader.repositories;


import com.julianduru.fileuploader.entities.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * created by julian
 */
@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long>  {


    Optional<FileUpload> findByReference(String reference);


}
