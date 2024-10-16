package com.ecommerce.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileServiceI {

    public String uploadFile(MultipartFile file, String path) throws IOException;

    public InputStream getFile(String path, String name) throws FileNotFoundException;
}
