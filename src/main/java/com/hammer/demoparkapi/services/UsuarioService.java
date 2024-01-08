package com.hammer.demoparkapi.services;

import com.hammer.demoparkapi.entities.Usuario;
import com.hammer.demoparkapi.exception.EntityNotFoundException;
import com.hammer.demoparkapi.exception.PasswordInvalidException;
import com.hammer.demoparkapi.exception.UsernameUniqueViolationException;
import com.hammer.demoparkapi.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);
        }catch (DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(String.format("Username {%s} já cadastrado", usuario.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow( () -> new EntityNotFoundException(String.format("Usuario id=%s não encontrado!", id)));
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
        }

        Usuario user = buscarPorId(id);
        if (!passwordEncoder.matches(senhaAtual, user.getPassword())) {
            throw new PasswordInvalidException("Sua senha não confere.");
        }

        user.setPassword(passwordEncoder.encode(novaSenha));
        return user;
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return this.usuarioRepository.findByUsername(username)
                .orElseThrow( () -> new EntityNotFoundException(String.format("Usuario com '%s' não encontrado!", username)));
    }

    @Transactional(readOnly = true)
    public Usuario.Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }
}


/*
@Transactional -> Spring vai cuidar da transação, abrir, fechar e gerenciar. Usado para metodos de consulta.
@RequiredArgsConstructor -> Serve para o Lombok crie um metodo construtor com a variavel usuario repository.
(readOnly = true) -> indica para o spring que esse metodo é exclusivo para uma consulta no banco de dados.

String.format -> funciona de uma forma muito parecida com o print F, então vai aparecer aqui username (digitado) já cadastrado
 */