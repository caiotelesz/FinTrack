package com.caio.fintrack.service;

import com.caio.fintrack.dto.request.CategoryRequestDTO;
import com.caio.fintrack.dto.response.CategoryResponseDTO;
import com.caio.fintrack.exception.ExistsNameException;
import com.caio.fintrack.model.Category;
import com.caio.fintrack.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO saveCategory(CategoryRequestDTO request) {

        Category category = new Category();
        category.setName(request.getNome());

        if(categoryRepository.existsByName(category.getName())) {
            throw new ExistsNameException("Já existe uma categoria com o nome '" + category.getName() + "'");
        }

        Category savedCategory = categoryRepository.save(category);

        return new CategoryResponseDTO(
            savedCategory.getId(),
            savedCategory.getName()
        );
    }

    public List<CategoryResponseDTO> findAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(
                        category -> new CategoryResponseDTO(
                                category.getId(),
                                category.getName()
                        ))
                .collect(Collectors.toList()
                );
    }

    public CategoryResponseDTO updateCategory(UUID id, CategoryRequestDTO request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category id not found"));

        category.setName(request.getNome());

        if(categoryRepository.existsByName(category.getName())) {
            throw new ExistsNameException("Já existe uma categoria com o nome '" + category.getName() + "'");
        }

        Category savedCategory = categoryRepository.save(category);

        return new CategoryResponseDTO(
            savedCategory.getId(),
            savedCategory.getName()
        );
    }
}
