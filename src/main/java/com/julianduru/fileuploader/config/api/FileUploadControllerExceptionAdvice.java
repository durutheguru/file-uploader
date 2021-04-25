package com.julianduru.fileuploader.config.api;


import com.julianduru.fileuploader.exception.ReferenceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * created by julian
 */
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FileUploadControllerExceptionAdvice {


    @ExceptionHandler({ReferenceNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> handleReferenceNotFoundException(ReferenceNotFoundException e) {
        log.error("Controller Exception: " + e.getMessage(), e);
        return new ResponseEntity<>(new ApiErrorResponse(e), HttpStatus.NOT_FOUND);
    }


}



