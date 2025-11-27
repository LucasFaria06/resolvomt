package com.resolvomt.api.controller;

import com.resolvomt.api.dto.LoginRequest;
import com.resolvomt.api.dto.TokenResponse;
import com.resolvomt.api.infra.security.TokenService;
import com.resolvomt.api.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager; // O "Gerente" que autentica

    @Autowired
    private TokenService tokenService; // A nossa impressora de pulseiras

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid LoginRequest dados) {
        // 1. Cria um DTO do próprio Spring com login/senha
        var tokenAutenticacao = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

        // 2. O Gerente tenta autenticar (bate no banco, checa hash da senha)
        var autenticacao = manager.authenticate(tokenAutenticacao);

        // 3. Se deu certo, pegamos o usuário logado
        var usuario = (Usuario) autenticacao.getPrincipal();

        // 4. Geramos a pulseira (Token JWT) pra ele
        var tokenJWT = tokenService.gerarToken(usuario);

        // 5. Devolvemos o token pro Front-end
        return ResponseEntity.ok(new TokenResponse(tokenJWT));
    }

}
