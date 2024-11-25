package br.unitins.tp1.notebooks.service;


import br.unitins.tp1.notebooks.dto.NotebookRequestDTO;
import br.unitins.tp1.notebooks.dto.NotebookResponseDTO;
import br.unitins.tp1.notebooks.modelo.Notebook;
import java.util.List;

public interface NotebookService {

    Notebook create(NotebookRequestDTO notebookRequestDTO);
    Notebook update(Long id, NotebookRequestDTO notebookRequestDTO);
    void delete(Long id);
    List<NotebookResponseDTO> findByModelo(String modelo);
    Notebook findById(Long id);
    List<NotebookResponseDTO> findAll();
}
