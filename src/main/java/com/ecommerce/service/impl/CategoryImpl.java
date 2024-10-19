package com.ecommerce.service.impl;

import com.ecommerce.dtos.CategoryDto;
import com.ecommerce.entities.Category;
import com.ecommerce.exception.BadApiRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.helper.Helper;
import com.ecommerce.payloads.PageableResponse;
import com.ecommerce.repository.CategoryRepo;
import com.ecommerce.service.CategoryI;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class CategoryImpl implements CategoryI {

    private Logger logger = LoggerFactory.getLogger(CategoryImpl.class);

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        String categoryIds = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryIds);

        Category category = mapper.map(categoryDto, Category.class);
        Category saveCategory = categoryRepo.save(category);
        return mapper.map(saveCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category With given id is not found"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        Category updateCategory = categoryRepo.save(category);

        return mapper.map(updateCategory, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category With given id is not found"));
        categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {

        Category category1 = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category With given id is not found"));

        return mapper.map(category1, CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable PAGEABLE = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categories = categoryRepo.findAll(PAGEABLE);

        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(categories, CategoryDto.class);

        return pageableResponse;
    }

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
