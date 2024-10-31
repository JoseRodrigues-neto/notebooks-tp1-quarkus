package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.NotebookRequestDTO;
import br.unitins.tp1.notebooks.dto.NotebookResponseDTO;
import br.unitins.tp1.notebooks.modelo.*;
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
    public NotebookResponseDTO create(NotebookRequestDTO dto) {
        Notebook notebook = new Notebook();
        populateNotebookFields(notebook, dto);
        notebookRepository.persist(notebook);
        return toResponseDTO(notebook);
    }

    @Override
    @Transactional
    public NotebookResponseDTO update(Long id, NotebookRequestDTO dto) {
        Notebook notebook = notebookRepository.findById(id);
        if (notebook == null) {
            return null;  
        }
        populateNotebookFields(notebook, dto);
        return toResponseDTO(notebook);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Notebook notebook = notebookRepository.findById(id);
        if (notebook != null) {
            notebookRepository.delete(notebook);
        }
    }

    @Override
    public NotebookResponseDTO findById(Long id) {
        Notebook notebook = notebookRepository.findById(id);
        return (notebook != null) ? toResponseDTO(notebook) : null;
    }

    @Override
    public List<NotebookResponseDTO> findAll() {
        return notebookRepository.findAll().list()
                .stream()
                .map(this::toResponseDTO)
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

    private NotebookResponseDTO toResponseDTO(Notebook notebook) {
        return new NotebookResponseDTO(
                notebook.getId(),
                notebook.getModelo(),
                notebook.getPreco(),
                notebook.getGarantia(),
                notebook.getFabricante().getNome(),
                notebook.getEspecificacao().getDetalhes(),
                notebook.getCor().name(),
   
                notebook.getCategoria().getNome()
        );
    }
}
