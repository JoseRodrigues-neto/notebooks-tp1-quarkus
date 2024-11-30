package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.CategoriaRequestDTO;
import br.unitins.tp1.notebooks.dto.CategoriaResponseDTO;
import br.unitins.tp1.notebooks.modelo.Categoria;
import br.unitins.tp1.notebooks.validation.ValidationException;
import br.unitins.tp1.notebooks.repository.CategoriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoriaServiceImpl implements CategoriaService {

    @Inject
    CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public Categoria create(CategoriaRequestDTO categoriaRequestDTO) {

        Categoria categoria = new Categoria();
        categoria.setNome(categoriaRequestDTO.nome());
        categoria.setDescricao(categoriaRequestDTO.descricao());

        categoriaRepository.persist(categoria);

        return categoria;
    }

    @Override
    @Transactional
    public Categoria update(Long id, @Valid CategoriaRequestDTO categoriaRequestDTO) {

        validarId(id);
        Categoria categoria = categoriaRepository.findById(id);

        categoria.setNome(categoriaRequestDTO.nome());
        categoria.setDescricao(categoriaRequestDTO.descricao());
        categoriaRepository.persist(categoria);
        return categoria;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validarId(id);
        Categoria categoria = categoriaRepository.findById(id);
        if (categoria != null) {
            categoriaRepository.delete(categoria);
        }
    }

    @Override
    public Categoria findById(Long id) {

        validarId(id);
        return categoriaRepository.findById(id);
    }

    @Override
    public List<CategoriaResponseDTO> findAll() {
        return categoriaRepository.findAll().list()
                .stream()
                .map(CategoriaResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoriaResponseDTO> findByNome(String nome) {
        validarNome(nome);
        return categoriaRepository.findByNome(nome)
                .stream()
                .map(CategoriaResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Categoria updateDescricao(Long id, String descricao) {
        validarId(id);
        Categoria categoria = categoriaRepository.findById(id);

        categoria.setDescricao(descricao);
        categoriaRepository.persist(categoria);
        return categoria;
    }

    private void validarId(long id) {
        Categoria categoria = categoriaRepository.findById(id);
        if (categoria == null)
            throw new ValidationException("id", "Categoria com o ID fornecido não encontrada.");
    }

    private void validarNome(String nome) {
        Categoria categoria = categoriaRepository.findByNomeUnico(nome);
        if (categoria == null)
            throw new ValidationException("nome", "Categoria com o nome fornecido não encontrada.");
    }

}
