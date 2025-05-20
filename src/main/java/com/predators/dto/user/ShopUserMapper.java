package com.predators.dto.user;

import com.predators.entity.ShopUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShopUserMapper {

    @Mapping(source = "name", target = "name")
    ShopUser toEntity(UserRequestDto userRequestDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    UserResponseDto toDto(ShopUser user);
}
