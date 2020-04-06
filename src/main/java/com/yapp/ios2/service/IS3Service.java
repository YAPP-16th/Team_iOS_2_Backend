package com.yapp.ios2.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IS3Service {

    String upload(MultipartFile file, String fileName) throws IOException;

}
