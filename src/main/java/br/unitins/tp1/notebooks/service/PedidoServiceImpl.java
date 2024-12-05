package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.notebooks.dto.PedidoRequestDTO;
import br.unitins.tp1.notebooks.dto.PedidoResponseDTO;
import br.unitins.tp1.notebooks.modelo.Pedido;
import br.unitins.tp1.notebooks.modelo.StatusPedido;
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
        String usernameAutenticado = jwt.getName();
        Cliente cliente = clienteRepository.findByUsername(usernameAutenticado);
        if (cliente == null) {
            throw new ValidationException("cliente", "Cliente não encontrado");
        }
      
        List<ItemPedido> itens = processarItens(pedidoDTO.itens());
    
        Pedido pedido = new Pedido(cliente, itens);
        pedido.setValorTotal(itens.stream()
                .mapToDouble(item -> item.getPreco() * item.getQuantidade())
                .sum());
    
        pedidoRepository.persist(pedido);
        return pedido;
    }
   
    
    private List<ItemPedido> processarItens(List<ItemPedidoRequestDTO> itensDTO) {
    return itensDTO.stream()
            .map(itemDto -> {
               
                int estoqueDisponivel = loteService.verificarEstoque(itemDto.notebookId());
                if (estoqueDisponivel < itemDto.quantidade()) {
                    throw new ValidationException("Quantidade", "Produto indisponível no estoque");
                }
 
                Notebook notebook = notebookRepository.findById(itemDto.notebookId());
                if (notebook == null) {
                    throw new ValidationException("Notebook", "Notebook id não encontrado");
                }
 
                loteService.atualizarEstoque(itemDto.notebookId(), itemDto.quantidade());

       
                ItemPedido itemPedido = itemPedidoService.create(itemDto);
                itemPedido.setPreco(notebook.getPreco());

                return itemPedido;
            })
            .collect(Collectors.toList());
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
    public Pedido findByPedido(Long id) {
        validarId(id);
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado com o ID: " + id);
        }
        return pedido;
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
        pedidoRepository.persist(pedido);  
        return pedido;
    }

    
    @Override
    public List<PedidoResponseDTO> findByCliente(Long idCliente) {
        return pedidoRepository.findByClienteId(idCliente).stream()
                .map(PedidoResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponseDTO> findMyPedidos() {

        return findByCliente(clienteRepository.findByUsername(jwt.getName()).getId());
    }

    private void validarId(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null)
            throw new ValidationException("ID", "Pedido com o ID fornecido não encontrado.");
    }

    @Override
@Transactional
public Pedido cancelarPedido(Long pedidoId) {
    validarId(pedidoId);
    
    Pedido pedido = findById(pedidoId);
 
    String usernameAutenticado = jwt.getName(); 

    if (!pedido.getCliente().getUsuario().getUsername().equals(usernameAutenticado)) {
        throw new ValidationException("Id", "Pedido não encontrado.");
    }

    if (pedido.getStatus() == StatusPedido.CANCELADO) {
        throw new ValidationException("Status", "O pedido já foi cancelado.");
    }
    if (pedido.getStatus() == StatusPedido.PAGO || pedido.getStatus() == StatusPedido.ENTREGUE ||  pedido.getStatus() == StatusPedido.ENVIADO) {
        throw new ValidationException("Status", "Não é possível cancelar um pedido concluído.");
    }

 
    pedido.setStatus(StatusPedido.CANCELADO);
 
    pedido.getItens().forEach(item -> {
        loteService.atualizarEstoque(item.getNotebook().getId(), -item.getQuantidade());
    });
 
    pedidoRepository.persist(pedido);

    return pedido;
}


}
