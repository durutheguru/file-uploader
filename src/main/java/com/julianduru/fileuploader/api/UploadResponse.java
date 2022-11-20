package com.julianduru.fileuploader.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by julian on 19/11/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse {


    private String publicUrl;


}
