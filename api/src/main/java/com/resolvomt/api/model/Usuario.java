package com.resolvomt.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Table(name= "usuarios")
public class Usuario implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column
    private String nomeCompleto;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String senha;

    @Column(name = "tipo_usuario", nullable = false)
    private String tipoUsuario;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro = LocalDateTime.now();

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
   }

   @Override
   public String getPassword(){
    return senha;
   }

   @Override
   public String getUsername(){
    return email;
   }

   @Override
   public boolean isAccountNonExpired(){
    return true;
   }

   @Override
   public boolean isAccountNonLocked(){
    return true;
   }

   @Override
   public boolean isCredentialsNonExpired(){
    return true;
   }

   @Override
   public boolean isEnabled(){
    return true;
   }
}
