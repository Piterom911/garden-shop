package com.predators.service;

import com.predators.entity.Category;
import com.predators.exception.CategoryNotFoundException;
import com.predators.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public List<Category> getAll() {
        return categoryJpaRepository.findAll();
    }

    @Override
    public Category getById(Long id) {
        return categoryJpaRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
    }

    @Override
    public Category create(Category category) {
        return categoryJpaRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        if (!categoryJpaRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
        categoryJpaRepository.deleteById(id);
    }
}