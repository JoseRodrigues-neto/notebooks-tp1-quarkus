package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.FabricanteRequestDTO;
import br.unitins.tp1.notebooks.dto.FabricanteResponseDTO;
import br.unitins.tp1.notebooks.modelo.Fabricante;
import br.unitins.tp1.notebooks.repository.FabricanteRepository;
import br.unitins.tp1.notebooks.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class FabricanteServiceImpl implements FabricanteService {

    @Inject
    FabricanteRepository repository;

    @Override
    public List<FabricanteResponseDTO> listAll() {
        return repository.listAll().stream().map(FabricanteResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public Fabricante findById(Long id) {
        validarId(id);
        Fabricante fabricante = repository.findById(id);
        return fabricante;
    }

    @Override
    @Transactional
    public Fabricante create(@Valid FabricanteRequestDTO dto) {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome(dto.nome());
        fabricante.setPaisOrigem(dto.paisOrigem());

        repository.persist(fabricante);
        return fabricante;
    }

    @Override
    @Transactional
    public void update(Long id, @Valid FabricanteRequestDTO dto) {
        validarId(id);
        Fabricante existente = repository.findById(id);
        if (existente != null) {
            existente.setNome(dto.nome());
            existente.setPaisOrigem(dto.paisOrigem());
            repository.persist(existente);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validarId(id);
        repository.deleteById(id);
    }

    @Override
    public List<FabricanteResponseDTO> findByNome(String nome) {
        validarNome(nome);
        return repository.findByNome(nome).stream()
                .map(FabricanteResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Fabricante updateNome(Long id, String novoNome) {
        validarId(id);

        Fabricante fabricante = repository.findById(id);
        fabricante.setNome(novoNome);
        return fabricante;
    }

    @Override
    @Transactional
    public Fabricante updatePaisOrigem(Long id, String novoPaisOrigem) {
        validarId(id);
        Fabricante fabricante = repository.findById(id);
        fabricante.setPaisOrigem(novoPaisOrigem);
        return fabricante;
    }

    private void validarId(long id) {
        Fabricante fabricante = repository.findById(id);
        if (fabricante == null)
            throw new ValidationException("id", "Fabricante com o ID fornecido não encontrado.");
    }

    private void validarNome(String nome) {
        Fabricante fabricante = repository.findByNomeUnico(nome);
        if (fabricante == null)
            throw new ValidationException("nome", "Fabrincate com o nome fornecido não encontrado.");
    }

}
