package leandroInc.Musicfy.Product.repository;

import leandroInc.Musicfy.Product.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Métodos customizados, se necessário
}
