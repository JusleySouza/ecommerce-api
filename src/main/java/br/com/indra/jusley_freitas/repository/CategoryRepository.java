package br.com.indra.jusley_freitas.repository;

import br.com.indra.jusley_freitas.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Category findByName(String name);

}
