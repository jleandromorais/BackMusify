package leandroInc.Musicfy.Product.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import leandroInc.Musicfy.Product.model.CartItem;
import leandroInc.Musicfy.Product.model.Product;
import leandroInc.Musicfy.Product.repository.CartItemRepository;
import leandroInc.Musicfy.Product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    public Product save(Product product) {
        return productRepository.save(product);
    }



    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> update(Long id, Product productDetails) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(productDetails.getName());
                    existingProduct.setImgPath(productDetails.getImgPath());
                    existingProduct.setSubtitle(productDetails.getSubtitle());
                    existingProduct.setFeatures(productDetails.getFeatures());
                    existingProduct.setPrice(productDetails.getPrice());
                    return productRepository.save(existingProduct);
                });
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
    // No ProductService
    public Product findById(Long id) {
        return productRepository.findById(id) // Retorna Optional<Product>
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
    @Transactional
    public void atualizarQuantidade(Long productId, int quantidade) {
        // Verifica se o produto existe primeiro
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Produto ID " + productId + " não existe");
        }

        // Busca o item no carrinho
        List<CartItem> items = cartItemRepository.findByProductId(productId);
        CartItem item = items.stream().findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Item do produto ID " + productId + " não encontrado no carrinho"));

        item.setQuantity(quantidade);
        // @Transactional garante o save automático
    }
}