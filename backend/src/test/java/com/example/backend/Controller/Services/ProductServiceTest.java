package com.example.backend.Controller.Services;

import com.example.backend.Model.Entities.Product;
import com.example.backend.Model.Entities.ProductRanking;
import com.example.backend.Model.Repositories.DetailSaleRepository;
import com.example.backend.Model.Repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private DetailSaleRepository detailSaleRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        detailSaleRepository = mock(DetailSaleRepository.class);
        productService = new ProductService(productRepository, detailSaleRepository);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(BigDecimal.valueOf(10.00));
        product1.setAmount(100);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(BigDecimal.valueOf(20.00));
        product2.setAmount(200);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // Act
        List<Product> products = productService.getAllProducts();

        // Assert
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("Product 1", products.get(0).getName());
    }

    @Test
    void testGetProductById() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(BigDecimal.valueOf(10.00));
        product.setAmount(100);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Optional<Product> result = productService.getProductById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Product 1", result.get().getName());
    }

    @Test
    void testSaveProduct() {
        // Arrange
        Product product = new Product();
        product.setName("New Product");
        product.setPrice(BigDecimal.valueOf(30.00));
        product.setAmount(50);

        when(productRepository.save(product)).thenReturn(product);

        // Act
        Product savedProduct = productService.saveProduct(product);

        // Assert
        assertNotNull(savedProduct);
        assertEquals("New Product", savedProduct.getName());
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Long productId = 1L;
        doNothing().when(productRepository).deleteById(productId);

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testGetProductRanking() {
        // Arrange
        Object[] row1 = {1L, "Product 1", 50L};
        Object[] row2 = {2L, "Product 2", 30L};
        when(detailSaleRepository.getProductRanking(Mockito.any())).thenReturn(Arrays.asList(row1, row2));

        // Act
        List<ProductRanking> rankings = productService.getProductRanking();

        // Assert
        assertNotNull(rankings);
        assertEquals(2, rankings.size());
        assertEquals("Product 1", rankings.get(0).getProductName());
        assertEquals(50L, rankings.get(0).getTotalSold());
    }
}
