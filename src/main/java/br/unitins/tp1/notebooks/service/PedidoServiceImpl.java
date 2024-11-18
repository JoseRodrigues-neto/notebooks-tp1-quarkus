package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.PedidoRequestDTO;
import br.unitins.tp1.notebooks.modelo.Pedido;
import br.unitins.tp1.notebooks.modelo.Cliente;
import br.unitins.tp1.notebooks.modelo.ItemPedido;
import br.unitins.tp1.notebooks.modelo.Notebook;
import br.unitins.tp1.notebooks.repository.ClienteRepository;
import br.unitins.tp1.notebooks.repository.NotebookRepository;
import br.unitins.tp1.notebooks.repository.PedidoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

      @Inject
    NotebookRepository notebookRepository;


    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ItemPedidoServiceImpl itemPedidoService; // Serviço de itens do pedido

    @Inject
    LoteService loteService; // Serviço de controle de estoque

   @Override
@Transactional
public Pedido create(PedidoRequestDTO pedidoDTO) {
    Cliente cliente = clienteRepository.findById(pedidoDTO.clienteId());
    if (cliente == null) {
        throw new IllegalArgumentException("Cliente não encontrado com o ID: " + pedidoDTO.clienteId());
    }

    // Verificar estoque antes de criar o pedido
    pedidoDTO.itens().forEach(itemDto -> {
        int estoqueDisponivel = loteService.verificarEstoque(itemDto.notebookId());
        if (estoqueDisponivel < itemDto.quantidade()) {
            throw new IllegalArgumentException("Estoque insuficiente para o notebook com ID: " + itemDto.notebookId());
        }
    });

    // Criando os itens do pedido e reduzindo o estoque
    List<ItemPedido> itens = pedidoDTO.itens().stream()
        .map(itemDto -> {
            // Buscar o notebook pelo ID para obter o preço
            Notebook notebook = notebookRepository.findById(itemDto.notebookId());
            if (notebook == null) {
                throw new IllegalArgumentException("Notebook não encontrado com o ID: " + itemDto.notebookId());
            }

            // Atualizando o estoque do notebook
            loteService.atualizarEstoque(itemDto.notebookId(), itemDto.quantidade());

            // Criar o item de pedido e atribuir o preço do notebook
            ItemPedido itemPedido = itemPedidoService.create(itemDto);
            itemPedido.setPreco(notebook.getPreco());  // Atualizando o preço do item com o preço do notebook

            return itemPedido;
        })
        .collect(Collectors.toList());

    // Criando o pedido e calculando o valor total
    Pedido pedido = new Pedido(cliente, itens);
    pedido.setValorTotal(itens.stream()
        .mapToDouble(item -> item.getPreco() * item.getQuantidade())
        .sum());

    pedidoRepository.persist(pedido);
    return pedido;
}


    @Override
    public Pedido findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado com o ID: " + id);
        }
        return pedido;
    }

    @Override
    @Transactional
    public void update(Long id, PedidoRequestDTO pedidoDTO) {
        Pedido pedido = findById(id);
        Cliente cliente = clienteRepository.findById(pedidoDTO.clienteId());
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado com o ID: " + pedidoDTO.clienteId());
        }

        // Atualizando os itens do pedido
        List<ItemPedido> novosItens = pedidoDTO.itens().stream()
            .map(itemDto -> {
                // Reverter o estoque antigo
                ItemPedido itemExistente = pedido.getItens().stream()
                    .filter(item -> item.getNotebook().getId().equals(itemDto.notebookId()))
                    .findFirst()
                    .orElse(null);

                if (itemExistente != null) {
                    loteService.atualizarEstoque(itemDto.notebookId(), -itemExistente.getQuantidade());
                }

                // Atualizar o estoque com os novos valores
                loteService.atualizarEstoque(itemDto.notebookId(), itemDto.quantidade());
                return itemPedidoService.create(itemDto);
            })
            .collect(Collectors.toList());

        pedido.setCliente(cliente);
        pedido.setItens(novosItens);
        pedido.setValorTotal(novosItens.stream()
            .mapToDouble(item -> item.getPreco() * item.getQuantidade())
            .sum());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Pedido pedido = findById(id);

        // Reverter o estoque ao excluir o pedido
        pedido.getItens().forEach(item -> {
            loteService.atualizarEstoque(item.getNotebook().getId(), -item.getQuantidade());
        });

        pedidoRepository.delete(pedido);
    }

    @Override
    public List<Pedido> listAll() {
        return pedidoRepository.listAll();
    }
}
