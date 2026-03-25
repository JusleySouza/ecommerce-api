package br.com.indra.jusley_freitas.repository;

import br.com.indra.jusley_freitas.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {

    boolean existsByNameAndCategoryId(String name, UUID categoryId);

    @Query("SELECT sc FROM SubCategory sc JOIN FETCH sc.category WHERE sc.category.id = :categoryId")
    List<SubCategory> findByCategoryId(UUID categoryId);

    boolean existsByNameAndCategoryIdAndId(String name, UUID categoryId, UUID id);

}
