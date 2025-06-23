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
        // 1. Validações básicas
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }

        // 2. Busca ou cria carrinho ativo
        Cart carrinho = obterCarrinhoAtivo();

        // 3. Busca o produto
        Product produto = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + productId));

        // 4. Verifica se item já existe no carrinho
        Optional<CartItem> itemExistente = cartItemRepository.findByCartIdAndProductId(carrinho.getId(), productId);

        if (itemExistente.isPresent()) {
            // Atualiza quantidade se item já existir
            CartItem item = itemExistente.get();
            item.setQuantity(item.getQuantity() + quantidade);
            return cartItemRepository.save(item);
        } else {
            // Cria novo item se não existir
            CartItem novoItem = new CartItem(produto, quantidade);
            novoItem.setCart(carrinho);
            return cartItemRepository.save(novoItem);
        }
    }

    // Método para obter ou criar carrinho ativo
    @Transactional
    public Cart obterCarrinhoAtivo() {
        // Implementação básica - ajuste conforme sua lógica de negócio
        // Pode ser:
        // 1. Carrinho da sessão do usuário
        // 2. Carrinho não finalizado do usuário autenticado
        // 3. Último carrinho criado (como está abaixo)

        return cartRepository.findFirstByOrderByIdDesc()
                .orElseGet(() -> cartRepository.save(new Cart()));
    }

    // Mantenha outros métodos existentes se necessário
}