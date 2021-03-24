package com.stock.soft.socksoft.Dto;

import java.io.ByteArrayOutputStream;
import org.springframework.http.MediaType;

public class DownloadFileDto {
    private ByteArrayOutputStream downloadInputStream;
    private MediaType contentType;

    public ByteArrayOutputStream getDownloadInputStream() {
        return downloadInputStream;
    }

    public void setDownloadInputStream(ByteArrayOutputStream downloadInputStream) {
        this.downloadInputStream = downloadInputStream;
    }

    public MediaType getContentType() {
        return contentType;
    }

    public void setContentType(MediaType contentType) {
        this.contentType = contentType;
    }
}
