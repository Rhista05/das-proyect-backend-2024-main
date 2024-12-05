package com.example.backend.Model.Repositories;

import com.example.backend.Model.Entities.DetailSale;
import com.example.backend.Model.Entities.Sale;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailSaleRepository extends JpaRepository<DetailSale, Long> {

    List<DetailSale> findBySale(Sale sale);

    List<DetailSale> findBySaleId(Long saleId);

    @Query("SELECT p.id, p.name, SUM(ds.quantity) AS totalSold "
            + "FROM DetailSale ds "
            + "JOIN ds.product p "
            + "GROUP BY p.id, p.name "
            + "ORDER BY totalSold DESC")
    List<Object[]> getProductRanking(Pageable pageable);
}
