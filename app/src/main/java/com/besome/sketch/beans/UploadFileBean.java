package com.besome.sketch.beans;

import com.google.common.net.MediaType;

import a.a.a.nA;

public class UploadFileBean extends nA {

    public String contentType;
    public String fileName;
    public String path;

    public UploadFileBean(String contentType, String filename, String path) {
        this.contentType = contentType;
        this.fileName = filename;
        this.path = path;
    }

    public UploadFileBean(MediaType contentType, String filename, String path) {
        this(contentType.toString(), filename, path);
    }
}
