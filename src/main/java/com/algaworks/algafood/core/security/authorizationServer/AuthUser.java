package com.algaworks.algafood.core.security.authorizationServer;

import com.algaworks.algafood.domain.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthUser extends User {

    private final String fullName;
    private final Long userId;

    public AuthUser(Usuario usuario, Collection<GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getSenha(), authorities);

        this.fullName = usuario.getNome();
        this.userId = usuario.getId();
    }
}
