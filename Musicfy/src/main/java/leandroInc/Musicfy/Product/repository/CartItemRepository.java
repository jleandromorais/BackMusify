package leandroInc.Musicfy.Product.repository;

import leandroInc.Musicfy.Product.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Método seguro que retorna apenas um resultado
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
    Optional<CartItem> findUniqueByCartAndProduct(
            @Param("cartId") Long cartId,
            @Param("productId") Long productId
    );

    // Método alternativo usando LIMIT 1
    @Query(value = "SELECT * FROM cart_titems WHERE cart_id = :cartId AND product_id = :productId LIMIT 1",
            nativeQuery = true)
    Optional<CartItem> findFirstByCartAndProduct(
            @Param("cartId") Long cartId,
            @Param("productId") Long productId
    );
}