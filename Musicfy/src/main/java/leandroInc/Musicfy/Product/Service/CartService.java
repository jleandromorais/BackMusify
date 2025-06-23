package leandroInc.Musicfy.Product.Service;

import jakarta.persistence.EntityNotFoundException;
import leandroInc.Musicfy.Product.model.*;
import leandroInc.Musicfy.Product.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CartItem adicionarItemAoCarrinho(Long productId, int quantidade) {
        // Validações
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }

        // Busca o produto
        Product produto = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        // Obtém o carrinho ativo
        Cart carrinho = obterCarrinhoAtivo();

        // Verifica se item já existe
        Optional<CartItem> itemExistente = cartItemRepository
                .findByCartIdAndProductId(carrinho.getId(), productId);

        if (itemExistente.isPresent()) {
            CartItem item = itemExistente.get();
            item.setQuantity(item.getQuantity() + quantidade);
            return cartItemRepository.save(item);
        } else {
            CartItem novoItem = new CartItem(produto, quantidade);
            novoItem.setCart(carrinho);
            carrinho.addItem(novoItem);
            return cartItemRepository.save(novoItem);
        }
    }

    private Cart obterCarrinhoAtivo() {
        // Implementação simplificada - em produção, associar ao usuário
        return cartRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> cartRepository.save(new Cart()));
    }
}