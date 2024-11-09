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
    public CategoriaResponseDTO create(CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaRequestDTO.nome()); // Acessando o campo do record
        categoria.setDescricao(categoriaRequestDTO.descricao()); // Acessando o campo do record
        categoriaRepository.persist(categoria);
        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
    }

    @Override
    @Transactional
    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoria = categoriaRepository.findById(id);
        if (categoria == null) {
            return null;  
        }
        categoria.setNome(categoriaRequestDTO.nome()); // Acessando o campo do record
        categoria.setDescricao(categoriaRequestDTO.descricao()); // Acessando o campo do record
        categoriaRepository.persist(categoria); // Persistindo a atualização
        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
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
    public List<CategoriaResponseDTO> findByNome(String nome) {
        return categoriaRepository.findByNome(nome)
                .stream()
                .map(CategoriaResponseDTO::valueOf)
                .collect(Collectors.toList());
    }


    @Override
    public CategoriaResponseDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id);
        if (categoria == null) {
            return null;  
        }
        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
    }

    @Override
    public List<CategoriaResponseDTO> findAll() {
        return categoriaRepository.findAll().list()
                .stream()
                .map(categoria -> new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao()))
                .collect(Collectors.toList());
    }
}
