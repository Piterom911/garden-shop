package com.predators.dto.product;

import com.predators.entity.Category;
import com.predators.entity.Product;
import com.predators.service.CategoryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    protected CategoryService categoryService;

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "image", target = "imageUrl")
    public abstract Product toEntity(ProductRequestDto productDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "imageUrl", target = "image")
    @Mapping(source = "discountPrice", target = "discountPrice")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    public abstract ProductResponseDto toDto(Product product);

    protected Category mapCategoryIdToCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return categoryService.getById(categoryId);
    }
}

