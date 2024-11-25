package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.FuncionarioRequestDTO;
import br.unitins.tp1.notebooks.dto.FuncionarioResponseDTO;
import br.unitins.tp1.notebooks.modelo.Funcionario;
import java.util.List;

public interface FuncionarioService {
    Funcionario create(FuncionarioRequestDTO dto);
    Funcionario findById(Long id);
    void update(Long id, FuncionarioRequestDTO funcionarioDTO);
    void delete(Long id);
    List<FuncionarioResponseDTO> listAll(); 
    List<FuncionarioResponseDTO> findByName(String name);
}
