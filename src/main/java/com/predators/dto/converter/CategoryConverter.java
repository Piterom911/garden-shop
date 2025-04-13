package com.predators.dto.converter;

import com.predators.dto.category.CategoryRequestDto;
import com.predators.dto.category.CategoryResponceDto;
import com.predators.entity.Category;
import com.predators.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements Converter<CategoryRequestDto, CategoryResponceDto, Category> {

    private final ProductService productService;

    public CategoryConverter(ProductService productService) {
        this.productService = productService;
    }

    public Category toEntity(CategoryRequestDto requestDto) {
        return Category.builder()
                .name(requestDto.name())
                .build();
    }

    public CategoryResponceDto toDto(Category category) {
        return CategoryResponceDto.builder()
                .id(category.getId())
                .name(category.getName())
                .products(category.getProducts())
                .build();
    }
}


