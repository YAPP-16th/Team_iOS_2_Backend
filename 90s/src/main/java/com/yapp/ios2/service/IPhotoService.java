package com.yapp.ios2.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPhotoService{

    String upload(MultipartFile photo, Integer albumUid, Integer photoOrder, Integer uploader) throws IOException;

}
