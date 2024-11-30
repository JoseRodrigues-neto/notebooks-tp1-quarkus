package br.unitins.tp1.notebooks.service;
 
import br.unitins.tp1.notebooks.dto.EspecificacaoRequestDTO;
import br.unitins.tp1.notebooks.modelo.Especificacao;
import jakarta.validation.Valid;
import br.unitins.tp1.notebooks.dto.EspecificacaoResponseDTO;
import java.util.List;

public interface EspecificacaoService {

    Especificacao create(@Valid EspecificacaoRequestDTO especificacaoRequestDTO);
    Especificacao update(Long id,@Valid  EspecificacaoRequestDTO especificacaoRequestDTO);
    List<EspecificacaoResponseDTO> findByProcessador(String processador);
    void delete(Long id);
    Especificacao findById(Long id);
    List<EspecificacaoResponseDTO> findAll();
    Especificacao updateMemoriaRam(Long id, String novaMemoriaRam);
    Especificacao updateArmazenamento(Long id, String novoArmazenamento);
 
}
