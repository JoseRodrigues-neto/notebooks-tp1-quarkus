package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.NotebookRequestDTO;
import br.unitins.tp1.notebooks.dto.NotebookResponseDTO;
import br.unitins.tp1.notebooks.modelo.Cliente;
import br.unitins.tp1.notebooks.modelo.Cor;
import br.unitins.tp1.notebooks.modelo.Lote;
import br.unitins.tp1.notebooks.modelo.Notebook;
import br.unitins.tp1.notebooks.repository.*;
import br.unitins.tp1.notebooks.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

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

    @Inject
    LoteRepository loteRepository;

    @Override
    @Transactional
    public Notebook create(@Valid NotebookRequestDTO notebookRequestDTO) {

        Notebook notebook = new Notebook();
        notebook.setModelo(notebookRequestDTO.modelo());
        notebook.setPreco(notebookRequestDTO.preco());
        notebook.setGarantia(notebookRequestDTO.garantia());
        notebook.setFabricante(fabricanteRepository.findById(notebookRequestDTO.fabricanteId()));
        notebook.setEspecificacao(especificacaoRepository.findById(notebookRequestDTO.especificacaoId()));
        notebook.setCategoria(categoriaRepository.findById(notebookRequestDTO.categoriaId()));

        try {
            notebook.setCor(Cor.valueOf(notebookRequestDTO.cor().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ValidationException("cor", "Valor inválido para a cor");
        }

        if(notebook.getFabricante() == null){
            throw new ValidationException("fabricante", "ID invalida");   
        }

        if(notebook.getEspecificacao()== null){
            throw new ValidationException("Especificacao", "ID invalida");   
        }

        if(notebook.getCategoria() == null){
            throw new ValidationException("Categoria", "ID invalida");   
        }

        try {
            notebook.setCor(Cor.valueOf(notebookRequestDTO.cor().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ValidationException("cor", "Valor inválido para a cor");
        }

        notebookRepository.persist(notebook);
        return notebook;
    }

    @Override
    @Transactional
    public Notebook update(Long id,@Valid NotebookRequestDTO notebookRequestDTO) {
        validarId(id);
        Notebook notebook = notebookRepository.findByIdOptional(id)
                .orElseThrow(() -> new IllegalArgumentException("Notebook não encontrado para o ID fornecido."));

        notebook.setModelo(notebookRequestDTO.modelo());
        notebook.setPreco(notebookRequestDTO.preco());
        notebook.setGarantia(notebookRequestDTO.garantia());
        notebook.setFabricante(fabricanteRepository.findById(notebookRequestDTO.fabricanteId()));
        notebook.setEspecificacao(especificacaoRepository.findById(notebookRequestDTO.especificacaoId()));
         notebook.setCategoria(categoriaRepository.findById(notebookRequestDTO.categoriaId()));

        try {
            notebook.setCor(Cor.valueOf(notebookRequestDTO.cor().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ValidationException("cor", "Valor inválido para a cor");
        }

        if(notebook.getFabricante() == null){
            throw new ValidationException("fabricante", "ID invalida");   
        }

        if(notebook.getEspecificacao()== null){
            throw new ValidationException("Especificacao", "ID invalida");   
        }

        if(notebook.getCategoria() == null){
            throw new ValidationException("Categoria", "ID invalida");   
        }

        return notebook;  
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validarId(id);
        boolean hasAssociatedLote = loteRepository.existeNotebookId(id);

        if (hasAssociatedLote) {
            throw new ValidationException("Lote","Não é possível deletar o notebook pois ele está associado a um lote.");
        }
        Notebook notebook = notebookRepository.findById(id);
        notebookRepository.delete(notebook);
        
    }

    @Override
    public List<NotebookResponseDTO> findByModelo(String modelo) {
        validarModelo(modelo);
        List<Notebook> notebooks = notebookRepository.findByModelo(modelo);
        return notebooks.stream()
                .map(NotebookResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public Notebook findById(Long id) {
        validarId(id);
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

    @Override
    @Transactional
    public Notebook updateNomeImagem(Long id, String nomeImagem) {
        validarId(id);
        Notebook notebook = notebookRepository.findById(id);

        notebook.setNomeImagem(nomeImagem);

        return notebook;
    }

    @Override
    @Transactional
    public Notebook updatePreco(Long id, Double preco) {
        validarId(id);
        Notebook notebook = notebookRepository.findById(id);
        notebook.setPreco(preco);
        return notebook;
    }

    @Override
    @Transactional
    public Notebook updateGarantia(Long id, Integer garantia) {
        validarId(id);

        if (garantia == null || garantia <= 0) {
            throw new ValidationException("garantiaId", "Garantia invalida");
        }
        Notebook notebook = notebookRepository.findById(id);
        notebook.setGarantia(garantia);
        return notebook; 
    }

    private void validarId(long id) {
        Notebook notebook = notebookRepository.findById(id);
        if (notebook == null)
            throw new ValidationException("id", "Notebook com o ID fornecido não encontrado.");
    }

    private void validarModelo(String modelo) {
        List<Notebook> notebook = notebookRepository.findByModelo(modelo);
        if (notebook == null)
            throw new ValidationException("Modelo", "Notebook com o modelo fornecido não encontrado.");
    }

}
