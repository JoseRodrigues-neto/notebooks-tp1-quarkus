package br.unitins.tp1.notebooks.service;


import br.unitins.tp1.notebooks.dto.NotebookRequestDTO;
import br.unitins.tp1.notebooks.dto.NotebookResponseDTO;
import java.util.List;

public interface NotebookService {

    NotebookResponseDTO create(NotebookRequestDTO notebookRequestDTO);
    NotebookResponseDTO update(Long id, NotebookRequestDTO notebookRequestDTO);
    void delete(Long id);
    List<NotebookResponseDTO> findByModelo(String modelo);
    NotebookResponseDTO findById(Long id);
    List<NotebookResponseDTO> findAll();
}
