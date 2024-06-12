package com.yourbnb.image.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.dir}")
    private String fileDir;

    /**
     * S3로 이미지를 업로드한다.
     *
     * @param file       : 업로드할 파일
     * @param uploadName : 업로드할 파일 이름
     * @return 업로드 경로
     */
    public String uploadImage(MultipartFile file, String uploadName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        PutObjectRequest objectRequest = new PutObjectRequest(bucket, uploadName, file.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead); // 파일의 ACL(Access Control List)을 PublicRead로 설정
        amazonS3Client.putObject(objectRequest);

        log.info("S3에 이미지 업로드 성공 - {}", uploadName);
        return amazonS3Client.getUrl(bucket, uploadName).toString();
    }

    /**
     * S3에 업로드 되어 있는 이미지의 URL을 가져온다.
     *
     * @param storeName : 가지고 올 파일 이름
     * @return 업로드 경로
     */
    public String getImageUrl(String storeName) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, fileDir + storeName,
                HttpMethod.GET);
        return amazonS3Client.generatePresignedUrl(request).toString();
    }

    /**
     * S3에 있는 이미지를 삭제한다.
     *
     * @param fileName : 삭제할 파일 이름
     */
    public void deleteImage(String fileName) {
        try {
            String key = fileDir + fileName;
            try {
                amazonS3Client.deleteObject(bucket, key);
            } catch (AmazonServiceException e) {
                log.error(e.getErrorMessage());
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        log.info("S3에 있는 이미지 삭제 성공 - {}", fileName);
    }
}
