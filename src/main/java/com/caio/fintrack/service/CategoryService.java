package com.caio.fintrack.service;

import com.caio.fintrack.dto.request.CategoryRequestDTO;
import com.caio.fintrack.dto.response.CategoryResponseDTO;
import com.caio.fintrack.exception.CategoryHasTransactionsException;
import com.caio.fintrack.exception.IdNotFoundException;
import com.caio.fintrack.exception.CategoryAlreadyExistsException;
import com.caio.fintrack.model.Category;
import com.caio.fintrack.repository.CategoryRepository;
import com.caio.fintrack.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public CategoryService(CategoryRepository categoryRepository, TransactionRepository transactionRepository) {
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    public CategoryResponseDTO saveCategory(CategoryRequestDTO request) {
        Category category = new Category();
        category.setName(request.getNome());

        if(categoryRepository.existsByName(request.getNome())) {
            throw new CategoryAlreadyExistsException(request.getNome());
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
                .orElseThrow(IdNotFoundException::new);

        if(categoryRepository.existsByName(request.getNome())) {
            throw new CategoryAlreadyExistsException(request.getNome());
        }

        category.setName(request.getNome());

        Category savedCategory = categoryRepository.save(category);

        return new CategoryResponseDTO(
            savedCategory.getId(),
            savedCategory.getName()
        );
    }

    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(IdNotFoundException::new);

        if(transactionRepository.existsByCategoryId(id)) {
            throw new CategoryHasTransactionsException();
        }

        categoryRepository.delete(category);
    }
}
