package com.boristenelsen.FileUpload;

import java.io.IOException;

public class FileStorageException extends RuntimeException {

    public FileStorageException(String s) {
        super(s);
    }
    public FileStorageException(String s, Throwable e) {
        super(s,e);
    }
}
