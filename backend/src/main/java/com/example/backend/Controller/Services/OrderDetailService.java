package com.example.backend.Controller.Services;

import com.example.backend.Model.Entities.OrderDetail;
import com.example.backend.Model.Repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // Crear un nuevo detalle de orden
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    // Obtener todos los detalles de órdenes
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    // Obtener detalle de orden por ID
    public OrderDetail getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    // Eliminar detalle de orden por ID
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }
}
