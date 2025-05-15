package com.predators.dto.category;

import com.predators.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "name", target = "name")
    Category toEntity(CategoryRequestDto categoryRequestDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    CategoryResponseDto toDto(Category category);
}
