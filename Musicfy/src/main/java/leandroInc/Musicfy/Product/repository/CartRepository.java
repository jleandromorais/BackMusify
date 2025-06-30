package leandroInc.Musicfy.Product.repository;

import leandroInc.Musicfy.Product.model.Cart;
import leandroInc.Musicfy.Product.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface CartRepository extends JpaRepository<Cart, Long> {

        // Método correto para buscar um item específico no carrinho
        @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
        Optional<CartItem> findCartItemByCartIdAndProductId(@Param("cartId") Long cartId,
                                                            @Param("productId") Long productId);

        // Ou alternativamente, se quiser buscar o carrinho inteiro:
        Optional<Cart> findById(Long cartId);

    Optional<Cart> findFirstByOrderByIdDesc();

    Optional<Cart> findByUserId(Long userId);
}
