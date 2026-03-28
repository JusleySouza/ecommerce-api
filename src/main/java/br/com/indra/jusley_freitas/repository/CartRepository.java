package br.com.indra.jusley_freitas.repository;

import br.com.indra.jusley_freitas.enums.CartStatus;
import br.com.indra.jusley_freitas.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    Optional<Cart> findByUserIdAndStatus(UUID userId, CartStatus status);

    Optional<Cart> findByUserId(UUID userId);

}
