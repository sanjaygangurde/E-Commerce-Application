package com.ecommerce.service.impl;

import com.ecommerce.exception.BadApiRequestException;
import com.ecommerce.service.FileServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileServiceI {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();
        logger.info("FileName : {} ", originalFilename);

        String fileName = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = fileName + extension;
        String fullPathName = path + File.separator + fileNameWithExtension;

        logger.info("Full path image : {}", fullPathName);

        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            logger.info("file extension is : {}", extension);
            // save file
            File folder = new File(path);
            if (!folder.exists()) {
                // create folder
                folder.mkdirs();
            }
            // upload file
            Files.copy(file.getInputStream(), Paths.get(fullPathName));
            return fileNameWithExtension;

        } else {
            throw new BadApiRequestException("File with this " + extension + " not allowed");
        }

    }

    @Override
    public InputStream getFile(String path, String name) throws FileNotFoundException {


        String fileName = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fileName);
        return inputStream;
    }
}
