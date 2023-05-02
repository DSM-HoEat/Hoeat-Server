package com.example.hoeatserver.infrastructure.aws.image.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.hoeatserver.domain.user.domain.User;
import com.example.hoeatserver.domain.user.domain.repository.UserRepository;
import com.example.hoeatserver.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Upload {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final UserFacade userFacade;
    private final AmazonS3Client amazonS3Client;
    private final UserRepository userRepository;

    public String uploadImage(MultipartFile multipartFile){
        return upload(multipartFile);
    }

    public void updateUser(MultipartFile multipartFile){
        User user = userFacade.getCurrentUser();
        if(user.getProfile() != null){
            amazonS3Client.deleteObject(bucket,user.getProfile());
        }
        user.userProfileChange(upload(multipartFile));
        userRepository.save(user);
    }

    public void delUser(User user){
        if(user.getProfile() != null){
            amazonS3Client.deleteObject(bucket,user.getProfile());
        }
    }

    private String getImageUrl(String s3FileName){
        return amazonS3Client.getUrl(bucket, s3FileName).toString();
    }

    public String upload(MultipartFile multipartFile){
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        ObjectMetadata objMeta = new ObjectMetadata();
        try {
            objMeta.setContentLength(multipartFile.getInputStream().available());
            amazonS3Client.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
        } catch (IOException e) {
            throw new IllegalArgumentException("Image Not Found");
        }

        return getImageUrl(s3FileName);
    }

}
