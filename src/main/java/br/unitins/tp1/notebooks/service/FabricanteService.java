package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.FabricanteRequestDTO;
import br.unitins.tp1.notebooks.dto.FabricanteResponseDTO;

import java.util.List;

public interface FabricanteService {

    List<FabricanteResponseDTO> listAll();
    FabricanteResponseDTO findById(Long id);
    void create(FabricanteRequestDTO dto);
    void update(Long id, FabricanteRequestDTO dto);
    void delete(Long id);
    List<FabricanteResponseDTO> findByNome(String nome);
}
