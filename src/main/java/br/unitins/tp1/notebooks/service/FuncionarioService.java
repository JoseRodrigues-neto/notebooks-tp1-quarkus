package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.FuncionarioRequestDTO;
import br.unitins.tp1.notebooks.dto.FuncionarioResponseDTO;
import br.unitins.tp1.notebooks.modelo.Funcionario;
import jakarta.validation.Valid;

import java.util.List;

public interface FuncionarioService {
    Funcionario create(@Valid FuncionarioRequestDTO dto);
    Funcionario findById(Long id);
    Funcionario update(Long id,@Valid  FuncionarioRequestDTO funcionarioDTO);
    void delete(Long id);
    List<FuncionarioResponseDTO> listAll(); 
    List<FuncionarioResponseDTO> findByName(String name);
    Funcionario findByMatricula(String matricula);
    
}
