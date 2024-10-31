package br.unitins.tp1.notebooks;

import br.unitins.tp1.notebooks.dto.UsuarioRequestDTO;
import br.unitins.tp1.notebooks.dto.UsuarioResponseDTO;
import br.unitins.tp1.notebooks.repository.UsuarioRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import jakarta.transaction.Transactional;
import br.unitins.tp1.notebooks.service.UsuarioService;

@QuarkusTest
public class UsuarioServiceTest {

    @Inject
    UsuarioService usuarioService;

    @Inject
    UsuarioRepository usuarioRepository;

    @Test
    @Transactional
    public void testCreateUsuario() {

        UsuarioRequestDTO usuarioDTO = new UsuarioRequestDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setEmail("joao@example.com");
        usuarioDTO.setSenha("senha123");

        UsuarioResponseDTO usuarioCriado = usuarioService.create(usuarioDTO);

        Assertions.assertNotNull(usuarioCriado);
        Assertions.assertEquals("João", usuarioCriado.getNome());
        Assertions.assertEquals("joao@example.com", usuarioCriado.getEmail());

        UsuarioResponseDTO usuarioBuscado = usuarioService.findById(usuarioCriado.getId());
        Assertions.assertNotNull(usuarioBuscado);
        Assertions.assertEquals("João", usuarioBuscado.getNome());
        Assertions.assertEquals("joao@example.com", usuarioBuscado.getEmail());
    }

    @Test
    @Transactional
    public void testFindById() {

        UsuarioRequestDTO usuarioDTO = new UsuarioRequestDTO();
        usuarioDTO.setNome("Maria");
        usuarioDTO.setEmail("maria@example.com");
        usuarioDTO.setSenha("senha456");

        UsuarioResponseDTO usuarioCriado = usuarioService.create(usuarioDTO);

        UsuarioResponseDTO usuarioBuscado = usuarioService.findById(usuarioCriado.getId());

        Assertions.assertNotNull(usuarioBuscado);
        Assertions.assertEquals("Maria", usuarioBuscado.getNome());
        Assertions.assertEquals("maria@example.com", usuarioBuscado.getEmail());
    }
}
