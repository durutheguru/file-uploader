package com.julianduru.fileuploader.config.api;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * created by julian
 */
public class ApiErrorResponse extends ApiResponse<String> {


    @JsonIgnore
    private Exception exception;


    public ApiErrorResponse() {
        super(Status.ERROR, null);
    }


    public ApiErrorResponse(String message) {
        super(Status.ERROR, message);
    }


    public ApiErrorResponse(String message, String data) {
        super(Status.ERROR, message, data);
    }


    public ApiErrorResponse(Exception exception) {
        super(Status.ERROR, ApiBodySanitizer.sanitizeMessage(exception));
        this.exception = exception;
    }


}


