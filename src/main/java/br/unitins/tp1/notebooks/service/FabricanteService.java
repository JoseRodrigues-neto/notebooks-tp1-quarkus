package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.FabricanteRequestDTO;
import br.unitins.tp1.notebooks.dto.FabricanteResponseDTO;

import java.util.List;

public interface FabricanteService {

    List<FabricanteResponseDTO> listar();

    FabricanteResponseDTO buscarPorId(Long id);

    void salvar(FabricanteRequestDTO dto);

    void atualizar(Long id, FabricanteRequestDTO dto);

    void remover(Long id);
}
