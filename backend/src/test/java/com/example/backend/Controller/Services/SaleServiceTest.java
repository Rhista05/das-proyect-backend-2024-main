/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.backend.Controller.Services;

/**
 *
 * @author User
 */

import com.example.backend.Model.Entities.Sale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SaleServiceTest {

    private SaleService saleService;
    private List<Sale> mockDatabase;

    @BeforeEach
    void setUp() {
        // Simulation data base
        mockDatabase = new ArrayList<>();

        
        saleService = new SaleService() {
            @Override
            public List<Sale> findAllSales() {
                return mockDatabase;
            }

            @Override
            public Optional<Sale> findSaleById(Long id) {
                return mockDatabase.stream().filter(sale -> sale.getId().equals(id)).findFirst();
            }

            @Override
            public Sale createSale(Sale sale) {
                sale.setId((long) (mockDatabase.size() + 1));
                sale.setSaleDate(LocalDate.now());
                mockDatabase.add(sale);
                return sale;
            }

            @Override
            public Sale updateSale(Sale sale) {
                Optional<Sale> existingSale = findSaleById(sale.getId());
                if (existingSale.isPresent()) {
                    mockDatabase.remove(existingSale.get());
                    mockDatabase.add(sale);
                    return sale;
                }
                return null;
            }

            @Override
            public void deleteSale(Long id) {
                mockDatabase.removeIf(sale -> sale.getId().equals(id));
            }
        };
    }

    @Test
    void testFindAllSales() {
        
        Sale sale1 = new Sale();
        sale1.setClient("Client 1");
        sale1.setTotal(100.0);
        saleService.createSale(sale1);

        Sale sale2 = new Sale();
        sale2.setClient("Client 2");
        sale2.setTotal(200.0);
        saleService.createSale(sale2);

        
        List<Sale> sales = saleService.findAllSales();
        assertEquals(2, sales.size(), "El número de ventas no coincide");
    }

    @Test
    void testFindSaleById() {
        
        Sale sale = new Sale();
        sale.setClient("Test Client");
        sale.setTotal(150.0);
        Sale createdSale = saleService.createSale(sale);

        
        Optional<Sale> foundSale = saleService.findSaleById(createdSale.getId());
        assertTrue(foundSale.isPresent(), "No se encontró la venta");
        assertEquals("Test Client", foundSale.get().getClient(), "El cliente no coincide");
    }

    @Test
    void testCreateSale() {
        Sale sale = new Sale();
        sale.setClient("New Client");
        sale.setTotal(250.0);

        Sale createdSale = saleService.createSale(sale);

        assertNotNull(createdSale.getId(), "El ID de la venta no fue asignado");
        assertEquals("New Client", createdSale.getClient(), "El cliente no coincide");
    }

    @Test
    void testUpdateSale() {
        Sale sale = new Sale();
        sale.setClient("Original Client");
        sale.setTotal(300.0);
        Sale createdSale = saleService.createSale(sale);

        createdSale.setClient("Updated Client");
        Sale updatedSale = saleService.updateSale(createdSale);

        assertNotNull(updatedSale, "La venta no fue actualizada");
        assertEquals("Updated Client", updatedSale.getClient(), "El cliente no fue actualizado correctamente");
    }

    @Test
    void testDeleteSale() {
        Sale sale = new Sale();
        sale.setClient("Delete Client");
        sale.setTotal(400.0);
        Sale createdSale = saleService.createSale(sale);

        saleService.deleteSale(createdSale.getId());

        Optional<Sale> deletedSale = saleService.findSaleById(createdSale.getId());
        assertTrue(deletedSale.isEmpty(), "La venta no fue eliminada correctamente");
    }
}