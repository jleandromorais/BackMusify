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
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/adicionar")
    public ResponseEntity<?> adicionarItem(@RequestBody ItemCarrinhoRequestDTO request) {
        try {
            // Validação básica dos dados de entrada
            if (request.getProductId() == null || request.getQuantity() <= 0) {
                return ResponseEntity.badRequest().body("Dados do produto inválidos");
            }

            // Chama o serviço modificado (sem cartId)
            var item = cartService.adicionarItemAoCarrinho(request.getProductId(), request.getQuantity());

            // Retorna o item adicionado com status 200
            return ResponseEntity.ok(new ItemCarrinhoDTO(item));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Item já existe no carrinho");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao processar requisição: " + e.getMessage());
        }
    }

    // Outros endpoints do carrinho (opcionais)

}