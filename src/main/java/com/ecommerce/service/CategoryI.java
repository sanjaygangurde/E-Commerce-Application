package com.ecommerce.service;

import com.ecommerce.dtos.CategoryDto;
import com.ecommerce.payloads.PageableResponse;

public interface CategoryI {

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto, String categoryId);

    void delete(String categoryId);

    CategoryDto getCategoryById(String categoryId);

    PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir);

}
