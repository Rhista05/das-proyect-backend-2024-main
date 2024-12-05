package com.example.backend.Controller.Controllers;

import com.example.backend.Controller.Services.DetailSaleService;
import com.example.backend.Controller.Services.ProductService;
import com.example.backend.Controller.Services.SaleService;
import com.example.backend.Model.Entities.DetailSale;
import com.example.backend.Model.Entities.Product;
import com.example.backend.Model.Entities.Sale;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detailSales")
public class DetailSaleController {

    
    private final DetailSaleService detailSaleService;
    private final SaleService saleService;
    private final ProductService productService;

    public DetailSaleController(DetailSaleService detailSaleService, SaleService saleService, ProductService productService) {
        this.detailSaleService = detailSaleService;
        this.saleService = saleService;
        this.productService = productService;
    }

    // Get all sales details
    @GetMapping
    public ResponseEntity<List<DetailSale>> getAllDetailSales() {
        List<DetailSale> detailSales = detailSaleService.findAll();
        return new ResponseEntity<>(detailSales, HttpStatus.OK);
    }

    // Get all sales details foy ID
    @GetMapping("/{id}")
    public ResponseEntity<DetailSale> getDetailSaleById(@PathVariable Long id) {
        DetailSale detailSale = detailSaleService.findById(id);
        if (detailSale != null) {
            return new ResponseEntity<>(detailSale, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // create nwe datail sale
    @PostMapping
    public ResponseEntity<DetailSale> createDetailSale(@RequestBody DetailSale detailSale) {
        if (detailSale.getSale() == null || detailSale.getProduct() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Long saleId = detailSale.getSale().getId();
        Long productId = detailSale.getProduct().getId();

        // Buscar la orden y el producto por sus IDs
        Optional<Sale> optionalOrder = saleService.findSaleById(saleId);
        Optional<Product> optionalProduct = productService.getProductById(productId);

        // Verificar si la orden y el producto existen
        Sale sale = optionalOrder.orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        Product product = optionalProduct.orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Establecer las relaciones en OrderDetail
        detailSale.setSale(sale);
        detailSale.setProduct(product);
        detailSale.setPrice(product.getPrice().multiply(BigDecimal.valueOf(detailSale.getQuantity())));

        // Safe order detail
        DetailSale createDetailSale = detailSaleService.createDetailSale(detailSale);
        return new ResponseEntity<>(createDetailSale, HttpStatus.CREATED);
    }

    // delete sail for id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetailSale(@PathVariable Long id) {
        detailSaleService.deleteDetailSale(id);
        return ResponseEntity.noContent().build();
    }

    // Detalles de la venta
    @GetMapping("/sale-pro/{saleId}")
    public ResponseEntity<List<DetailSale>> getDetailSalesBySaleId(@PathVariable Long saleId) {
        List<DetailSale> detailSales = detailSaleService.getDetailsBySaleId(saleId);
        if (detailSales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detailSales);
    }

}
