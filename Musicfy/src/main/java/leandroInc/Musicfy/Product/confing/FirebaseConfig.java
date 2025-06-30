package leandroInc.Musicfy.Product.confing;// leandroInc.Musicfy.Product.confing.FirebaseConfig.java

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource; // 1. Importe a classe correta

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream; // 2. Use InputStream

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            // 3. Carregue o arquivo como um recurso do classpath
            InputStream serviceAccount = new ClassPathResource("serviceAccountKey.json").getInputStream();

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) { // Evita reinicialização
                FirebaseApp.initializeApp(options);
            }

        } catch (IOException e) {
            // Trate a exceção de forma mais específica
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }

    @Bean
    public FirebaseAuth firebaseAuth() {
        // Este bean agora será criado corretamente
        return FirebaseAuth.getInstance();
    }
}