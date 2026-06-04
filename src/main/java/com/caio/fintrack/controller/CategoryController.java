package com.caio.fintrack.controller;

import com.caio.fintrack.model.Category;
import com.caio.fintrack.repository.CategoryRepository;
import com.caio.fintrack.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/categorias")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {

        Category saveCategory = categoryService.saveCategory(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveCategory);
    }
}
