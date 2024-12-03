package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.FabricanteRequestDTO;
import br.unitins.tp1.notebooks.dto.FabricanteResponseDTO;
import br.unitins.tp1.notebooks.modelo.Fabricante;
import jakarta.validation.Valid;

import java.util.List;

public interface FabricanteService {

    List<FabricanteResponseDTO> listAll();
    Fabricante findById(Long id);
    Fabricante create(@Valid FabricanteRequestDTO dto);
    void update(Long id,@Valid  FabricanteRequestDTO dto);
    void delete(Long id);
    List<FabricanteResponseDTO> findByNome(String nome);
    Fabricante updateNome(Long id, String novoNome);
    Fabricante updatePaisOrigem(Long id, String novoPaisOrigem);
    Fabricante findByPais(String pais);
}
