package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.NotebookRequestDTO;
import br.unitins.tp1.notebooks.dto.NotebookResponseDTO;
import br.unitins.tp1.notebooks.modelo.Cor;
import br.unitins.tp1.notebooks.modelo.Notebook;
import br.unitins.tp1.notebooks.repository.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class NotebookServiceImpl implements NotebookService {

    @Inject
    NotebookRepository notebookRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    @Inject
    EspecificacaoRepository especificacaoRepository;

    @Inject
    CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public Notebook create(NotebookRequestDTO notebookRequestDTO) {
        Notebook notebook = new Notebook();
        populateNotebookFields(notebook, notebookRequestDTO);
        notebookRepository.persist(notebook);
        return notebook;
    }

    @Override
    @Transactional
    public Notebook update(Long id, NotebookRequestDTO notebookRequestDTO) {
        Notebook notebook = notebookRepository.findById(id);
        if (notebook == null) {
            throw new IllegalArgumentException("Notebook não encontrado para o ID fornecido.");
        }
        populateNotebookFields(notebook, notebookRequestDTO);
        return notebook;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Notebook notebook = notebookRepository.findById(id);
        if (notebook != null) {
            notebookRepository.delete(notebook);
        } else {
            throw new IllegalArgumentException("Notebook não encontrado para o ID fornecido.");
        }
    }

    @Override
    public List<NotebookResponseDTO> findByModelo(String modelo) {
        List<Notebook> notebooks = notebookRepository.findByModelo(modelo);
        return notebooks.stream()
                .map(NotebookResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public Notebook findById(Long id) {
        Notebook notebook = notebookRepository.findById(id);
        if (notebook == null) {
            throw new IllegalArgumentException("Notebook não encontrado para o ID fornecido.");
        }
        return notebook;
    }

    @Override
    public List<NotebookResponseDTO> findAll() {
        return notebookRepository.findAll().list()
                .stream()
                .map(NotebookResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    private void populateNotebookFields(Notebook notebook, NotebookRequestDTO dto) {
        notebook.setModelo(dto.modelo());
        notebook.setPreco(dto.preco());
        notebook.setGarantia(dto.garantia());
        notebook.setFabricante(fabricanteRepository.findById(dto.fabricanteId()));
        notebook.setEspecificacao(especificacaoRepository.findById(dto.especificacaoId()));
        notebook.setCor(Cor.valueOf(dto.cor().toUpperCase()));
        notebook.setCategoria(categoriaRepository.findById(dto.categoriaId()));
    }
}
