package br.com.indra.jusley_freitas.service;

import br.com.indra.jusley_freitas.dto.response.HistoryProductResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PriceHistoryService {

    public List<HistoryProductResponseDTO> getHistoryByProductId(UUID productId);

}
