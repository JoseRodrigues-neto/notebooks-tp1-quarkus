package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.CategoriaRequestDTO;
import br.unitins.tp1.notebooks.dto.CategoriaResponseDTO;
import br.unitins.tp1.notebooks.modelo.Categoria;
import br.unitins.tp1.notebooks.repository.CategoriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoriaServiceImpl implements CategoriaService {

    @Inject
    CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public Categoria create(CategoriaRequestDTO categoriaRequestDTO) {
        // Criar uma nova entidade Categoria a partir do DTO de entrada
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaRequestDTO.nome());
        categoria.setDescricao(categoriaRequestDTO.descricao());
        
        // Persistir a nova Categoria
        categoriaRepository.persist(categoria);
        
        // Retornar a entidade Categoria (como esperado pela interface)
        return categoria;
    }

    @Override
    @Transactional
    public Categoria update(Long id, CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoria = categoriaRepository.findById(id);
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria não encontrada com o ID fornecido.");
        }
        categoria.setNome(categoriaRequestDTO.nome());
        categoria.setDescricao(categoriaRequestDTO.descricao());
        categoriaRepository.persist(categoria);
        return categoria;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Categoria categoria = categoriaRepository.findById(id);
        if (categoria != null) {
            categoriaRepository.delete(categoria);
        }
    }

    @Override
    public Categoria findById(Long id) {
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
        return categoriaRepository.findByNome(nome)
                .stream()
                .map(CategoriaResponseDTO::valueOf)
                .collect(Collectors.toList());
    }
}
