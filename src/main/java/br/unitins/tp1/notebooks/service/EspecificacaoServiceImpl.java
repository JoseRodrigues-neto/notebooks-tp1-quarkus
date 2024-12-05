package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.EspecificacaoRequestDTO;
import br.unitins.tp1.notebooks.dto.EspecificacaoResponseDTO;
import br.unitins.tp1.notebooks.modelo.Especificacao;
import br.unitins.tp1.notebooks.repository.EspecificacaoRepository;
import br.unitins.tp1.notebooks.validation.ValidationException;
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
    public Especificacao create(EspecificacaoRequestDTO dto) {
        Especificacao especificacao = new Especificacao();
        especificacao.setProcessador(dto.processador());
        especificacao.setMemoriaRam(dto.memoriaRam());
        especificacao.setArmazenamento(dto.armazenamento());
        especificacao.setTela(dto.tela());
        especificacao.setBateria(dto.bateria());
        especificacao.setPeso(dto.peso());

        especificacaoRepository.persist(especificacao);
        return especificacao;
    }

    @Override
    @Transactional
    public Especificacao update(Long id, EspecificacaoRequestDTO dto) {
        validarId(id);
        Especificacao especificacao = especificacaoRepository.findById(id);
        if (especificacao == null) {
            throw new IllegalArgumentException("Especificação não encontrada com o ID fornecido.");
        }

        especificacao.setProcessador(dto.processador());
        especificacao.setMemoriaRam(dto.memoriaRam());
        especificacao.setArmazenamento(dto.armazenamento());
        especificacao.setTela(dto.tela());
        especificacao.setBateria(dto.bateria());
        especificacao.setPeso(dto.peso());

        return especificacao;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validarId(id);
        Especificacao especificacao = especificacaoRepository.findById(id);
        if (especificacao != null) {
            especificacaoRepository.delete(especificacao);
        }
    }

    @Override
    public Especificacao findById(Long id) {
        validarId(id);
        return especificacaoRepository.findById(id);
    }

    @Override
    public List<EspecificacaoResponseDTO> findAll() {
        return especificacaoRepository.findAll().list()
                .stream()
                .map(especificacao -> new EspecificacaoResponseDTO(
                        especificacao.getId(),
                        especificacao.getProcessador(),
                        especificacao.getMemoriaRam(),
                        especificacao.getArmazenamento(),
                        especificacao.getTela(),
                        especificacao.getBateria(),
                        especificacao.getPeso()))
                .collect(Collectors.toList());
    }

    @Override
    public List<EspecificacaoResponseDTO> findByProcessador(String processador) {
        validarNomeProcessador(processador);
        return especificacaoRepository.findByProcessador(processador).stream()
                .map(especificacao -> new EspecificacaoResponseDTO(
                        especificacao.getId(),
                        especificacao.getProcessador(),
                        especificacao.getMemoriaRam(),
                        especificacao.getArmazenamento(),
                        especificacao.getTela(),
                        especificacao.getBateria(),
                        especificacao.getPeso()))
                .collect(Collectors.toList());
    }

    public Especificacao findByNomeProcessador(String processador) {
        Especificacao especificacao = especificacaoRepository.findByProcessadorUnico(processador);

        return especificacao;
    }

    @Transactional
    public Especificacao updateMemoriaRam(Long id, String novaMemoriaRam) {
        validarId(id);
        Especificacao especificacao = especificacaoRepository.findById(id);
        especificacao.setMemoriaRam(novaMemoriaRam);
        return especificacao;
    }

    @Transactional
    public Especificacao updateArmazenamento(Long id, String novoArmazenamento) {
        validarId(id);
        Especificacao especificacao = especificacaoRepository.findById(id);
        especificacao.setArmazenamento(novoArmazenamento);
        return especificacao;
    }

    private void validarId(long id) {
        Especificacao especificacao = especificacaoRepository.findById(id);
        if (especificacao == null)
            throw new ValidationException("id", "Processador com o ID fornecido não encontrado.");
    }

    private void validarNomeProcessador(String processador) {
        Especificacao especificacao = especificacaoRepository.findByProcessadorUnico(processador);
        if (especificacao == null)
            throw new ValidationException("nome", "Nome do processador fornecido não encontrado.");
    }

}
