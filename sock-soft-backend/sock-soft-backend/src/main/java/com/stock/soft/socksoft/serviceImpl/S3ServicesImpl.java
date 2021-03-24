package com.stock.soft.socksoft.serviceImpl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.stock.soft.socksoft.Dto.DownloadFileDto;
import com.stock.soft.socksoft.controller.CompanyController;
import com.stock.soft.socksoft.service.S3Services;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.logging.Level;

@Service
public class S3ServicesImpl implements S3Services {


    private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CompanyController.class.getName());
    @Autowired
    private AmazonS3 s3client;

    @Value("${cse.profile.s3.bucket}")
    private String bucketName;

    @Override
    public DownloadFileDto downloadFile(String keyName) {
        DownloadFileDto downloadFileDto = new DownloadFileDto();
        try {
            System.out.println("Downloading an object.");
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
            System.out.println("Content-Type: " + s3object.getObjectMetadata().getContentType());
            InputStream is = s3object.getObjectContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }
            downloadFileDto.setDownloadInputStream(baos);
            downloadFileDto.setContentType(MediaType.valueOf(s3object.getObjectMetadata().getContentType()));
            logger.info("===================== Import File - Done! =====================");
        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  downloadFileDto;
    }

    @Override
    public String uploadFile(String keyName,  MultipartFile file) {
        PutObjectResult putObjectRequest = null;
        try {
            ObjectMetadata objMetadata = new ObjectMetadata();
            objMetadata.setContentLength(15L);

           putObjectRequest = s3client.putObject(new PutObjectRequest(bucketName, keyName, file.getInputStream(), objMetadata).withCannedAcl(CannedAccessControlList.Private));
        } catch (AmazonServiceException ase) {

            logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {

            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        } catch (FileNotFoundException ex) {

            java.util.logging.Logger.getLogger(S3ServicesImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  keyName;
    }
}
