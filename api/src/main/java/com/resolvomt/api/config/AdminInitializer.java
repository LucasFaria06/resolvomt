package com.resolvomt.api.config;

import com.resolvomt.api.enums.TipoUsuario;
import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AdminInitializer {

    @Bean
    public CommandLineRunner createAdminUser(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
      return args -> {
        if (repository.findByEmail("admin@resolvomt.com").isEmpty()) {

            Usuario admin = new Usuario();
            admin.setNomeCompleto("Administrador Master");
            admin.setEmail("admin@resolvomt.com");
            admin.setSenha(passwordEncoder.encode("senhaforte123"));
            admin.setTipoUsuario(TipoUsuario.ADMIN);

            repository.save(admin);
            System.out.println(">>> Usuário ADMIN padrão criado: admin@resolvomt.com / senhaforte123 <<<");
        }
     };
    }
}

