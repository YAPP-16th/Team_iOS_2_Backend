package com.yapp.ios2.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.util.IOUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@NoArgsConstructor
public class S3Service{

    private AmazonS3 s3Client;
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.sns.topic.arn")
    private String arn;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String upload(MultipartFile file, String fileName) throws IOException {

        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, fileName).toString();
    }

    public byte[] download(Long albumUid, String fileName) throws IOException {
        S3Object file = s3Client.getObject(new GetObjectRequest(bucket +
        "/" + albumUid.toString(), fileName + ".jpeg"));

        S3ObjectInputStream s3OIS = file.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(s3OIS);

        return bytes;
//        Resource resource = new ByteArrayResource(bytes);
//        return resource;
    }

    public void deleteByAlbum(Long albumUid){

        for (S3ObjectSummary file : s3Client.listObjects(bucket, albumUid.toString() + "/").getObjectSummaries()){
            s3Client.deleteObject(bucket, file.getKey());
        }

    }


}
