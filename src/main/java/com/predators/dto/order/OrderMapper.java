//package com.predators.dto.order;
//
//
//import com.predators.dto.orderitem.OrderItemMapper;
//import com.predators.dto.orderitem.OrderItemResponseDto;
//import com.predators.entity.Order;
//import com.predators.entity.ShopUser;
//import com.predators.entity.enums.DeliveryMethod;
//import com.predators.entity.enums.OrderStatus;
//import com.predators.service.ShopUserService;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Named;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
//public abstract class OrderMapper { // Change to abstract class to use @Autowired within custom methods
//
//    @Autowired
//    protected OrderItemMapper orderItemMapper;
//
//    @Autowired
//    protected ShopUserService shopUserService;
//
//    @Mapping(target = "id", ignore = true) // ID is generated
//    @Mapping(target = "user", ignore = true, qualifiedByName = "mapUserFromId") // Custom mapping for user
//    @Mapping(target = "deliveryMethod", source = "deliveryMethod", qualifiedByName = "stringToDeliveryMethod")
//    @Mapping(target = "createdAt", expression = "java(java.sql.Timestamp.from(java.time.Instant.now()))")
//    @Mapping(target = "contactPhone", source = "user", qualifiedByName = "mapContactPhoneFromUser") // Get from user
//    @Mapping(target = "status", expression = "java(com.predators.entity.enums.OrderStatus.CREATED)")
//    @Mapping(target = "orderItems", ignore = true) // Items will be handled separately (e.g., in service)
//    @Mapping(target = "updatedAt", ignore = true) // Updated at will be handled later
//    public abstract Order toEntity(OrderRequestDto dto); // Pass ShopUser here
//
//    // Map Order entity to OrderResponseDto
//    @Mapping(source = "user.id", target = "userId")
//    @Mapping(source = "status", target = "status", qualifiedByName = "orderStatusToString")
//    @Mapping(source = "orderItems", target = "items", qualifiedByName = "mapOrderItems")
//    public abstract OrderResponseDto toDto(Order order);
//
//
//    // Custom mapping for DeliveryMethod
//    @Named("stringToDeliveryMethod")
//    protected DeliveryMethod stringToDeliveryMethod(String deliveryMethod) {
//        if (deliveryMethod == null) {
//            return null;
//        }
//        return Enum.valueOf(DeliveryMethod.class, deliveryMethod.toUpperCase());
//    }
//
//    // Custom mapping for OrderStatus to String
//    @Named("orderStatusToString")
//    protected String orderStatusToString(OrderStatus status) {
//        return status != null ? status.name() : null;
//    }
//
//    // Custom mapping for order items
//    @Named("mapOrderItems")
//    protected List<OrderItemResponseDto> mapOrderItems(List<com.predators.entity.OrderItem> orderItems) {
//        if (orderItems == null) {
//            return null;
//        }
//        return orderItems.stream()
//                .map(orderItemMapper::toDto)
//                .toList();
//    }
//
//    // Placeholder for mapping ShopUser by ID - this should ideally be handled by a service
//    // For simplicity, we'll assume the ShopUser is passed directly to toEntity
//    @Named("mapUserFromId")
//    protected ShopUser mapUserFromId() {
//        return shopUserService.getCurrentUser();
//    }
//
//    // Custom mapping for contactPhone from ShopUser
//    @Named("mapContactPhoneFromUser")
//    protected String mapContactPhoneFromUser(ShopUser user) {
//        return user != null ? user.getPhoneNumber() : null;
//    }
//}
//
