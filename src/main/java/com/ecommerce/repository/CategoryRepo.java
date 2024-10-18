package com.ecommerce.repository;

import com.ecommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepo extends JpaRepository<Category, String> {

}
