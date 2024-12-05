package br.unitins.tp1.notebooks.service;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp1.notebooks.dto.AlteraSenhaDTO;
import br.unitins.tp1.notebooks.dto.AlteraUserNameDTO;
import br.unitins.tp1.notebooks.dto.UsuarioResponseDTO;

import br.unitins.tp1.notebooks.modelo.Usuario;
import br.unitins.tp1.notebooks.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import br.unitins.tp1.notebooks.validation.ValidationException;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    JsonWebToken jwt;

    @Inject
    HashService hash; 

    @Inject
    Logger logger;

    @Override
    public List<UsuarioResponseDTO> findByName(String nome) {
        validarNome(nome);
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
    public Usuario findById(Long id) {
        validarId(id);
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }
        return usuario;  
    }

    @Override
    public Usuario findByUsernameAndSenha(String username, String senha) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            return null;
        }
        return usuario;
    }

    @Override
    @Transactional
    public Usuario alteraSenha(AlteraSenhaDTO alteraSenhaDTO) {
        String usernameAutenticado = jwt.getName();

        if (!alteraSenhaDTO.userName().equals(usernameAutenticado)) {
            throw new ValidationException("Usuario","usuario não autenticado");
        }

        
        Usuario usuario = usuarioRepository.findByUsername(alteraSenhaDTO.userName());
        if (usuario == null) {
            throw new ValidationException("userName","Usuario username não existe");
        }

       
        if (!hash.getHashSenha(alteraSenhaDTO.senhaAntiga()).equals(usuario.getSenha())) {
            throw new ValidationException("SenhaAntiga","senha incorreta");
        }

        String senhaNovaHash = hash.getHashSenha(alteraSenhaDTO.senhaNova());

        usuario.setSenha(senhaNovaHash);

    
        usuarioRepository.persist(usuario);

        return usuario;
    }

    @Override
    @Transactional
    public Usuario alteraUsername(AlteraUserNameDTO alteraUsernameDTO) {
 
        String usernameAutenticado = jwt.getName();

        if (usernameAutenticado == null) {
            throw new IllegalStateException("Usuário autenticado não encontrado.");
        }

      
        Usuario usuario = usuarioRepository.findByUsername(usernameAutenticado);
        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        if (!hash.getHashSenha(alteraUsernameDTO.senha()).equals(usuario.getSenha())) {
            throw new IllegalArgumentException("A senha incorreta.");
        }

        Usuario usuarioExistente = usuarioRepository.findByUsername(alteraUsernameDTO.novoUserName());
        if (usuarioExistente != null) {
            throw new IllegalArgumentException("O nome de usuário já está em uso.");
        }

        usuario.setUsername(alteraUsernameDTO.novoUserName());

     
        usuarioRepository.persist(usuario);

       
        return usuario;
    }

    @Transactional
    public void alteraEmail(String novoEmail) {
        
        String usernameAutenticado = jwt.getName();

        if (usernameAutenticado == null) {
            throw new IllegalStateException("Usuário autenticado não encontrado.");
        }
 
        Usuario usuario = usuarioRepository.findByUsername(usernameAutenticado);
        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

     
        usuario.setEmail(novoEmail);
        usuarioRepository.persist(usuario);

 
    }

    @Override
    @Transactional
    public void AlteraNome(String novoNome) {

        String usernameAutenticado = jwt.getName();
       
        Usuario usuario = usuarioRepository.findByUsername(usernameAutenticado);
        if (usuario != null) {

            usuario.setNome(novoNome);
            usuarioRepository.persist(usuario);
        } else {
            throw new NotFoundException("Cliente não encontrado.");
        }
    }

    private void validarId(long id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null)
            throw new ValidationException("id", "Cliente com o ID fornecido não encontrado.");
    }

    private void validarNome(String nome) {
        Usuario usuario = usuarioRepository.findByNameUnico(nome);
        if (usuario == null)
            throw new ValidationException("nome", "Cliente com o nome fornecido não encontrado.");
    }

    public Usuario findByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        return usuario;
    }

}
