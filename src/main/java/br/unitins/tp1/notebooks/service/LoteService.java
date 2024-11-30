package br.unitins.tp1.notebooks.service;

import java.util.List;

import br.unitins.tp1.notebooks.dto.LoteRequestDTO;
import br.unitins.tp1.notebooks.modelo.Lote;
import jakarta.validation.Valid;
import br.unitins.tp1.notebooks.dto.LoteResponseDTO;

public interface LoteService {

    List<LoteResponseDTO> findAll();  

    Lote findById(Long id); 

    Lote create(@Valid LoteRequestDTO dto);  

    Lote update(Long id,@Valid  LoteRequestDTO dto);  

    void delete(Long id);  
    int verificarEstoque(@Valid Long notebookId);

     void atualizarEstoque(@Valid Long notebookId,  int quantidade);

    Lote atualizarQuantidade(Long id, int quantidade);
}
