package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.config.LoggerConfig;
import br.com.indra.jusley_freitas.dto.response.HistoryProductResponseDTO;
import br.com.indra.jusley_freitas.mapper.PriceHistoryMapper;
import br.com.indra.jusley_freitas.model.PriceHistory;
import br.com.indra.jusley_freitas.repository.PriceHistoryRepository;
import br.com.indra.jusley_freitas.service.PriceHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PriceHistoryServiceImplement implements PriceHistoryService {

    private final PriceHistoryRepository historyRepository;

    @Transactional(readOnly = true)
    public List<HistoryProductResponseDTO> getHistoryByProductId(UUID productId) {
        List<PriceHistory> priceHistories = historyRepository.findByProductsId(productId);

        LoggerConfig.LOGGER_PRICE_HISTORY.info(" Price history of the successfully returned product!");
        return PriceHistoryMapper.toResponseList(priceHistories);
    }
}
