package com.caio.fintrack.service;

import com.caio.fintrack.model.Category;
import com.caio.fintrack.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveCategory(Category category) {

        categoryRepository.save(category);

        return category;
    }
}
