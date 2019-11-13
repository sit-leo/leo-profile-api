package app.leo.profile.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentManagementService {

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    private Logger logger;

    public Map<String,String> uploadMultipleFiles(List<MultipartFile> files) {
        Map<String,String> nameList =new HashMap<>();
        if (files != null) {
            files.forEach(multipartFile -> {
                File file = convertMultiPartFileToFile(multipartFile);
                String uniqueFileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
                uploadFileToS3bucket(uniqueFileName, file, bucketName);
                nameList.put(multipartFile.getOriginalFilename(),uniqueFileName);
            });
        }
        return nameList;
    }

    private void uploadFileToS3bucket(String fileName, File file, String bucketName) {
        amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, file));

    }

    private void uploadPublicFileToS3bucket(String fileName,File file,String bucketName){
        amazonS3Client.putObject(new PutObjectRequest(bucketName,fileName,file).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String getLinkOfProfilePicture(String filename){
        return amazonS3Client.getUrl(bucketName, filename).toExternalForm();
    }

    public Map<String,String> uploadPicture (MultipartFile multipartFile, String folderName){
        Map<String,String> name = new HashMap<>();
        if(multipartFile != null){
            File file = convertMultiPartFileToFile(multipartFile);
            String uploadFilename = folderName +  System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            uploadPublicFileToS3bucket(uploadFilename,file,bucketName);
            name.put(multipartFile.getOriginalFilename(),uploadFilename);
        }
        return name;
    }

    public S3Object getPicture(String fileName){
        return getS3Object(fileName);
    }

    public S3ObjectInputStream getObjectInputStream(String fileName){
        S3Object object = amazonS3Client.getObject(this.bucketName,fileName);
        return object.getObjectContent();
    }

    public InputStream getInputStream(String fileName){
        S3Object object = amazonS3Client.getObject(this.bucketName,fileName);
        return object.getObjectContent();
    }
    public S3Object getS3Object(String fileName){
        return amazonS3Client.getObject(this.bucketName,fileName);
    }

    private void deleteFileFromS3bucket(String fileName, String bucketName) {
        amazonS3Client.deleteObject(bucketName, fileName);
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            logger.warn("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
