package br.com.indra.jusley_freitas.config;

import br.com.indra.jusley_freitas.exception.handler.CustomizeResponseEntityExceptionHandler;
import br.com.indra.jusley_freitas.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerConfig {

    public static final Logger LOGGER_PRODUCT = LoggerFactory.getLogger(ProductService.class);
    public static final Logger LOGGER_PRICE_HISTORY = LoggerFactory.getLogger(PriceHistoryService.class);
    public static final Logger LOGGER_INVENTORY_TRANSACTION = LoggerFactory.getLogger(InventoryTransactionService.class);
    public static final Logger LOGGER_CATEGORY = LoggerFactory.getLogger(CategoryService.class);
    public static final Logger LOGGER_SUB_CATEGORY = LoggerFactory.getLogger(SubCategoryService.class);
    public static final Logger LOGGER_EXCEPTION = LoggerFactory.getLogger(CustomizeResponseEntityExceptionHandler.class);

}
