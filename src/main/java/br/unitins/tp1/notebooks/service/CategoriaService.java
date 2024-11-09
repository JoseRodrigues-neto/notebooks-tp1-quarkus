package br.unitins.tp1.notebooks.service;


import br.unitins.tp1.notebooks.dto.CategoriaRequestDTO;
import br.unitins.tp1.notebooks.dto.CategoriaResponseDTO;
import java.util.List;

public interface CategoriaService {

    CategoriaResponseDTO create(CategoriaRequestDTO categoriaRequestDTO);
    CategoriaResponseDTO update(Long id, CategoriaRequestDTO categoriaRequestDTO);
    void delete(Long id);
    
    CategoriaResponseDTO findById(Long id);
    List<CategoriaResponseDTO> findAll();
    List<CategoriaResponseDTO> findByNome(String nome);
}
