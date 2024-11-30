package br.unitins.tp1.notebooks.service;


import br.unitins.tp1.notebooks.dto.NotebookRequestDTO;
import br.unitins.tp1.notebooks.dto.NotebookResponseDTO;
import br.unitins.tp1.notebooks.modelo.Notebook;
import jakarta.validation.Valid;

import java.util.List;

public interface NotebookService {

    Notebook create(@Valid NotebookRequestDTO notebookRequestDTO);
    Notebook update(Long id,@Valid  NotebookRequestDTO notebookRequestDTO);
    void delete(Long id);
    List<NotebookResponseDTO> findByModelo(String modelo);
    Notebook findById(Long id);
    List<NotebookResponseDTO> findAll();
    Notebook updateNomeImagem(Long id, String nomeImagem);
    Notebook updatePreco(Long id, Double preco);
    Notebook updateGarantia(Long id, Integer garantia);
}
