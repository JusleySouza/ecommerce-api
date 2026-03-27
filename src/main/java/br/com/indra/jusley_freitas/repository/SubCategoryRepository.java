package br.com.indra.jusley_freitas.repository;

import br.com.indra.jusley_freitas.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {

    boolean existsByNameAndCategoryId(String name, UUID categoryId);

    List<SubCategory> findByCategoryId(UUID categoryId);

}
