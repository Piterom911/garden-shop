package com.predators.service;

import com.jayway.jsonpath.PathNotFoundException;
import com.predators.dto.cart.ProductToCartRequestDto;
import com.predators.entity.Cart;
import com.predators.entity.CartItem;
import com.predators.entity.Product;
import com.predators.entity.ShopUser;
import com.predators.repository.CartJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartJpaRepository repository;

    private final ShopUserService shopUserService;

    private final ProductService productService;

    private final CartItemService cartItemService;

    @Override
    public List<Cart> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public CartItem addProduct(ProductToCartRequestDto productToCartRequestDto) {
        ShopUser currentUser = shopUserService.getCurrentUser();
        if (currentUser.getCart() == null) {
            Cart cart = Cart.builder()
                    .userId(currentUser)
                    .build();
            repository.save(cart);
            currentUser.setCart(cart);
        }
        Product product = productService.getById(productToCartRequestDto.productId());
        Optional<CartItem> byProductId = cartItemService.findByProduct_Id(productToCartRequestDto.productId());
        if (byProductId.isPresent()) {
            CartItem cartItem = byProductId.get();
            cartItem.setQuantity(productToCartRequestDto.quantity());
            return cartItemService.create(cartItem);
        }
        return cartItemService.create(CartItem.builder()
                .cart(currentUser.getCart())
                .product(product)
                .quantity(productToCartRequestDto.quantity())
                .build());
    }

    @Override
    public void deleteProduct(Long productId) {
        ShopUser user = shopUserService.getCurrentUser();
        Optional<CartItem> byProductId = cartItemService.findByProduct_Id(productId);
        if (byProductId.isEmpty()) {
            throw new PathNotFoundException("Product with id " + productId + " not found");
        }
        cartItemService.delete(byProductId.get().getId());
    }

}
