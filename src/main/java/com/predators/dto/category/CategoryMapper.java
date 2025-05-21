package com.predators.dto.category;

import com.predators.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto toDto(Category category);
}
