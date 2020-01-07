package com.boristenelsen.FileUpload;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileModel {

    private final String filename;
    private final byte[] data;

}
