package br.unitins.tp1.notebooks.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.notebooks.dto.UsuarioResponseDTO;
import br.unitins.tp1.notebooks.modelo.Usuario;
import br.unitins.tp1.notebooks.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UsuarioResponseDTO create(String nome, String email, String senha) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuarioRepository.persist(usuario);
        return UsuarioResponseDTO.valueOf(usuario); // Retorna o DTO do usuário criado
    }

    @Override
    @Transactional
    public void update(Long id, String nome, String email, String senha) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }
        usuarioRepository.delete(usuario);
    }

    @Override
    public List<UsuarioResponseDTO> findByName(String nome) {
        List<Usuario> usuarios = usuarioRepository.findByName(nome);
        return usuarios.stream()
                .map(UsuarioResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> listAll() {
        return usuarioRepository.listAll().stream()
                .map(UsuarioResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public Usuario findByUsernameAndSenha(String username, String senha) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            return null;
        }
        return usuario;
    }
}
