package com.example.hackathon.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AmazonConfig {

    @Bean(name = "amazonS3")
    public AmazonS3 s3Bucket() {
        AWSCredentials awsCredentials =
                new BasicAWSCredentials("AKIAQQNHZOC3VOJULWF7", "RCWgGoMaZPCG2awjYyULhCH3AfJK6wZYo2BdcCaq");
        return AmazonS3ClientBuilder
                .standard()
                .withRegion("us-east-1")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
