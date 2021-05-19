package com.besome.sketch.beans;

import com.google.common.net.MediaType;

import a.a.a.nA;

public class UploadFileBean extends nA {
    public String contentType;
    public String fileName;
    public String path;

    public UploadFileBean(String str, String str2, String str3) {
        this.contentType = str;
        this.fileName = str2;
        this.path = str3;
    }

    public UploadFileBean(MediaType mediaType, String str, String str2) {
        this(mediaType.toString(), str, str2);
    }
}
