package com.resolvomt.api.controller.auth;

import com.resolvomt.api.dto.cliente.ClienteRegisterRequestDTO;
import com.resolvomt.api.dto.jwt.JwtResponseDTO;
import com.resolvomt.api.dto.jwt.LoginRequestDTO;
import com.resolvomt.api.dto.prestador.PrestadorRegisterRequestDTO;
import com.resolvomt.api.model.Cliente;
import com.resolvomt.api.model.Prestador;
import com.resolvomt.api.security.JwtTokenProvider;
import com.resolvomt.api.service.ClienteService;
import com.resolvomt.api.service.PrestadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final PrestadorService prestadorService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider,
                          ClienteService clienteService,
                          PrestadorService prestadorService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.clienteService = clienteService;
        this.prestadorService = prestadorService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.senha()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtResponseDTO(jwt));
    }

    @PostMapping("/register/client")
    public ResponseEntity<?> registerClient(@Valid @RequestBody ClienteRegisterRequestDTO dto) {
        Cliente novoCliente = clienteService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                "Cliente " + novoCliente.getUsuario().getNomeCompleto() + " registrado com sucesso!"
        );
    }

    @PostMapping("/register/prestador")
    public ResponseEntity<?> registerPrestador(@Valid @RequestBody PrestadorRegisterRequestDTO dto){
        try {
            Prestador novoPrestador = prestadorService.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Prestador " + novoPrestador.getNome() + " registrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}