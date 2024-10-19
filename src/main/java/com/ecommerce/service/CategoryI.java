package com.ecommerce.service;

import com.ecommerce.dtos.CategoryDto;
import com.ecommerce.payloads.PageableResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface CategoryI {

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto, String categoryId);

    void delete(String categoryId);

    CategoryDto getCategoryById(String categoryId);

    PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir);

    public String uploadFile(MultipartFile file, String path) throws IOException;

    public InputStream getFile(String path, String name) throws FileNotFoundException;

}
