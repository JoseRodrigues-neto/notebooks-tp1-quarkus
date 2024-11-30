// // package br.unitins.tp1.notebooks.resources;

// // import static org.junit.jupiter.api.Assertions.*;

// // import java.util.List;

// // import org.junit.jupiter.api.Test;

// // import br.unitins.tp1.notebooks.dto.UsuarioResponseDTO;
// // import br.unitins.tp1.notebooks.modelo.Usuario;
// // import br.unitins.tp1.notebooks.service.UsuarioService;
// // import io.quarkus.test.junit.QuarkusTest;
// // import jakarta.inject.Inject;
// // import jakarta.transaction.Transactional;

// // @QuarkusTest
// // public class UsuarioServiceImplTest {

// //     @Inject
// //     UsuarioService usuarioService;

// //     @Test
// //     @Transactional
// //     public void testCreateUsuario() {
// //         // Criação de um novo usuário
// //         Usuario usuario = usuarioService.create("João", "joao@example.com", "senha123");
// //         assertNotNull(usuario);
// //         assertEquals("João", usuario.getNome());
// //         assertEquals("joao@example.com", usuario.getEmail());
// //     }

// //     @Test
// //     @Transactional
// //     public void testUpdateUsuario() {
// //         // Criar um novo usuário
// //         Usuario usuario = usuarioService.create("Maria", "maria@example.com", "senha123");
// //         assertNotNull(usuario);

// //         // Atualizar o usuário
// //         usuarioService.update(usuario.getId(), "Maria Silva", "maria.silva@example.com", "novaSenha456");

// //         // Buscar o usuário atualizado
// //         Usuario usuarioAtualizado = usuarioService.findById(usuario.getId());
// //         assertEquals("Maria Silva", usuarioAtualizado.getNome());
// //         assertEquals("maria.silva@example.com", usuarioAtualizado.getEmail());
// //         assertEquals("novaSenha456", usuarioAtualizado.getSenha());
// //     }

// //     @Test
// //     @Transactional
// //     public void testDeleteUsuario() {
// //         // Criar um novo usuário
// //         Usuario usuario = usuarioService.create("Carlos", "carlos@example.com", "senha123");
// //         assertNotNull(usuario);

// //         // Excluir o usuário
// //         usuarioService.delete(usuario.getId());

// //         // Verificar que o usuário foi removido
// //         RuntimeException exception = assertThrows(RuntimeException.class, 
// //             () -> usuarioService.findById(usuario.getId())
// //         );
// //         assertEquals("Usuário não encontrado.", exception.getMessage());
// //     }

// //     @Test
// //     public void testFindByIdUsuario() {
// //         // Criar um novo usuário
// //         Usuario usuario = usuarioService.create("Ana", "ana@example.com", "senha123");
// //         assertNotNull(usuario);

// //         // Buscar pelo ID
// //         Usuario usuarioBuscado = usuarioService.findById(usuario.getId());
// //         assertNotNull(usuarioBuscado);
// //         assertEquals("Ana", usuarioBuscado.getNome());
// //         assertEquals("ana@example.com", usuarioBuscado.getEmail());
// //     }

// //     @Test
// //     public void testFindByNameUsuario() {
// //         // Criar múltiplos usuários
// //         usuarioService.create("Lucas", "lucas@example.com", "senha123");
// //         usuarioService.create("Lucas", "lucas.silva@example.com", "senha456");

// //         // Buscar usuários pelo nome
// //         List<UsuarioResponseDTO> usuarios = usuarioService.findByName("Lucas");
// //         assertNotNull(usuarios);
// //         assertEquals(2, usuarios.size());
// //     }
// // }
