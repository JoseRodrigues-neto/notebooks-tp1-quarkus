package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.FuncionarioRequestDTO;
import br.unitins.tp1.notebooks.dto.FuncionarioResponseDTO;
import java.util.List;

public interface FuncionarioService {
    FuncionarioResponseDTO create(FuncionarioRequestDTO dto);
    FuncionarioResponseDTO findById(Long id);
    void update(Long id, FuncionarioRequestDTO funcionarioDTO);
    void delete(Long id);
    List<FuncionarioResponseDTO> listAll(); 
    List<FuncionarioResponseDTO> findByName(String name);
}
