package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.notebooks.modelo.ItemPedido;
import br.unitins.tp1.notebooks.modelo.Notebook;
 
import br.unitins.tp1.notebooks.repository.ItemPedidoRepository;
import br.unitins.tp1.notebooks.repository.NotebookRepository;
import br.unitins.tp1.notebooks.repository.PedidoRepository;  // Importar o PedidoRepository

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ItemPedidoServiceImpl implements ItemPedidoService {

    @Inject
    ItemPedidoRepository itemPedidoRepository;

    @Inject
    NotebookRepository notebookRepository;

    @Inject
    PedidoRepository pedidoRepository;  // Injetar o PedidoRepository

    @Override
    @Transactional
    public ItemPedido create(ItemPedidoRequestDTO itemPedidoDTO) {
        // Verificar se o ID do Notebook é válido
        if (itemPedidoDTO.notebookId() == null || itemPedidoDTO.notebookId() <= 0) {
            throw new IllegalArgumentException("ID do Notebook inválido");
        }
    
        // Encontrar o Notebook
        Notebook notebook = notebookRepository.findById(itemPedidoDTO.notebookId());
        if (notebook == null) {
            throw new IllegalArgumentException("Notebook não encontrado com o ID: " + itemPedidoDTO.notebookId());
        }
    
        // Criar um ItemPedido com o preço do notebook
        ItemPedido itemPedido = new ItemPedido(notebook, itemPedidoDTO.quantidade(), notebook.getPreco()); // Usando o preço do notebook
        itemPedidoRepository.persist(itemPedido);
        return itemPedido;
    }
    
    

    @Override
    public ItemPedido findById(Long id) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id);
        if (itemPedido == null) {
            throw new IllegalArgumentException("ItemPedido não encontrado com o ID: " + id);
        }
        return itemPedido;
    }

    @Override
    @Transactional
    public void update(Long id, ItemPedidoRequestDTO itemPedidoDTO) {
        // Buscar o ItemPedido a ser atualizado
        ItemPedido itemPedido = findById(id);

        // Buscar o Notebook e o Pedido
        Notebook notebook = notebookRepository.findById(itemPedidoDTO.notebookId());
        if (notebook == null) {
            throw new IllegalArgumentException("Notebook não encontrado com o ID: " + itemPedidoDTO.notebookId());
        }

        // Atualizar os dados do ItemPedido
        itemPedido.setNotebook(notebook);

        itemPedido.setQuantidade(itemPedidoDTO.quantidade());

        //alterei aqui para tira o erro, se de pal em preço foi por causa daqui
        itemPedido.setPreco(notebook.getPreco());

        // Persistir a atualização
        itemPedidoRepository.persist(itemPedido);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Buscar o ItemPedido a ser excluído
        ItemPedido itemPedido = findById(id);
        itemPedidoRepository.delete(itemPedido);
    }

    @Override
    public List<ItemPedido> listAll() {
        return itemPedidoRepository.listAll();
    }
}
