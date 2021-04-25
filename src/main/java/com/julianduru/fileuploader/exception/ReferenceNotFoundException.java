package com.julianduru.fileuploader.exception;


import lombok.Data;

/**
 * created by julian
 */
@Data
public class ReferenceNotFoundException extends RuntimeException {


    public ReferenceNotFoundException() {
    }

    public ReferenceNotFoundException(String message) {
        super(message);
    }

    public ReferenceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReferenceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ReferenceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}


