package br.unitins.tp1.notebooks.service;

import java.util.List;
import br.unitins.tp1.notebooks.dto.UsuarioRequestDTO;
import br.unitins.tp1.notebooks.dto.UsuarioResponseDTO;

public interface UsuarioService {
    UsuarioResponseDTO create(UsuarioRequestDTO dto);  
    UsuarioResponseDTO buscarUsuario(Long id); 
    void update(Long id, UsuarioRequestDTO dto); 
    void delete(Long id);  
    List<UsuarioResponseDTO> findByName(String nome);
    List<UsuarioResponseDTO> listAll();  
    UsuarioResponseDTO findById(Long id);
    
}
