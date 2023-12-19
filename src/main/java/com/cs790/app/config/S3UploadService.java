package com.cs790.app.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class S3UploadService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public String uploadFile(MultipartFile file) throws IOException {


            // Convert MultipartFile to File
            File convertedFile = convertMultiPartFileToFile(file);
        System.out.println(convertedFile.getAbsolutePath());
        System.out.println(file.getOriginalFilename());
            // Upload file to S3
//            String key = "uploads/" + file.getOriginalFilename();
        String key = file.getOriginalFilename();
        s3Client.putObject(new com.amazonaws.services.s3.model.PutObjectRequest(bucketName,key,convertedFile));
        convertedFile.delete();
//        return "File uploaded: "+ key;
        return s3Client.getUrl(bucketName, key).toString();


    }


    public byte[] downloadFile(String fileName){
        S3Object s3Object = s3Client.getObject(bucketName,fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try{
          byte[] content =   IOUtils.toByteArray(inputStream);
          return content;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String removeFile(String fileName){
        try {
            s3Client.deleteObject(bucketName, fileName);
            return fileName + " removed";
        } catch (AmazonS3Exception e) {
            // Log the exception
            e.printStackTrace();
            return "Error removing file: " + e.getMessage();
        }
    }
    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        //writing the file so that it can be accessed later for upload by s3 client, else exception - file not found for MD5 Hash
        try(FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        }catch(Exception e){

        }
        return convertedFile;
    }
}
