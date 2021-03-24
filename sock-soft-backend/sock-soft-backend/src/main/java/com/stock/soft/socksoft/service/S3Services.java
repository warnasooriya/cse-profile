package com.stock.soft.socksoft.service;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.stock.soft.socksoft.Dto.DownloadFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;

public interface S3Services {
    public DownloadFileDto downloadFile(String keyName);

    public String uploadFile(String keyName, MultipartFile file);
}
