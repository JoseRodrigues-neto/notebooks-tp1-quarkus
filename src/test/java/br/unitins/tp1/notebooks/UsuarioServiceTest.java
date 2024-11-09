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
        // Criação de um novo UsuarioRequestDTO usando o record
        UsuarioRequestDTO usuarioDTO = new UsuarioRequestDTO("João", "joao@example.com", "senha123");

        // Criação do usuário no serviço e recebimento do UsuarioResponseDTO
        UsuarioResponseDTO usuarioCriado = usuarioService.create(usuarioDTO);

        // Verificações
        Assertions.assertNotNull(usuarioCriado);
        Assertions.assertEquals("João", usuarioCriado.nome());
        Assertions.assertEquals("joao@example.com", usuarioCriado.email());

        // Busca pelo ID do usuário criado e validação das informações
        UsuarioResponseDTO usuarioBuscado = usuarioService.findById(usuarioCriado.id());
        Assertions.assertNotNull(usuarioBuscado);
        Assertions.assertEquals("João", usuarioBuscado.nome());
        Assertions.assertEquals("joao@example.com", usuarioBuscado.email());
    }

    @Test
    @Transactional
    public void testFindById() {
        // Criação de um novo UsuarioRequestDTO para teste
        UsuarioRequestDTO usuarioDTO = new UsuarioRequestDTO("Maria", "maria@example.com", "senha456");

        // Criação do usuário no serviço
        UsuarioResponseDTO usuarioCriado = usuarioService.create(usuarioDTO);

        // Busca pelo ID do usuário e validação das informações
        UsuarioResponseDTO usuarioBuscado = usuarioService.findById(usuarioCriado.id());

        Assertions.assertNotNull(usuarioBuscado);
        Assertions.assertEquals("Maria", usuarioBuscado.nome());
        Assertions.assertEquals("maria@example.com", usuarioBuscado.email());
    }
}
