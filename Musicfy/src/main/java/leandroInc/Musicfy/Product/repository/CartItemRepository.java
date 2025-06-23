package leandroInc.Musicfy.Product.repository;

import leandroInc.Musicfy.Product.model.Cart;
import leandroInc.Musicfy.Product.model.CartItem;
import leandroInc.Musicfy.Product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Método principal usando IDs
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
    Optional<CartItem> findByCartIdAndProductId(@Param("cartId") Long cartId,
                                                @Param("productId") Long productId);

    // Método alternativo usando objetos (opcional)
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    // REMOVA o método abaixo pois causa o erro de comparação
    // Optional<CartItem> findUniqueByCartAndProduct(Long id, Long productId);
}