package com.predators.service;

import com.predators.dto.cart.ProductToItemDto;
import com.predators.entity.Cart;
import com.predators.entity.CartItem;
import com.predators.entity.Product;
import com.predators.entity.ShopUser;
import com.predators.exception.NotCurrentClientCartException;
import com.predators.exception.ProductNotFoundException;
import com.predators.repository.CartJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartJpaRepository cartRepository;

    @Mock
    private ShopUserService shopUserService;

    @Mock
    private ProductService productService;

    @Mock
    private CartItemService cartItemService;

    @InjectMocks
    private CartServiceImpl cartService;

    private ShopUser user;
    private Product product;
    private ProductToItemDto dto;

    @BeforeEach
    void setUp() {
        user = new ShopUser();
        user.setId(1L);

        product = Product.builder()
                .id(10L)
                .name("Test Product")
                .build();

        dto = new ProductToItemDto(10L, 3);
    }

    @Test
    void addProduct_shouldCreateCartAndAddItem() {
        user.setCart(null);
        when(shopUserService.getCurrentUser()).thenReturn(user);
        when(productService.getById(10L)).thenReturn(product);

        CartItem newItem = CartItem.builder()
                .id(100L)
                .product(product)
                .quantity(3)
                .build();

        when(cartItemService.create(any())).thenReturn(newItem);

        CartItem result = cartService.addProduct(dto);

        assertNotNull(result);
        assertEquals(3, result.getQuantity());
        assertEquals(10L, result.getProduct().getId());

        verify(cartRepository, times(1)).save(any(Cart.class));
        verify(cartItemService, times(1)).create(any(CartItem.class));
    }

    @Test
    void addProduct_shouldUpdateQuantityIfProductAlreadyInCart() {
        Cart cart = new Cart();
        cart.setCartItems(new HashSet<>());

        CartItem existing = CartItem.builder()
                .id(1L)
                .product(product)
                .quantity(1)
                .build();

        CartItem cartItemNew = CartItem.builder()
                .id(1L)
                .product(product)
                .quantity(3)
                .build();

        cart.getCartItems().add(existing);
        cart.getCartItems().add(cartItemNew);
        cart.setId(1L);
        user.setCart(cart);

        when(shopUserService.getCurrentUser()).thenReturn(user);
        when(productService.getById(10L)).thenReturn(product);
        when(cartItemService.create(any())).thenReturn(existing);

        CartItem result = cartService.addProduct(dto);

        assertEquals(1, result.getQuantity());
    }

    @Test
    void getAll_shouldReturnAllCarts() {
        List<Cart> carts = List.of(new Cart(), new Cart());
        when(cartRepository.findAll()).thenReturn(carts);

        List<Cart> result = cartService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    void getAllProducts_shouldReturnCartItemsFromUserCart() {
        CartItem item = CartItem.builder().product(product).build();
        Cart cart = Cart.builder().cartItems(Set.of(item)).build();
        user.setCart(cart);

        when(shopUserService.getCurrentUser()).thenReturn(user);

        Set<CartItem> products = cartService.getAllCartItems();

        assertEquals(1, products.size());
    }


    @Test
    void deleteProduct_shouldRemoveProductFromUserCart() {
        Cart cart = new Cart();
        cart.setId(1L);
        CartItem item = CartItem.builder().id(22L).product(product).cart(cart).build();

        user.setCart(cart);

        when(shopUserService.getCurrentUser()).thenReturn(user);
        when(cartItemService.findByProduct_Id(10L)).thenReturn(Optional.of(item));

        cartService.deleteProduct(10L);

        verify(cartItemService).delete(22L);
    }

    @Test
    void deleteProduct_shouldThrowIfNotFound() {
        when(cartItemService.findByProduct_Id(10L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> cartService.deleteProduct(10L));
    }

    @Test
    void deleteProduct_shouldThrowIfCartNotOwnedByUser() {
        Cart cart = new Cart();
        cart.setId(2L);
        CartItem item = CartItem.builder().id(33L).product(product).cart(cart).build();

        Cart userCart = new Cart();
        userCart.setId(1L);
        user.setCart(userCart);

        when(shopUserService.getCurrentUser()).thenReturn(user);
        when(cartItemService.findByProduct_Id(10L)).thenReturn(Optional.of(item));

        assertThrows(NotCurrentClientCartException.class, () -> cartService.deleteProduct(10L));
    }

    @Test
    void deleteById_shouldDeleteIfFound() {
        Cart cart = new Cart();
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        cartService.deleteById(1L);

        verify(cartRepository).delete(cart);
    }

    @Test
    void deleteById_shouldDoNothingIfNotFound() {
        when(cartRepository.findById(999L)).thenReturn(Optional.empty());

        cartService.deleteById(999L);

        verify(cartRepository, never()).delete(any());
    }
}
