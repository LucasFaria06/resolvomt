package com.resolvomt.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()) // Desabilita proteção contra ataques de form (não precisa em API)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Não guarda sessão, usa Token
                .authorizeHttpRequests(req -> {
                    // --- REGRAS DE ACESSO ---
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll(); // Login é público (óbvio)
                    req.requestMatchers(HttpMethod.POST, "/usuarios").permitAll(); // Cadastro é público
                    req.anyRequest().authenticated(); // O resto TEM que estar logado
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona nosso filtro antes do padrão
                .build();
    }

    @Bean // Exporta o AuthenticationManager para o Controller usar
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean // Ensina o Spring a usar hash BCrypt nas senhas
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}