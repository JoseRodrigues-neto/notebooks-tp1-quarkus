package br.unitins.tp1.notebooks.service;
 
import br.unitins.tp1.notebooks.dto.EspecificacaoRequestDTO;
import br.unitins.tp1.notebooks.modelo.Especificacao;
import br.unitins.tp1.notebooks.dto.EspecificacaoResponseDTO;
import java.util.List;

public interface EspecificacaoService {

    Especificacao create(EspecificacaoRequestDTO especificacaoRequestDTO);
    Especificacao update(Long id, EspecificacaoRequestDTO especificacaoRequestDTO);
    List<EspecificacaoResponseDTO> findByProcessador(String processador);
    void delete(Long id);
    Especificacao findById(Long id);
    List<EspecificacaoResponseDTO> findAll();
}
