package br.com.indra.jusley_freitas.repository;

import br.com.indra.jusley_freitas.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Product findBySku(String sku);

    Product findByName(String name);

    Product findByIdAndActiveTrue(UUID id);

    List<Product> findAllByActiveTrue();

    @Query("SELECT p FROM Product p JOIN FETCH p.subCategory WHERE p.subCategory.id = :subCategoryId")
    List<Product> findBySubCategoryId(UUID subCategoryId);

    @Query("SELECT p FROM Product p JOIN FETCH p.subCategory sc JOIN FETCH sc.category c WHERE c.id = :categoryId")
    List<Product> findAllByCategoryId(UUID categoryId);

}
