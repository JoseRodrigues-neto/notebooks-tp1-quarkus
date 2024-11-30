package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.modelo.Categoria;
import jakarta.validation.Valid;
import br.unitins.tp1.notebooks.dto.CategoriaRequestDTO;
import br.unitins.tp1.notebooks.dto.CategoriaResponseDTO;
import java.util.List;

public interface CategoriaService {

    Categoria create(@Valid CategoriaRequestDTO categoriaRequestDTO);
    Categoria update( Long id, @Valid  CategoriaRequestDTO categoriaRequestDTO);
    void delete(@Valid Long id);
    Categoria findById(Long id);
    List<CategoriaResponseDTO> findAll();
    List<CategoriaResponseDTO> findByNome(String nome);
    Categoria updateDescricao(Long id, String descricao);
}
