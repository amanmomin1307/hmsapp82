//package com.hmsapp.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.AmazonS3Exception;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//
//@Service
//public class BucketService {
//    //to uploade file in s3
//
//    @Autowired
//    private AmazonS3 amazonS3;
//
//    public String uploadFile(MultipartFile file, String bucketName){
//        if(file.isEmpty()){
//            throw new IllegalStateException("Cannot uploade empty file");
//        }
//        try{
//            File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+file.getOriginalFilename());
//            file.transferTo(convFile);
//            try {
//                amazonS3.putObject(bucketName,convFile.getName(),convFile);
//                return amazonS3.getUrl(bucketName,file.getOriginalFilename()).toString();
//            }catch (AmazonS3Exception s3Exception){
//
//            }
//        }catch (Exception e){
//
//        }
//    }
//}

package com.hmsapp.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class BucketService {

    @Autowired
    private AmazonS3 amazonS3;

    public String uploadFile(MultipartFile file, String bucketName) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload an empty file.");
        }

        File convFile = null;
        try {
            // Convert MultipartFile to a temporary file
            convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);

            // Upload the file to the S3 bucket
            amazonS3.putObject(bucketName, convFile.getName(), convFile);

            // Return the file's public URL
            return amazonS3.getUrl(bucketName, convFile.getName()).toString();

        } catch (AmazonS3Exception s3Exception) {
            throw new RuntimeException("Error uploading file to S3: " + s3Exception.getMessage(), s3Exception);

        } catch (IOException ioException) {
            throw new RuntimeException("Error converting MultipartFile to File: " + ioException.getMessage(), ioException);

        } finally {
            // Ensure the temporary file is deleted after upload
            if (convFile != null && convFile.exists()) {
                convFile.delete();
            }
        }
    }
}

