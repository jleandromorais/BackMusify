package leandroInc.Musicfy.Product.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import leandroInc.Musicfy.Product.dto.UserDTO;
import leandroInc.Musicfy.Product.model.Usuario;
import leandroInc.Musicfy.Product.model.Cart;
import leandroInc.Musicfy.Product.repository.CartRepository;
import leandroInc.Musicfy.Product.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CartRepository cartRepository;
    private final FirebaseAuth firebaseAuth;

    public UsuarioService(UsuarioRepository usuarioRepository, CartRepository cartRepository, FirebaseAuth firebaseAuth) {
        this.usuarioRepository = usuarioRepository;
        this.cartRepository = cartRepository;
        this.firebaseAuth = firebaseAuth;
    }

    @Transactional
    public void criarUsuarioComCarrinho(String token, UserDTO userDTO) throws FirebaseAuthException {
        // Verifica o token via Firebase
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(token);
        String uid = decodedToken.getUid();

        // Pode adicionar lógica de segurança, por exemplo:
        if (!userDTO.getFirebaseUid().equals(uid)) {
            throw new SecurityException("UID do token não corresponde ao UID do usuário");
        }



        // Criar usuario
        Usuario usuario = new Usuario();
        usuario.setId(userDTO.getId());
        usuario.setName(userDTO.getFullName());
        usuario.setEmail(userDTO.getEmail());
        // setar outros campos conforme seu DTO...

        usuarioRepository.save(usuario);

        // Criar carrinho vinculado usuário
        Cart cart = new Cart();
        cart.setUser(usuario);
        cartRepository.save(cart);
    }
}