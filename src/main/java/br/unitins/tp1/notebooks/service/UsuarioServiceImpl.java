// package br.unitins.tp1.notebooks.service;

// import br.unitins.tp1.notebooks.dto.UsuarioRequestDTO;
// import br.unitins.tp1.notebooks.dto.UsuarioResponseDTO;
// import br.unitins.tp1.notebooks.modelo.Usuario;
// import br.unitins.tp1.notebooks.repository.UsuarioRepository;

// import jakarta.enterprise.context.ApplicationScoped;
// import jakarta.inject.Inject;
// import jakarta.transaction.Transactional;
// import java.util.List;
// import java.util.stream.Collectors;

// @ApplicationScoped
// public class UsuarioServiceImpl implements UsuarioService {

//     @Inject
//     UsuarioRepository usuarioRepository;

//     @Override
//     @Transactional
//     public UsuarioResponseDTO create(UsuarioRequestDTO usuarioDTO) {
//         Usuario usuario = new Usuario(
//                 usuarioDTO.getNome(),
//                 usuarioDTO.getEmail(),
//                 usuarioDTO.getSenha()
//         );
//         usuarioRepository.persist(usuario);

 
//         return UsuarioResponseDTO.valueOf(usuario);
//     }

//     @Override
//     public UsuarioResponseDTO findById(Long id) {
//         Usuario usuario = usuarioRepository.findById(id);
//         if (usuario == null) {
//             return null;  
//         }
//         return UsuarioResponseDTO.valueOf(usuario); // Usa valueOf
//     }  

//         @Override
//     public List<UsuarioResponseDTO> findByName(String nome) {
//         List<Usuario> usuarios = usuarioRepository.findByName(nome);
//         return usuarios.stream()
//                 .map(UsuarioResponseDTO::valueOf)
//                 .collect(Collectors.toList());
//     }

//     @Override
//     public UsuarioResponseDTO buscarUsuario(Long id) {
//         Usuario usuario = usuarioRepository.findById(id);
//         if (usuario == null) {
//             return null;  
//         }
//         return UsuarioResponseDTO.valueOf(usuario); // Usa valueOf
//     }

//     @Override
//     @Transactional
//     public void update(Long id, UsuarioRequestDTO usuarioDTO) {
//         Usuario usuario = usuarioRepository.findById(id);
//         if (usuario != null) {
//             usuario.setNome(usuarioDTO.getNome());
//             usuario.setEmail(usuarioDTO.getEmail());
//             usuario.setSenha(usuarioDTO.getSenha());
//             usuarioRepository.persist(usuario);
//         }
//     }

//     @Override
//     @Transactional
//     public void delete(Long id) {
//         Usuario usuario = usuarioRepository.findById(id);
//         if (usuario != null) {
//             usuarioRepository.delete(usuario);
//         }
//     }

//     @Override
//     public List<UsuarioResponseDTO> listAll() {
//         List<Usuario> usuarios = usuarioRepository.listAll();
//         return usuarios.stream()
//                 .map(UsuarioResponseDTO::valueOf)  
//                 .toList();
//     }

    
// }
