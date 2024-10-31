package br.unitins.tp1.notebooks.service;
 
import br.unitins.tp1.notebooks.dto.EspecificacaoRequestDTO;
import br.unitins.tp1.notebooks.dto.EspecificacaoResponseDTO;
import java.util.List;

public interface EspecificacaoService {

    EspecificacaoResponseDTO create(EspecificacaoRequestDTO especificacaoRequestDTO);
    EspecificacaoResponseDTO update(Long id, EspecificacaoRequestDTO especificacaoRequestDTO);
    void delete(Long id);
    EspecificacaoResponseDTO findById(Long id);
    List<EspecificacaoResponseDTO> findAll();
}
