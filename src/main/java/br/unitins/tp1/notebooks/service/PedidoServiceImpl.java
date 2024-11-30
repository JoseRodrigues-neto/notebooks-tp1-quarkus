package br.unitins.tp1.notebooks.service;

 
import br.unitins.tp1.notebooks.dto.PedidoRequestDTO;
import br.unitins.tp1.notebooks.dto.PedidoResponseDTO;
import br.unitins.tp1.notebooks.modelo.Pedido;
import br.unitins.tp1.notebooks.modelo.StatusPedido;
import br.unitins.tp1.notebooks.modelo.Usuario;
import br.unitins.tp1.notebooks.modelo.Cliente;
import br.unitins.tp1.notebooks.modelo.ItemPedido;
import br.unitins.tp1.notebooks.modelo.Notebook;
import br.unitins.tp1.notebooks.repository.ClienteRepository;
import br.unitins.tp1.notebooks.repository.NotebookRepository;
import br.unitins.tp1.notebooks.repository.PedidoRepository;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.enterprise.context.ApplicationScoped;
import br.unitins.tp1.notebooks.validation.ValidationException;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

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
    ItemPedidoServiceImpl itemPedidoService;  

    @Inject
    LoteService loteService; 

   

    @Inject
    JsonWebToken jwt;

   @Override
@Transactional
public Pedido create(@Valid PedidoRequestDTO pedidoDTO) {
    Cliente cliente = clienteRepository.findById(pedidoDTO.clienteId());
    if (cliente == null) {
        throw new ValidationException("cliente" ,"Cliente não encontrado");
    }

    // Verificar estoque antes de criar o pedido
    pedidoDTO.itens().forEach(itemDto -> {
        int estoqueDisponivel = loteService.verificarEstoque(itemDto.notebookId());
        if (estoqueDisponivel < itemDto.quantidade()) {
            throw new ValidationException("Estoque" ,"Produto indiponivel no estoque");
        }
    });

    // Criando os itens do pedido e reduzindo o estoque
    List<ItemPedido> itens = pedidoDTO.itens().stream()
        .map(itemDto -> {
            // Buscar o notebook pelo ID para obter o preço
            Notebook notebook = notebookRepository.findById(itemDto.notebookId());
            if (notebook == null) {
                throw new ValidationException("Notebook" ,"Notebook id não encontrado");
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
        validarId(id);
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado com o ID: " + id);
        }
        return pedido;
    }

    @Override
    @Transactional
    public void update(Long id, PedidoRequestDTO pedidoDTO) {
        validarId(id);
        Pedido pedido = findById(id);
        Cliente cliente = clienteRepository.findById(pedidoDTO.clienteId());
        if (cliente == null) {
            throw new ValidationException("cliente" ,"Cliente não encontrado");
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
        validarId(id);
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
    
    @Override
    @Transactional
    public Pedido alterarStatusPedido(Long pedidoId, Long statusId) {
        validarId(pedidoId);
        Pedido pedido = findById(pedidoId);

        StatusPedido novoStatus = StatusPedido.fromId(statusId);
        if (novoStatus == null) {
            throw new IllegalArgumentException("ID do status inválido: " + statusId);
        }

        pedido.setStatus(novoStatus);
        pedidoRepository.persist(pedido); // Atualiza o pedido no banco
        return pedido;
    }

  // metodos pessoais
    @Override
    public List<PedidoResponseDTO> findByCliente(Long idCliente) {
        return pedidoRepository.findByClienteId(idCliente).stream()  // Usando o método do repositório para buscar pelo ID
            .map(PedidoResponseDTO::valueOf)  // Convertendo os pedidos para o DTO
            .collect(Collectors.toList());  // Coletando em uma lista
    }
    
  
    @Override
    public List<PedidoResponseDTO> findMyPedidos() {
  
        return findByCliente(clienteRepository.findByUsername(jwt.getName()).getId());
    } 


     private void validarId(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) 
            throw new ValidationException("nome", "Pedido com o ID fornecido não encontrado.");
    }

}
