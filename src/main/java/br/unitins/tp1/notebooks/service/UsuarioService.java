package br.unitins.tp1.notebooks.service;

import java.util.List;
import br.unitins.tp1.notebooks.modelo.Usuario;
import br.unitins.tp1.notebooks.dto.UsuarioResponseDTO;

public interface UsuarioService {
    UsuarioResponseDTO create(String nome, String email, String senha); 
    void update(Long id, String nome, String email, String senha);  
    void delete(Long id);  
    List<UsuarioResponseDTO> findByName(String nome);
    List<UsuarioResponseDTO> listAll();  
    UsuarioResponseDTO findById(Long id);
    Usuario findByUsernameAndSenha(String username, String senha);  
}
