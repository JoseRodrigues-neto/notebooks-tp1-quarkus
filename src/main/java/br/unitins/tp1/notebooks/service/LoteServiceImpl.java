package br.unitins.tp1.notebooks.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.notebooks.dto.LoteRequestDTO;
import br.unitins.tp1.notebooks.dto.LoteResponseDTO;
import br.unitins.tp1.notebooks.modelo.Funcionario;
import br.unitins.tp1.notebooks.modelo.Lote;
import br.unitins.tp1.notebooks.modelo.Notebook;
import br.unitins.tp1.notebooks.repository.LoteRepository;
import br.unitins.tp1.notebooks.repository.NotebookRepository;
import br.unitins.tp1.notebooks.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class LoteServiceImpl implements LoteService {

    @Inject
    private LoteRepository loteRepository;

    @Inject
    private NotebookRepository notebookRepository;

    @Override
    public List<LoteResponseDTO> findAll() {
      
        return loteRepository.findAll()
                .stream()
                .map(LoteResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public Lote findById(Long id) {
        validarId(id);
        Lote lote = loteRepository.findById(id);
        if (lote == null) {
            throw new IllegalArgumentException("Lote não encontrado com o ID: " + id);
        }
        return lote;
    }

    @Override
    @Transactional
    public Lote create(LoteRequestDTO dto) {
        validarNotebookId(dto.notebookId());
        Notebook notebook = notebookRepository.findById(dto.notebookId());
    
        Lote lote = new Lote(notebook, dto.quantidade());
        lote.setDataEntrada(dto.dataEntrada());
        loteRepository.persist(lote);

        return lote;
    }

    @Override
    @Transactional
    public Lote update(Long id,@Valid LoteRequestDTO dto) {
    
        validarId(id);
        validarNotebookId(dto.notebookId());

        Lote lote = loteRepository.findById(id);

        Notebook notebook = notebookRepository.findById(dto.notebookId());

        lote.setNotebook(notebook);
        lote.setQuantidade(dto.quantidade());
        lote.setDataEntrada(dto.dataEntrada());

        return lote;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validarId(id);
        loteRepository.deleteById(id);
    }

    @Override
    public int verificarEstoque(@Valid Long notebookId) {
        
        Notebook notebook = notebookRepository.findById(notebookId);
        if (notebook == null) {
            throw new ValidationException("id", "Notebook com o ID fornecido não encontrado."); 
        }

        return loteRepository.findByNotebookId(notebookId)
                .stream()
                .mapToInt(Lote::getQuantidade)
                .sum();
    }

    @Override
    @Transactional
    public void atualizarEstoque(Long notebookId,  int quantidade) {
        validarId(notebookId);
        List<Lote> lotes = loteRepository.findByNotebookId(notebookId);

        int restante = quantidade;

        for (Lote lote : lotes) {
            int quantidadeLote = lote.getQuantidade();

            if (restante <= 0) {
                break;  
            }

            if (quantidadeLote <= restante) {
              
                restante -= quantidadeLote;
                lote.setQuantidade(0);
            } else {
               
                lote.setQuantidade(quantidadeLote - restante);
                restante = 0;
            }

            loteRepository.persist(lote);  
        }

        if (restante > 0) {
            throw new IllegalArgumentException("Estoque insuficiente para o notebook com ID: " + notebookId);
        }
    }

    @Override
@Transactional
public Lote atualizarQuantidade(Long id, int quantidade) {
    validarId(id);

    Lote lote = loteRepository.findById(id);

    if (quantidade < 0) {
        throw new ValidationException("quantidade", "A quantidade não pode ser negativa.");
    }

    lote.setQuantidade(quantidade);

    loteRepository.persist(lote); // Atualiza o lote no banco de dados

    return lote;
}

     private void validarId(long id) {
            Lote lote = loteRepository.findById(id);
        if (lote == null) 
            throw new ValidationException("id", "Lote com o ID fornecido não encontrado.");
    }

    private void validarNotebookId(long id) {
            Notebook notebook = notebookRepository.findById(id);
        if (notebook == null) 
            throw new ValidationException("id", "Notebook com o ID fornecido não encontrado."); 
}
}
