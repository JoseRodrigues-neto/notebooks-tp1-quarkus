package br.unitins.tp1.notebooks.service;


import br.unitins.tp1.notebooks.dto.EspecificacaoRequestDTO;
import br.unitins.tp1.notebooks.dto.EspecificacaoResponseDTO;
import br.unitins.tp1.notebooks.modelo.Especificacao;
import br.unitins.tp1.notebooks.repository.EspecificacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EspecificacaoServiceImpl implements EspecificacaoService {

    @Inject
    EspecificacaoRepository especificacaoRepository;

    @Override
    @Transactional
    public EspecificacaoResponseDTO create(EspecificacaoRequestDTO dto) {
        Especificacao especificacao = new Especificacao();
        populateEspecificacaoFields(especificacao, dto);
        especificacaoRepository.persist(especificacao);
        return toResponseDTO(especificacao);
    }

    @Override
    @Transactional
    public EspecificacaoResponseDTO update(Long id, EspecificacaoRequestDTO dto) {
        Especificacao especificacao = especificacaoRepository.findById(id);
        if (especificacao == null) {
            return null;
        }
        populateEspecificacaoFields(especificacao, dto);
        return toResponseDTO(especificacao);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Especificacao especificacao = especificacaoRepository.findById(id);
        if (especificacao != null) {
            especificacaoRepository.delete(especificacao);
        }
    }

    @Override
    public EspecificacaoResponseDTO findById(Long id) {
        Especificacao especificacao = especificacaoRepository.findById(id);
        return (especificacao != null) ? toResponseDTO(especificacao) : null;
    }

    @Override
    public List<EspecificacaoResponseDTO> findAll() {
        return especificacaoRepository.findAll().list()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private void populateEspecificacaoFields(Especificacao especificacao, EspecificacaoRequestDTO dto) {
        especificacao.setProcessador(dto.processador());
        especificacao.setMemoriaRam(dto.memoriaRam());
        especificacao.setArmazenamento(dto.armazenamento());
        especificacao.setTela(dto.tela());
        especificacao.setBateria(dto.bateria());
        especificacao.setPeso(dto.peso());
    }

    private EspecificacaoResponseDTO toResponseDTO(Especificacao especificacao) {
        return new EspecificacaoResponseDTO(
                especificacao.getId(),
                especificacao.getProcessador(),
                especificacao.getMemoriaRam(),
                especificacao.getArmazenamento(),
                especificacao.getTela(),
                especificacao.getBateria(),
                especificacao.getPeso()
        );
    }
   
    @Override
public List<EspecificacaoResponseDTO> findByProcessador(String processador) {
    return especificacaoRepository.findByProcessador(processador).stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
}

    
}
