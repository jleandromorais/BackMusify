package leandroInc.Musicfy.Product.Controller;

import jakarta.persistence.EntityNotFoundException;

import leandroInc.Musicfy.Product.Service.CartService;
import leandroInc.Musicfy.Product.dto.ItemCarrinhoDTO;
import leandroInc.Musicfy.Product.dto.ItemCarrinhoRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000") // ✅ Origem específica
@RestController
@RequestMapping("/carrinho")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/adicionar")
    public ResponseEntity<?> adicionarItem(
            @RequestBody ItemCarrinhoRequestDTO request
    ) {
        try {
            var item = cartService.adicionarItemAoCarrinho(request.getId(), request.getQuantity());
            return ResponseEntity.ok(new ItemCarrinhoDTO(item));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // ✅ Mensagem de erro
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}