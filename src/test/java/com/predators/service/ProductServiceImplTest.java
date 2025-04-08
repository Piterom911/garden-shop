package com.predators.service;

import com.predators.entity.Product;
import com.predators.exception.ProductNotFoundException;
import com.predators.repository.ProductJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductJpaRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    public void testGetAll() {
        service.getAll();
        verify(repository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        service.create(new Product());
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void testGetById() {
        Long id = 1L;
        Product expectedProduct = new Product();
        when(repository.findById(id)).thenReturn(Optional.of(expectedProduct));

        Product actualProduct = service.getById(id);

        assertEquals(expectedProduct, actualProduct);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testGetByIdNotFound() {
        assertThrows(ProductNotFoundException.class, () ->
                service.getById(2L));
    }

    @Test
    void delete() {
        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}