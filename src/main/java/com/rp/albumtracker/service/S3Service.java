package com.rp.albumtracker.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;

@Component
public class S3Service {
  @Value("${s3.bucket.name}")
  private String bucketName;

  private S3Template s3Template;

  S3Service(S3Template s3Template) throws FileNotFoundException {
    this.s3Template = s3Template;
  }

  public void readFile() throws IOException {

  }

  public String uploadFile(InputStream file, String filename) throws IOException {
    S3Resource createdResource = s3Template.upload(bucketName, filename, file);
    return createdResource.getURL().toString();
  }
}
