package com.example.backend.Controller.Services;

import com.example.backend.Model.Entities.DetailSale;
import com.example.backend.Model.Entities.Sale;
import com.example.backend.Model.Repositories.DetailSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DetailSaleService {

    @Autowired
    private DetailSaleRepository detailSaleRepository;

    public List<DetailSale> findAll() {
        return detailSaleRepository.findAll();
    }

    public DetailSale findById(Long id) {
        return detailSaleRepository.findById(id).orElse(null);
    }

    public DetailSale createDetailSale(DetailSale detailSale) {
        return detailSaleRepository.save(detailSale);
    }

    public void deleteDetailSale(Long id) {
        detailSaleRepository.deleteById(id);
    }
    
    public List<DetailSale> findBySale(Sale sale) {
        return detailSaleRepository.findBySale(sale);
    }
    
    public List<DetailSale> getDetailsBySaleId(Long saleId) {
        return detailSaleRepository.findBySaleId(saleId);
    }
}
