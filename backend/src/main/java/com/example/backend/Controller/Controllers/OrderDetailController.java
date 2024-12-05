package com.example.backend.Controller.Controllers;

import com.example.backend.Model.Entities.OrderDetail;
import com.example.backend.Controller.Services.OrderDetailService;
import com.example.backend.Controller.Services.OrderService;
import com.example.backend.Controller.Services.ProductService;
import com.example.backend.Model.Entities.Order;
import com.example.backend.Model.Entities.Product;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orderDetails")
public class OrderDetailController {

    private final OrderDetailService orderDetailService; 
    private final OrderService orderService;
    private final ProductService productService;
    
    public OrderDetailController(OrderDetailService orderDetailService, OrderService orderService, ProductService productService) {
        this.orderDetailService = orderDetailService;
        this.orderService = orderService;
        this.productService = productService;
    }

    // Crear un nuevo detalle de orden
    @PostMapping
    public ResponseEntity<OrderDetail> createOrderDetail(@RequestBody OrderDetail orderDetail) {
        if (orderDetail.getOrder() == null || orderDetail.getProduct() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Long orderId = orderDetail.getOrder().getId();
        Long productId = orderDetail.getProduct().getId();

        // Buscar la orden y el producto por sus IDs
        Optional<Order> optionalOrder = orderService.getOrderById(orderId);
        Optional<Product> optionalProduct = productService.getProductById(productId);

        // Verificar si la orden y el producto existen
        Order order = optionalOrder.orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        Product product = optionalProduct.orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Establecer las relaciones en OrderDetail
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity())));

        // Guardar el detalle de la orden
        OrderDetail createdOrderDetail = orderDetailService.createOrderDetail(orderDetail);
        return new ResponseEntity<>(createdOrderDetail, HttpStatus.CREATED);
    }

    // Obtener todos los detalles de órdenes
    @GetMapping
    public ResponseEntity<List<OrderDetail>> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails();
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    // Obtener detalle de orden por ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Long id) {
        OrderDetail orderDetail = orderDetailService.getOrderDetailById(id);
        if (orderDetail != null) {
            return new ResponseEntity<>(orderDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar detalle de orden por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }
}
