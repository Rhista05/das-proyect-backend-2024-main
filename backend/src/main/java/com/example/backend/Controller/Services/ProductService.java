package com.example.backend.Controller.Services;

import com.example.backend.Model.Entities.Product;
import com.example.backend.Model.Entities.ProductRanking;
import com.example.backend.Model.Repositories.DetailSaleRepository;
import com.example.backend.Model.Repositories.ProductRepository;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final DetailSaleRepository detailSaleRepository;

    public ProductService(ProductRepository productRepository, DetailSaleRepository detailSaleRepository) {
        this.productRepository = productRepository;
        this.detailSaleRepository = detailSaleRepository;
    }

    // Obtener todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Obtener un producto por ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Guardar (crear o actualizar) un producto
    public Product saveProduct(Product product) {
        return productRepository.save(product); // El repositorio se encargará de crear o actualizar el producto
    }

    // Eliminar un producto
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductRanking> getProductRanking() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Object[]> result = detailSaleRepository.getProductRanking(pageable);
        List<ProductRanking> rankings = new ArrayList<>();
        for (Object[] row : result) {
            ProductRanking ranking = new ProductRanking();
            ranking.setProductId((Long) row[0]);
            ranking.setProductName((String) row[1]);
            ranking.setTotalSold((Long) row[2]);
            rankings.add(ranking);
        }
        return rankings;
    }
}
