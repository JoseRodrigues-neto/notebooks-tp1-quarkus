package br.unitins.tp1.notebooks.dto;

import br.unitins.tp1.notebooks.modelo.Pedido;
import br.unitins.tp1.notebooks.modelo.StatusPedido;
import java.time.LocalDate;
import java.util.List;

public record PedidoResponseDTO(
        Long id,  
        StatusPedido status,                    
        ClienteResponseDTO cliente,      
        List<ItemPedidoResponseDTO> itens,  
        LocalDate dataPedido,              
        Double valorTotal
       // Status statusPedido         
) {
 
    public static PedidoResponseDTO valueOf(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getStatus(),
                ClienteResponseDTO.valueOf(pedido.getCliente()),  
                pedido.getItens().stream()
                        .map(ItemPedidoResponseDTO::valueOf)      
                        .toList(),
                pedido.getDataPedido(),
                pedido.getValorTotal()
        );
    }
}
