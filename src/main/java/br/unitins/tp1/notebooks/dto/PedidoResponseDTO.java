package br.unitins.tp1.notebooks.dto;

import br.unitins.tp1.notebooks.modelo.Pedido;
import java.time.LocalDate;
import java.util.List;
 

public record PedidoResponseDTO(
    Long id,
    String status,
    String nomeCliente, 
    String endereco, 
    List<ItemPedidoResponseDTO> itens,      
    int quantidadeTotalItens,       
    LocalDate dataPedido,      
    Double valorTotal ,
    String tipoPagamento
        
) {

    public static PedidoResponseDTO valueOf(Pedido pedido) {
        return new PedidoResponseDTO(
            pedido.getId(),
            pedido.getStatus().getDescricao(),
            pedido.getCliente().getUsuario().getNome(), 
            pedido.getCliente().getEndereco(), 
            pedido.getItens().stream()
            .map(ItemPedidoResponseDTO::valueOf)      
            .toList(),                      
            pedido.getItens().stream().mapToInt(item -> item.getQuantidade()).sum(),   
            pedido.getDataPedido(),
            pedido.getValorTotal(),
            pedido.getTipoPagamento()
        );
    }
}
