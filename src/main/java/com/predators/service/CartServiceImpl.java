package com.predators.service;

import com.predators.dto.cart.ProductToItemDto;
import com.predators.entity.Cart;
import com.predators.entity.CartItem;
import com.predators.entity.Product;
import com.predators.entity.ShopUser;
import com.predators.exception.NotCurrentClientCartException;
import com.predators.exception.ProductNotFoundException;
import com.predators.repository.CartJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
    public Set<CartItem> getAllCartItems() {
        ShopUser currentUser = shopUserService.getCurrentUser();
        if (currentUser.getCart() == null) {
            return null;
        }
        return currentUser.getCart().getCartItems();
    }

    @Override
    @Transactional
    public CartItem addProduct(ProductToItemDto productToItemDto) {
        ShopUser currentUser = shopUserService.getCurrentUser();
        Cart cart = currentUser.getCart();
        if (cart == null) {
            cart = createCart(currentUser);
        }

        Product product = productService.getById(productToItemDto.productId());
        CartItem cartItem = cartItemService.findCartItemByProductAndCart(product, cart);
        if (cartItem == null) {
            cartItem = CartItem.builder()
                    .cart(currentUser.getCart())
                    .product(product)
                    .quantity(productToItemDto.quantity())
                    .build();
        } else {
            cartItem.setQuantity(productToItemDto.quantity());
        }

        return cartItemService.create(cartItem);
    }

    private Cart createCart(ShopUser currentUser) {
        return repository.save(Cart.builder()
                .user(currentUser)
                .build());
    }

    @Override
    public void deleteProduct(Long productId) {
        Optional<CartItem> cartItemByProduct = cartItemService.findByProduct_Id(productId);
        if (cartItemByProduct.isEmpty()) {
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        }

        ShopUser user = shopUserService.getCurrentUser();
        Cart cart = cartItemByProduct.get().getCart();
        if (!Objects.equals(user.getCart().getId(), cart.getId())) {
            throw new NotCurrentClientCartException("This is not your Cart. Finger weg!");
        }
        cartItemService.delete(cartItemByProduct.get().getId());
    }

    @Override
    public void deleteById(Long id) {
        Optional<Cart> cartIf = repository.findById(id);
        cartIf.ifPresent(repository::delete);
    }

    @Override
    public Cart save(Cart cart) {
        return repository.save(cart);
    }
}
