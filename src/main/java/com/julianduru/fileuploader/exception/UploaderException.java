package com.julianduru.fileuploader.exception;


import lombok.Data;

/**
 * created by julian
 */
@Data
public class UploaderException extends RuntimeException {


    public UploaderException() {
    }

    public UploaderException(String message) {
        super(message);
    }

    public UploaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public UploaderException(Throwable cause) {
        super(cause);
    }

    public UploaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
