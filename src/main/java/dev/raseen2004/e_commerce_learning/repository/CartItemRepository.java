package dev.raseen2004.e_commerce_learning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.raseen2004.e_commerce_learning.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndProductId(
        Long cardId,
        Long productId
    );
}
