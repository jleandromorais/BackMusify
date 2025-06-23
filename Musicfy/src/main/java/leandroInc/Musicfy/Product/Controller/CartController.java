package leandroInc.Musicfy.Product.Controller;

import jakarta.persistence.EntityNotFoundException;

import leandroInc.Musicfy.Product.Service.CartService;
import leandroInc.Musicfy.Product.dto.ItemCarrinhoDTO;
import leandroInc.Musicfy.Product.dto.ItemCarrinhoRequestDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{cartId}/adicionar")
    public ResponseEntity<?> adicionarItem(
            @PathVariable Long cartId,
            @RequestBody ItemCarrinhoRequestDTO request
    ) {
        try {
            var item = cartService.adicionarItemAoCarrinho(cartId, request.getProductId(), request.getQuantity());
            return ResponseEntity.ok(new ItemCarrinhoDTO(item));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Dados inválidos: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            // Trata violação de constraint única
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Item já existe no carrinho");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro interno: " + e.getMessage());
        }
    }
}