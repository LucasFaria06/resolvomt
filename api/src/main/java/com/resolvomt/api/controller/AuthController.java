package com.resolvomt.api.controller;

import com.resolvomt.api.dto.cliente.ClienteRegisterRequestDTO;
import com.resolvomt.api.dto.jwt.JwtResponseDTO;
import com.resolvomt.api.dto.jwt.LoginRequestDTO;
import com.resolvomt.api.dto.prestador.PrestadorRegisterRequestDTO;
import com.resolvomt.api.model.Cliente;
import com.resolvomt.api.security.JwtTokenProvider;
import com.resolvomt.api.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final ClienteService clienteService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, ClienteService clienteService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.clienteService = clienteService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.email(),
                            loginRequest.senha()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);

            return ResponseEntity.ok(new JwtResponseDTO(jwt));

        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou senha inválidos.");
        }
    }

    @PostMapping("/register/client")
    public ResponseEntity<?> registerClient(@RequestBody ClienteRegisterRequestDTO dto) {
        try {
            Cliente novoCliente = clienteService.registrar(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    "Cliente " + novoCliente.getUsuario().getNomeCompleto() + " registrado com sucesso!"
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar cliente!");
        }
    }

    @PostMapping("/register/prestador")
    public ResponseEntity<?> registerPrestador(@RequestBody PrestadorRegisterRequestDTO dto){
        prestadorService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Prestador registrado com sucesso!");
    }
}