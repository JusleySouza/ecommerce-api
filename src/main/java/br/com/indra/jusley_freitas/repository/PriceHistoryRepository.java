package br.com.indra.jusley_freitas.repository;

import br.com.indra.jusley_freitas.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, UUID> {

    List<PriceHistory> findByProductId(UUID productId);

}
