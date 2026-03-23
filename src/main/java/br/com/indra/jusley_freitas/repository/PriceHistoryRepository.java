package br.com.indra.jusley_freitas.repository;

import br.com.indra.jusley_freitas.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, UUID> {

    @Query("SELECT h FROM PriceHistory h JOIN FETCH h.product WHERE h.product.id = :productId")
    List<PriceHistory> findByProductsId(UUID productId);

}
