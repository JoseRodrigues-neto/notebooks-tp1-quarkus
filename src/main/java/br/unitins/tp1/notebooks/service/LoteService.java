package br.unitins.tp1.notebooks.service;

import java.util.List;

import br.unitins.tp1.notebooks.dto.LoteRequestDTO;
import br.unitins.tp1.notebooks.dto.LoteResponseDTO;

public interface LoteService {

    List<LoteResponseDTO> findAll(); // Lista todos os lotes

    LoteResponseDTO findById(Long id); // Busca um lote por ID

    LoteResponseDTO create(LoteRequestDTO dto); // Cria um novo lote

    LoteResponseDTO update(Long id, LoteRequestDTO dto); // Atualiza um lote existente

    void delete(Long id); // Exclui um lote pelo ID
    int verificarEstoque(Long notebookId);

     void atualizarEstoque(Long notebookId, int quantidade);
}
